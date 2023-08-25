package com.pinkunicorp.watch_eyes.ui.library

import androidx.lifecycle.ViewModel
import com.pinkunicorp.watch_eyes.domain.useCase.GetAllEyesUseCase
import com.pinkunicorp.watch_eyes.domain.useCase.GetCurrentEyeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LibraryViewModel(
    val getAllEyes: GetAllEyesUseCase,
    val getCurrentEye: GetCurrentEyeUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUIState())
    val uiState = _uiState.asStateFlow()

    fun consumeActions(actions: List<LibraryUIState.Action>) {
        _uiState.update {
            it.copy(
                actions = it.actions - actions.toSet()
            )
        }
    }

    fun handleEvent(event: LibraryUIState.Event) {
        when (event) {
            LibraryUIState.Event.OnClickToBack -> {
                _uiState.update {
                    it.copy(
                        actions = it.actions + LibraryUIState.Action.NavigateToBack
                    )
                }
            }
        }
    }

}
