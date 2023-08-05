package com.pinkunicorp.watch_eyes.domain.useCase

import com.pinkunicorp.watch_eyes.domain.repository.EyeRepository

class GetAllEyesUseCase(
    private val eyeRepository: EyeRepository
) {
    suspend operator fun invoke() = eyeRepository.getEyes()
}
