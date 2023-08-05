package com.pinkunicorp.watch_eyes.domain.repository

import com.pinkunicorp.common.eyes.BlackEye
import com.pinkunicorp.common.eyes.VampireEye
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EyeRepository {
    suspend fun getEyes() = withContext(Dispatchers.IO) {
        listOf(
            BlackEye(),
            VampireEye()
        )
    }
}
