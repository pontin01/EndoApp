package com.example.endoapp.ui

import android.view.LayoutInflater
import android.widget.Button
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.endoapp.R

@Composable
fun StartScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.start, null)

            view.findViewById<Button>(R.id.login)
                .setOnClickListener {
                    onLoginClick()
                }

            view.findViewById<Button>(R.id.signUp)
                .setOnClickListener {
                    onSignUpClick()
                }

            view
        }
    )
}