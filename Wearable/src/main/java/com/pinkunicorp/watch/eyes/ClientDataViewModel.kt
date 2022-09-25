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
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.wearable.*

class ClientDataViewModel(
    application: Application
) :
    AndroidViewModel(application),
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {

    var state by mutableStateOf<Int>(0)
        private set

    var manualPosition by mutableStateOf(Triple(0f, 0f, 0.2f))
        private set

    var specAnimation by mutableStateOf<Int?>(null)
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
                                    Triple(items[0], items[1], items[2])
                                }
                        }
                        DataLayerListenerService.SPEC_PATH -> {
                            specAnimation = DataMapItem.fromDataItem(dataEvent.dataItem)
                                .dataMap
                                .getInt(DataLayerListenerService.SPEC_KEY)
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