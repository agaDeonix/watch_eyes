package com.pinkunicorp.watch_eyes.di

import com.pinkunicorp.watch_eyes.domain.repository.EyeRepository
import com.pinkunicorp.watch_eyes.domain.useCase.GetAllEyesUseCase
import com.pinkunicorp.watch_eyes.domain.useCase.GetCurrentEyeUseCase
import com.pinkunicorp.watch_eyes.ui.home.HomeViewModel
import com.pinkunicorp.watch_eyes.ui.library.LibraryViewModel
import org.koin.dsl.module

val appModule = module {
    single { HomeViewModel(get()) }
    single { LibraryViewModel() }
    single { EyeRepository() }
    single { GetAllEyesUseCase(get()) }
    single { GetCurrentEyeUseCase(get()) }
}
