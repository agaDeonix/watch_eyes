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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.wearable.*
import com.pinkunicorp.common.eyes.BlackEye
import com.pinkunicorp.common.eyes.CommonEye
import com.pinkunicorp.common.extensions.toMap
import com.pinkunicorp.common.eyes.VampireEye

class ClientDataViewModel(
    application: Application
) :
    AndroidViewModel(application),
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {

    var state by mutableStateOf(CommonEye.EyeState())
        private set

    private val allEyes = listOf(BlackEye(), VampireEye())

    var selectedEye by mutableStateOf(allEyes.first())
        private set

    override fun onDataChanged(dataEvents: DataEventBuffer) {

        // Do additional work for specific events
        dataEvents.forEach { dataEvent ->
            when (dataEvent.type) {
                DataEvent.TYPE_CHANGED -> {
                    when (dataEvent.dataItem.uri.path) {
                        DataLayerListenerService.STATE_PATH -> {
                            val mode = DataMapItem.fromDataItem(dataEvent.dataItem)
                                .dataMap
                                .getInt(DataLayerListenerService.STATE_MODE_KEY)
                            val data = DataMapItem.fromDataItem(dataEvent.dataItem)
                                .dataMap
                                .getDataMap(DataLayerListenerService.STATE_DATA_KEY)?.toBundle()
                                ?.toMap()
                            state = state.copy(
                                mode = CommonEye.State.values()[mode],
                                data = data
                            )
                        }
                        DataLayerListenerService.SELECTED_EYE_PATH -> {
                            selectedEye = DataMapItem.fromDataItem(dataEvent.dataItem)
                                .dataMap
                                .getInt(DataLayerListenerService.SELECTED_EYE_KEY).let {
                                    allEyes[it]
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
