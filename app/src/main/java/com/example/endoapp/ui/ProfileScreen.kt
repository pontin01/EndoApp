package com.example.endoapp.ui

import android.view.LayoutInflater
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
import com.example.endoapp.state.AuthHash
import com.example.endoapp.state.UserDetails

@Composable
fun ProfileScreen(
    onEditAvatarClick: () -> Unit,
//    onEditProfileClick: () -> Unit,
    onHealthDataClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onHelpClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onChatClick: () -> Unit,
    onForumClick: () -> Unit,
    onHomeClick: () -> Unit,
    onArticlesClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val context = LocalContext.current

    // get user details
    val user: User = UserDetails().getUserDetails(context)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.profile, null)

            // set user details
            val profileNameText = view.findViewById<TextView>(R.id.profileNameText)
            profileNameText.text = user.username

            val profileEmailText = view.findViewById<TextView>(R.id.profileEmailText)
            profileEmailText.text = user.email
//          profileNameText
//          profileEmailText
//
//            view.findViewById<ImageButton>(R.id.editAvatarButton)
//                .setOnClickListener {
//                    onEditAvatarClick()
//                }

//            view.findViewById<ImageButton>(R.id.editProfileButton)
//                .setOnClickListener {
//                    onEditProfileClick()
//                }

//            view.findViewById<ImageButton>(R.id.healthDataButton)
//                .setOnClickListener {
//                    onHealthDataClick()
//                }

//            view.findViewById<ImageButton>(R.id.notificationsButton)
//                .setOnClickListener {
//                    onNotificationsClick()
//                }
//
//            view.findViewById<ImageButton>(R.id.privacyButton)
//                .setOnClickListener {
//                    onPrivacyClick()
//                }
//
//            view.findViewById<ImageButton>(R.id.helpButton)
//                .setOnClickListener {
//                    onHelpClick()
//                }
//
            view.findViewById<LinearLayout>(R.id.logoutButton)
                .setOnClickListener {
                    AuthHash().setAuthHashLocally("", context)
                    UserDetails().clearUserDetails(context)

                    onLogoutClick()
                }

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