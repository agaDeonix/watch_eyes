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

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pinkunicorp.watch.eyes.R
import com.google.android.gms.wearable.Asset
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import kotlin.math.absoluteValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ClientDataViewModel(
    application: Application
) :
    AndroidViewModel(application),
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {

    var state by mutableStateOf<Int>(0)
        private set

    var manualPosition by mutableStateOf(Pair(0f, 0f))
        private set

    override fun onDataChanged(dataEvents: DataEventBuffer) {

        // Do additional work for specific events
        dataEvents.forEach { dataEvent ->
            when (dataEvent.type) {
                DataEvent.TYPE_CHANGED -> {
                    when (dataEvent.dataItem.uri.path) {
                        DataLayerListenerService.STATE_PATH -> {
                            state = DataMapItem.fromDataItem(dataEvent.dataItem)
                                .dataMap
                                .getInt(DataLayerListenerService.STATE_KEY)
                        }
                        DataLayerListenerService.MANUAL_POSITION_PATH -> {
                            manualPosition = DataMapItem.fromDataItem(dataEvent.dataItem)
                                .dataMap
                                .getString(DataLayerListenerService.MANUAL_POSITION_KEY).let {
                                    val items = it.split(";").map { it.toFloat() }
                                    Pair(items[0], items[1])
                                }
                        }
                    }
                }
            }
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {

    }

    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {

    }
}

/**
 * A data holder describing a client event.
 */
data class Event(
    @StringRes val title: Int,
    val text: String
)
