package com.pinkunicorp.watch_eyes.ui.library

data class LibraryUIState(
    val isLoading: Boolean = true,
    val actions: List<Action> = emptyList()
) {
    sealed class Event {
        object OnClickToBack : Event()
    }

    sealed class Action {
        object NavigateToBack : Action()
    }
}
