package com.example.endoapp.ui

import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.endoapp.R

@Composable
fun LoginScreen(
    onGoClick: () -> Unit,
    onBackClick: () -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.login, null)

            view.findViewById<Button>(R.id.goButton)
                .setOnClickListener {
                    onGoClick()
                }

            view.findViewById<ImageButton>(R.id.back)
                .setOnClickListener {
                    onBackClick()
                }

            view
        }
    )
}

private fun login(email: String, password: String, passwordConfirmation: String) {

}