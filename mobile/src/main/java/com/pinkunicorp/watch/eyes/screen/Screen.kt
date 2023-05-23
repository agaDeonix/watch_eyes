package com.pinkunicorp.watch.eyes.screen

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Library: Screen("library")
}
