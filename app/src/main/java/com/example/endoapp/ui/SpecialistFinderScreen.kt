package com.example.endoapp.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.endoapp.R
import com.example.endoapp.map.SpecialistFinder


@Composable
fun SpecialistFinderScreen(
    onChatClick: () -> Unit,
    onForumClick: () -> Unit,
    onHomeClick: () -> Unit,
    onArticlesClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
//
//    val intent = Intent(context, SpecialistFinder::class.java)
//    context.startActivity(intent)

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        context.startActivity(
            Intent(context, SpecialistFinder::class.java)
        )
    }

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