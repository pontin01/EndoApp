package com.example.endoapp.database.functions

import com.example.endoapp.database.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email

suspend fun login(
    email: String,
    password: String,
    onError: (String) -> Unit,
    onSuccess: () -> Unit
) : Boolean {
    // blank input included
    if (email.isBlank()) {
        onError("Please enter an email.")
        return false
    }
    if (password.isBlank()) {
        onError("Please enter a password.")
        return false
    }

    // query supabase auth
    try {
        supabase.auth.signInWith(Email) {
            this.email = email.trim()
            this.password = password
        }
    } catch (e: AuthRestException) {
        onError(e.errorDescription)
        return false
    }



    onSuccess()
    return true
}