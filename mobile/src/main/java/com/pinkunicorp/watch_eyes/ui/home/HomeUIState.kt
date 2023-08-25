package com.pinkunicorp.watch_eyes.ui.home

import com.pinkunicorp.common.eyes.CommonEye

data class HomeUIState(
    val currentEye: CommonEye? = null,
    val actions: List<Action> = emptyList()
) {

    sealed class Event {
        data object OnClickToOther : Event()
        data object OnClickToOpenWearApp : Event()
    }

    sealed class Action {
        data object NavigateToLibrary : Action()
        data object OpenWearApp : Action()
    }

}
