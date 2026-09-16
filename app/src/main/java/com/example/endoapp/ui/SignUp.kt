package com.example.endoapp.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.endoapp.R
import com.example.endoapp.database.User
import com.example.endoapp.database.functions.PasswordManager
import com.example.endoapp.database.functions.UserService

class SignUp : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.sign_up)

        // Input Fields
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val passwordConfirmationInput = findViewById<EditText>(R.id.passwordConfirmationInput)

        // Error Message
        val errorMessage = findViewById<TextView>(R.id.signUpErrorMessage)

        // Buttons
        val backButton = findViewById<ImageButton>(R.id.back)
        backButton.setOnClickListener {
            finish()
        }
        val goButton = findViewById<Button>(R.id.goButton)
        goButton.setOnClickListener {
            lifecycleScope.launch {
                println(usernameInput.text.toString())
                println(emailInput.text.toString())
                println(passwordInput.text.toString())
                println(passwordConfirmationInput.text.toString())
                val result = signUp(
                    username = usernameInput.text.toString(),
                    email = emailInput.text.toString(),
                    password = passwordInput.text.toString(),
                    passwordConfirmation = passwordConfirmationInput.text.toString()
                )
                if (result != "") {
                    errorMessage.text = result
                    errorMessage.visibility = View.VISIBLE
                }
            }

            usernameInput.clearComposingText()
            emailInput.clearComposingText()
            passwordInput.clearComposingText()
            passwordConfirmationInput.clearComposingText()
        }
    }

    private suspend fun signUp(username: String, email: String, password: String, passwordConfirmation: String): String {
        val user = User(username=username, email=email)
        val userService = UserService(user)

        if (userService.usernameExists()) {
            return "Username Already Exists."
        }
        if (userService.emailExists()) {
            return "Email Already Exists."
        }
        if (password != passwordConfirmation) {
            return "Passwords Do Not Match."
        }

        user.passwordHash = PasswordManager().createPasswordHash(password)
        userService.createUser()

        return ""
    }
}