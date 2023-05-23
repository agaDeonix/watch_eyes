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
package com.pinkunicorp.watch_eyes

import androidx.lifecycle.ViewModel
import com.google.android.gms.wearable.*
import com.pinkunicorp.common.eyes.BlackEye
import com.pinkunicorp.common.eyes.CommonEye
import com.pinkunicorp.common.eyes.VampireEye

/**
 * A state holder for the client data.
 */
class ClientDataViewModel :
    ViewModel(),
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {

    var allEyes = listOf(BlackEye(), VampireEye())
    var currentEye: CommonEye = allEyes.first()

    override fun onDataChanged(dataEvents: DataEventBuffer) {

    }

    override fun onMessageReceived(messageEvent: MessageEvent) {

    }

    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {

    }

}
