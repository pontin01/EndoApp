package com.example.endoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.endoapp.ui.HomeScreen
import com.example.endoapp.ui.LoginScreen
import com.example.endoapp.ui.SignUpScreen
import com.example.endoapp.ui.StartScreen

class Main : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EndoApp()
        }
    }
}

@Composable
fun EndoApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "start"
    ) {
        // Start Screen
        composable("start") {
            StartScreen(
                onLoginClick = { navController.navigate("login") },
                onSignUpClick = { navController.navigate("sign_up") }
            )
        }

        // Login Screen
        composable("login") {
            LoginScreen(
                onGoClick = { navController.navigate("home") },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Sign Up Screen
        composable("sign_up") {
            SignUpScreen(
                onGoClick = { navController.navigate("home") },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Home Screen
        composable("home") {
            HomeScreen(
                onChatClick = { navController.navigate("login") },
                onForumClick = { navController.navigate("login") },
                onHomeClick = { navController.navigate("home") },
                onArticlesClick = { navController.navigate("login") },
                onProfileClick = { navController.navigate("login") }
            )
        }

    }
}