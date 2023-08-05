package com.pinkunicorp.watch_eyes.ui.home

import com.pinkunicorp.common.eyes.CommonEye

data class HomeState(
    val currentEye: CommonEye? = null,
) {

    sealed class Event {
        object OnClickToOther : Event()
    }

    sealed class Action {
        object NavigateToLibrary : Action()
    }

}
