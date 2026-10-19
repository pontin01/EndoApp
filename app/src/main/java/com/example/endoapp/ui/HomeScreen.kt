package com.example.endoapp.ui

import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.endoapp.R
import com.example.endoapp.database.User
import com.example.endoapp.state.UserDetails

@Composable
fun HomeScreen(
    onChatClick: () -> Unit,
    onForumClick: () -> Unit,
    onHomeClick: () -> Unit,
    onArticlesClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSpecialistFinderClick: () -> Unit,
    onFAQClick: () -> Unit,
    onEventsClick: () -> Unit,
) {
    val context = LocalContext.current

    // get user details
    val user: User = UserDetails().getUserDetails(context)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.home, null)

            // set welcome screen to username
            val usernameGreeting = view.findViewById<TextView>(R.id.usernameGreeting)
            usernameGreeting.text = user.username


            view.findViewById<ImageButton>(R.id.chatButton).setOnClickListener { onChatClick() }
            view.findViewById<ImageButton>(R.id.forumButton).setOnClickListener { onForumClick() }
            view.findViewById<ImageButton>(R.id.homeButton).setOnClickListener { onHomeClick() }
            view.findViewById<ImageButton>(R.id.articlesButton).setOnClickListener { onArticlesClick() }
            view.findViewById<ImageButton>(R.id.profileButton).setOnClickListener { onProfileClick() }
            view.findViewById<LinearLayout>(R.id.specialistFinderButton).setOnClickListener { onSpecialistFinderClick() }
            view.findViewById<LinearLayout>(R.id.faqsButton).setOnClickListener { onFAQClick() }
            view.findViewById<LinearLayout>(R.id.localEventsButton).setOnClickListener { onEventsClick() }

            view
        }
    )
}