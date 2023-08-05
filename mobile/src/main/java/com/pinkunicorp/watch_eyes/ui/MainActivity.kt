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
package com.pinkunicorp.watch_eyes.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.DataMap
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import com.pinkunicorp.common.eyes.CommonEye
import com.pinkunicorp.common.extensions.toBundle
import com.pinkunicorp.watch_eyes.ui.home.HomeScreen
import com.pinkunicorp.watch_eyes.ui.library.Library
import com.pinkunicorp.watch_eyes.ui.Screen
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {

//    private val dataClient by lazy { Wearable.getDataClient(this) }
//    private val messageClient by lazy { Wearable.getMessageClient(this) }
//    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }
//    private val nodeClient by lazy { Wearable.getNodeClient(this) }

//    private var sendManualPositionJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MaterialTheme {
                NavHost(navController = navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        HomeScreen(navController = navController)
                    }
                    composable(Screen.Library.route) {
//                        Library(onBackClick = {
//                            navController.navigateUp()
//                        },
//                            allEyes = clientDataViewModel.allEyes,
//                            currentEye = clientDataViewModel.currentEye,
//                            onSelectNewEye = { eye, pos ->
////                                clientDataViewModel.currentEye = eye
////                                sendSelectedEye(pos)
//                                navController.navigateUp()
//                            }
//                        )
                    }
                }
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        dataClient.addListener(clientDataViewModel)
//        messageClient.addListener(clientDataViewModel)
//        capabilityClient.addListener(
//            clientDataViewModel,
//            Uri.parse("wear://"),
//            CapabilityClient.FILTER_REACHABLE
//        )
//    }
//
//    override fun onPause() {
//        super.onPause()
//        dataClient.removeListener(clientDataViewModel)
//        messageClient.removeListener(clientDataViewModel)
//        capabilityClient.removeListener(clientDataViewModel)
//    }

//    private fun startWearableActivity() {
//        lifecycleScope.launch {
//            try {
//                val nodes = nodeClient.connectedNodes.await()
//
//                // Send a message to all nodes in parallel
//                nodes.map { node ->
//                    async {
//                        messageClient.sendMessage(node.id, START_ACTIVITY_PATH, byteArrayOf())
//                            .await()
//                    }
//                }.awaitAll()
//
//                Log.d(TAG, "Starting activity requests sent successfully")
//            } catch (cancellationException: CancellationException) {
//                throw cancellationException
//            } catch (exception: Exception) {
//                Log.d(TAG, "Starting activity failed: $exception")
//            }
//        }
//    }

//    private fun sendNewState(state: CommonEye.EyeState) {
//        lifecycleScope.launch {
//            try {
//                val request = PutDataMapRequest.create(STATE_PATH).apply {
//                    dataMap.putInt(STATE_MODE_KEY, state.mode.ordinal)
//                    state.data?.toBundle()?.let {
//                        DataMap.fromBundle(it)
//                    }?.let {
//                        dataMap.putDataMap(STATE_DATA_KEY, it)
//                    }
//                }
//                    .asPutDataRequest()
//                    .setUrgent()
//
//                val result = dataClient.putDataItem(request).await()
//
//                Log.d(TAG, "DataItem saved: $result")
//            } catch (cancellationException: CancellationException) {
//                throw cancellationException
//            } catch (exception: Exception) {
//                Log.d(TAG, "Saving DataItem failed: $exception")
//            }
//        }
//    }

//    private fun sendSelectedEye(selectedEyePos: Int) {
//        lifecycleScope.launch {
//            try {
//                val request = PutDataMapRequest.create(SELECTED_EYE_PATH).apply {
//                    dataMap.putInt(SELECTED_EYE_KEY, selectedEyePos)
//                }
//                    .asPutDataRequest()
//                    .setUrgent()
//
//                val result = dataClient.putDataItem(request).await()
//
//                Log.d(TAG, "DataItem saved: $result")
//            } catch (cancellationException: CancellationException) {
//                throw cancellationException
//            } catch (exception: Exception) {
//                Log.d(TAG, "Saving DataItem failed: $exception")
//            }
//        }
//    }

//    private fun sendManualPosition(x: Float, y: Float, focus: Float) {
//        sendManualPositionJob?.cancel()
//        sendManualPositionJob = lifecycleScope.launch {
//            delay(100)
//            try {
//                val request = PutDataMapRequest.create(MANUAL_POSITION_PATH).apply {
//                    dataMap.putString(MANUAL_POSITION_KEY, "$x;$y;$focus")
//                }
//                    .asPutDataRequest()
//                    .setUrgent()
//
//                val result = dataClient.putDataItem(request).await()
//
//                Log.d(TAG, "DataItem saved: $result")
//            } catch (cancellationException: CancellationException) {
//                throw cancellationException
//            } catch (exception: Exception) {
//                Log.d(TAG, "Saving DataItem failed: $exception")
//            }
//        }
//    }

    companion object {
        private const val TAG = "MainActivity"

        private const val START_ACTIVITY_PATH = "/start-activity"
        private const val STATE_PATH = "/state"
        private const val STATE_MODE_KEY = "mode"
        private const val STATE_DATA_KEY = "data"

        private const val SELECTED_EYE_PATH = "/selected-eye"
        private const val SELECTED_EYE_KEY = "selected-eye"
    }
}
