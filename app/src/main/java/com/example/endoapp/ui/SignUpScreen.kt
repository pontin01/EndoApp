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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.example.endoapp.R
import com.example.endoapp.database.functions.UserService
import com.example.endoapp.database.functions.signUp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onGoClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val view = LocalView.current
    val context = LocalContext.current

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
                .inflate(R.layout.sign_up, null)

            // user inputs
            val username = view.findViewById<EditText>(R.id.usernameInput)
            val email = view.findViewById<EditText>(R.id.emailInput)
            val password = view.findViewById<EditText>(R.id.passwordInput)
            val passwordConfirmation = view.findViewById<EditText>(R.id.passwordConfirmationInput)

            val signUpErrorMessage = view.findViewById<TextView>(R.id.signUpErrorMessage)

            view.findViewById<Button>(R.id.goButton)
                .setOnClickListener {
                    // run sign up function in coroutine
                    val scope = CoroutineScope(Dispatchers.Main)
                    scope.launch {
                        // SupabaseAuth
                        signUp(
                            username = username.text.toString(),
                            email = email.text.toString(),
                            password = password.text.toString(),
                            passwordConfirmation = passwordConfirmation.text.toString(),
                            onSuccess = {
                                UserService().createUser(
                                    email.text.toString(),
                                    username.text.toString(),
                                    password.text.toString(),
                                    context
                                )

                                onGoClick()
                            },
                            onError = { error: String ->
                                signUpErrorMessage.text = error
                                signUpErrorMessage.visibility = View.VISIBLE
                            }
                        )

                        // Creating User record in database

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