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
fun HomeScreen(
    onChatClick: () -> Unit,
    onForumClick: () -> Unit,
    onHomeClick: () -> Unit,
    onArticlesClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.home, null)

            view.findViewById<ImageButton>(R.id.chatButton)
                .setOnClickListener {
                    onChatClick()
                }

            view.findViewById<ImageButton>(R.id.forumButton)
                .setOnClickListener {
                    onForumClick()
                }

            view.findViewById<ImageButton>(R.id.homeButton)
                .setOnClickListener {
                    onHomeClick()
                }

            view.findViewById<ImageButton>(R.id.articlesButton)
                .setOnClickListener {
                    onArticlesClick()
                }

            view.findViewById<ImageButton>(R.id.profileButton)
                .setOnClickListener {
                    onProfileClick()
                }

            view
        }
    )
}