package com.pinkunicorp.watch_eyes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkunicorp.watch_eyes.domain.useCase.GetCurrentEyeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCurrentEyeUseCase: GetCurrentEyeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentEyeUseCase().let { eye ->
                _uiState.update { it.copy(currentEye = eye) }
            }
        }
    }

    fun handleEvent(event: HomeState.Event) {
        when (event) {
            HomeState.Event.OnClickToOther -> {
                _uiState.update { it.copy(currentEye = null) }
            }
        }
    }

}
