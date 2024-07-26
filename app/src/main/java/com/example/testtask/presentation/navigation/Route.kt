package com.example.testtask.presentation.navigation

sealed class Route(val path: String) {

    data object MainScreen: Route(path = "main_screen")

}