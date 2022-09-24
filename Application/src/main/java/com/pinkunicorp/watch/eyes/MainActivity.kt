/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pinkunicorp.watch.eyes

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.wearable.*
import java.io.ByteArrayOutputStream
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

/**
 * Manages Wearable clients to showcase the [DataClient], [MessageClient], [CapabilityClient] and
 * [NodeClient].
 *
 * While resumed, this activity periodically sends a count through the [DataClient], and offers
 * the ability for the user to take and send a photo over the [DataClient].
 *
 * This activity also allows the user to launch the companion wear activity via the [MessageClient].
 *
 * While resumed, this activity also logs all interactions across the clients, which includes events
 * sent from this activity and from the watch(es).
 */
class MainActivity : ComponentActivity() {

    private val dataClient by lazy { Wearable.getDataClient(this) }
    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }
    private val nodeClient by lazy { Wearable.getNodeClient(this) }

    private var sendManualPositionJob: Job? = null

    private val isCameraSupported by lazy {
        packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    private val clientDataViewModel by viewModels<ClientDataViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainApp(
                    onStateClick = ::sendNewState,
                    onStartWearableActivityClick = ::startWearableActivity,
                    onPositionChange = ::sendManualPosition
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dataClient.addListener(clientDataViewModel)
        messageClient.addListener(clientDataViewModel)
        capabilityClient.addListener(
            clientDataViewModel,
            Uri.parse("wear://"),
            CapabilityClient.FILTER_REACHABLE
        )
    }

    override fun onPause() {
        super.onPause()
        dataClient.removeListener(clientDataViewModel)
        messageClient.removeListener(clientDataViewModel)
        capabilityClient.removeListener(clientDataViewModel)
    }

    private fun startWearableActivity() {
        lifecycleScope.launch {
            try {
                val nodes = nodeClient.connectedNodes.await()

                // Send a message to all nodes in parallel
                nodes.map { node ->
                    async {
                        messageClient.sendMessage(node.id, START_ACTIVITY_PATH, byteArrayOf())
                            .await()
                    }
                }.awaitAll()

                Log.d(TAG, "Starting activity requests sent successfully")
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Starting activity failed: $exception")
            }
        }
    }

    private fun sendNewState(state: State) {
        lifecycleScope.launch {
            try {
                val request = PutDataMapRequest.create(STATE_PATH).apply {
                    dataMap.putInt(STATE_KEY, state.ordinal)
                }
                    .asPutDataRequest()
                    .setUrgent()

                val result = dataClient.putDataItem(request).await()

                Log.d(TAG, "DataItem saved: $result")
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Saving DataItem failed: $exception")
            }
        }
    }

    private fun sendManualPosition(x: Float, y: Float) {
//        sendManualPositionJob?.cancel()
        sendManualPositionJob = lifecycleScope.launch {
//            delay(10)
            try {
                val request = PutDataMapRequest.create(MANUAL_POSITION_PATH).apply {
                    dataMap.putString(MANUAL_POSITION_KEY, "$x;$y")
                    Log.e("APP", "X:${x} Y:${y}")
                }
                    .asPutDataRequest()
                    .setUrgent()

                val result = dataClient.putDataItem(request).await()

                Log.d(TAG, "DataItem saved: $result")
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Saving DataItem failed: $exception")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"

        private const val START_ACTIVITY_PATH = "/start-activity"
        private const val STATE_PATH = "/state"
        private const val STATE_KEY = "state"
        private const val MANUAL_POSITION_PATH = "/manual-position"
        private const val MANUAL_POSITION_KEY = "manual-position"
    }
}
