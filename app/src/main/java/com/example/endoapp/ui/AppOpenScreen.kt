package com.example.endoapp.ui

import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.endoapp.R
import com.example.endoapp.database.User
import com.example.endoapp.database.functions.UserService
import com.example.endoapp.state.AuthHash
import com.example.endoapp.state.UserDetails

@Composable
fun AppOpenScreen(
    onSignedIn: () -> Unit,
    onSignedOut: () -> Unit
) {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.app_open, null)

            view
        }
    )

    LaunchedEffect(Unit) {
        val authHash = AuthHash().getAuthHashLocally(context)

        if (authHash.isEmpty()) {
            onSignedOut()
        }

        // get user details from database and store locally
        val user: User = UserService().getUser(authHash)
        UserDetails().setUserDetails(user, context)

        onSignedIn()
    }
}