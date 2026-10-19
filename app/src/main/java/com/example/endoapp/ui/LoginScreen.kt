package com.example.endoapp.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.example.endoapp.R
import com.example.endoapp.database.functions.UserService
import com.example.endoapp.database.functions.login
import com.example.endoapp.state.AuthHash
import com.example.endoapp.state.UserDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onGoClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val view = LocalView.current

    // Change Time and Battery icons to be Black
    SideEffect {
        val window = (view.context as Activity).window

        WindowCompat.enableEdgeToEdge(window)

        WindowCompat.getInsetsController(window, view).apply {
            // false = light icons/text (white)
            isAppearanceLightStatusBars = true
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.login, null)

            // user inputs
            val email = view.findViewById<EditText>(R.id.emailInput)
            val password = view.findViewById<EditText>(R.id.passwordInput)

            val loginErrorMessage = view.findViewById<TextView>(R.id.loginErrorMessage)

            view.findViewById<Button>(R.id.goButton)
                .setOnClickListener {
                    // run login function in coroutine
                    val scope = CoroutineScope(Dispatchers.Main)
                    scope.launch {
                        val authHash = UserService().getAuthHash(email.text.toString())
                        val user = UserService().getUser(authHash)

                        // login with SupabaseAuth
                        val success = login(
                            email = email.text.toString(),
                            password = password.text.toString(),
                            onSuccess = {
                                AuthHash().setAuthHashLocally(authHash, context)
                                UserDetails().setUserDetails(user, context)

                                onGoClick()
                            },
                            // create error message
                            onError = { error: String ->
                                loginErrorMessage.text = error
                                loginErrorMessage.visibility = View.VISIBLE
                            }
                        )
                    }
                }

            view.findViewById<ImageButton>(R.id.back)
                .setOnClickListener {
                    onBackClick()
                }

            view
        }
    )
}

