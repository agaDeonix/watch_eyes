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

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentEyeUseCase().let { eye ->
                _uiState.update { it.copy(currentEye = eye) }
            }
        }
    }

    fun handleEvent(event: HomeUIState.Event) {
        when (event) {
            HomeUIState.Event.OnClickToOther -> {
                _uiState.update {
                    it.copy(
                        actions = it.actions + HomeUIState.Action.NavigateToLibrary
                    )
                }
            }

            HomeUIState.Event.OnClickToOpenWearApp -> {
                _uiState.update {
                    it.copy(
                        actions = it.actions + HomeUIState.Action.OpenWearApp
                    )
                }
            }
        }
    }

    fun consumeActions(actions: List<HomeUIState.Action>) {
        _uiState.update {
            it.copy(
                actions = it.actions - actions.toSet()
            )
        }
    }

}
