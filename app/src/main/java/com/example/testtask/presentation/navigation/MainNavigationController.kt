package com.example.testtask.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testtask.presentation.ui.screens.MainScreen

@Composable
fun MainNavigationController(navController: NavHostController){
    CompositionLocalProvider(
        LocalNavController provides navController
    ){
        NavHost(
            navController = navController,
            startDestination = Route.MainScreen.path,
            enterTransition = { fadeIn(animationSpec = tween(350)) },
            exitTransition = { fadeOut(animationSpec = tween(350)) },
        ){
            composable(route = Route.MainScreen.path) {
                MainScreen()
            }
        }
    }
}