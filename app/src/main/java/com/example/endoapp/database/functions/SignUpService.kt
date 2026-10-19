package com.example.endoapp.database.functions

import android.util.Patterns
import androidx.compose.ui.platform.LocalContext
import com.example.endoapp.database.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email

suspend fun signUp(
    username: String,
    email: String,
    password: String,
    passwordConfirmation: String,
    onError: (String) -> Unit,
    onSuccess: suspend () -> Unit
) {
    // blank input
    if (username.isBlank() || email.isBlank() || password.isBlank() || passwordConfirmation.isBlank()) {
        onError("A required field is missing.")
        return
    }
    // email is not in email format
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        onError("You have entered an invalid email address.")
        return
    }
    // passwords do not match
    if (password != passwordConfirmation) {
        onError("The passwords do not match.")
        return
    }

    // signup user through supabase auth
    try {
        val result = supabase.auth.signUpWith(Email) {
            this.email = email.trim()
            this.password = password
        }

        onSuccess()
    } catch (e: AuthRestException) {
        onError(e.errorDescription)
    }
}