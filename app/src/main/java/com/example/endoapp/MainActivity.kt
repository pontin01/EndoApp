package com.example.endoapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.endoapp.map.SpecialistFinder
import com.example.endoapp.ui.AppOpenScreen
import com.example.endoapp.ui.ChatScreen
import com.example.endoapp.ui.EventsScreen
import com.example.endoapp.ui.FaqScreen
import com.example.endoapp.ui.HomeScreen
import com.example.endoapp.ui.LoginScreen
import com.example.endoapp.ui.ProfileScreen
import com.example.endoapp.ui.SignUpScreen
import com.example.endoapp.ui.SpecialistFinderScreen
import com.example.endoapp.ui.StartScreen

class Main : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EndoApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EndoApp() {
    val LOGIN = "login"
    val SIGNUP = "sign_up"
    val CHAT = "chat"
    val FORUM = "login"
    val HOME = "home"
    val ARTCILES = "articles"
    val PROFILE = "profile"


    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "app_open"
    ) {
        // App Open Screen
        composable("app_open") {
            AppOpenScreen(
                onSignedIn = { navController.navigate(HOME) },
                onSignedOut = { navController.navigate("start") }
            )
        }

        // Start Screen
        composable("start") {
            StartScreen(
                onLoginClick = { navController.navigate(LOGIN) },
                onSignUpClick = { navController.navigate(SIGNUP) }
            )
        }

        // Login Screen
        composable("login") {
            LoginScreen(
                onGoClick = { navController.navigate(HOME) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Sign Up Screen
        composable("sign_up") {
            SignUpScreen(
                onGoClick = { navController.navigate(HOME) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Home Screen
        composable("home") {
            HomeScreen(
                onChatClick = { navController.navigate(CHAT) },
                onForumClick = { navController.navigate(LOGIN) },
                onHomeClick = { navController.navigate(HOME) },
                onArticlesClick = { navController.navigate(ARTCILES) },
                onProfileClick = { navController.navigate(PROFILE) },
                onSpecialistFinderClick = {
                    context.startActivity(
                        Intent(context, SpecialistFinder::class.java)
                    )
                },
                onFAQClick = {
                    navController.navigate("faq")
                },
                onEventsClick = { navController.navigate("events") }
            )
        }

        // Profile Screen
        composable("profile") {
            ProfileScreen(
                onEditAvatarClick = { navController.navigate(LOGIN) },
//                onEditProfileClick = { navController.navigate(LOGIN) },
                onHealthDataClick = { navController.navigate(LOGIN) },
                onNotificationsClick = { navController.navigate(LOGIN) },
                onPrivacyClick = { navController.navigate(LOGIN) },
                onHelpClick = { navController.navigate(LOGIN) },
                onLogoutClick = { navController.navigate("start") },
                onChatClick = { navController.navigate(CHAT) },
                onForumClick = { navController.navigate(LOGIN) },
                onHomeClick = { navController.navigate(HOME) },
                onArticlesClick = { navController.navigate(ARTCILES) },
                onProfileClick = { navController.navigate(PROFILE) }
            )
        }

        // Chat Screen
        composable("chat") {
            ChatScreen(
                onChatClick = { navController.navigate(CHAT) },
                onForumClick = { navController.navigate(LOGIN) },
                onHomeClick = { navController.navigate(HOME) },
                onArticlesClick = { navController.navigate(ARTCILES) },
                onProfileClick = { navController.navigate(PROFILE) }
            )
        }

        // FAQ Screen
        composable("faq") {
            FaqScreen(
                onChatClick = { navController.navigate(CHAT) },
                onForumClick = { navController.navigate(LOGIN) },
                onHomeClick = { navController.navigate(HOME) },
                onArticlesClick = { navController.navigate(ARTCILES) },
                onProfileClick = { navController.navigate(PROFILE) }
            )
        }

        // Local Events
        composable("events") {
            EventsScreen(
                onChatClick = { navController.navigate(CHAT) },
                onForumClick = { navController.navigate(LOGIN) },
                onHomeClick = { navController.navigate(HOME) },
                onArticlesClick = { navController.navigate(ARTCILES) },
                onProfileClick = { navController.navigate(PROFILE) }
            )
        }

        // Articles Screen
        composable("articles") {
            ArticlesFlow(navController)
        }

    }
}