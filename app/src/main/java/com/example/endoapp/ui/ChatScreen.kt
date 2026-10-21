package com.example.endoapp.ui

import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.endoapp.R
import com.example.endoapp.chat.ChatAdapter
import com.example.endoapp.chat.ChatViewModel


@Composable
fun ChatScreen(
    onChatClick: () -> Unit,
    onForumClick: () -> Unit,
    onHomeClick: () -> Unit,
    onArticlesClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val context = LocalContext.current

    val viewModel: ChatViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>
            ): T {
                if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ChatViewModel(
                        context = context
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    )

    val messages by viewModel.messages.collectAsState()
    val isTyping by viewModel.isTyping.collectAsState()

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(), // lifts the input bar above the keyboard
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.chat, null)

            // ---- Bottom nav (same wiring as HomeScreen) ----
            view.findViewById<ImageButton>(R.id.chatButton).setOnClickListener { onChatClick() }
            view.findViewById<ImageButton>(R.id.forumButton).setOnClickListener { onForumClick() }
            view.findViewById<ImageButton>(R.id.homeButton).setOnClickListener { onHomeClick() }
            view.findViewById<ImageButton>(R.id.articlesButton).setOnClickListener { onArticlesClick() }
            view.findViewById<ImageButton>(R.id.profileButton).setOnClickListener { onProfileClick() }

            // ---- Message list ----
            val recycler = view.findViewById<RecyclerView>(R.id.messagesRecycler)
            recycler.layoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
            recycler.adapter = ChatAdapter()

            // ---- Sending ----
            val input = view.findViewById<EditText>(R.id.messageInput)
            fun submit() {
                viewModel.send(input.text.toString())
                input.text.clear()
            }
            view.findViewById<ImageButton>(R.id.sendButton).setOnClickListener { submit() }
            input.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEND) { submit(); true } else false
            }

            view
        },
        update = { view ->
            // Runs whenever messages / isTyping change
            val recycler = view.findViewById<RecyclerView>(R.id.messagesRecycler)
            val adapter = recycler.adapter as ChatAdapter
            adapter.submitList(messages) {
                if (messages.isNotEmpty()) recycler.smoothScrollToPosition(messages.lastIndex)
            }
            view.findViewById<TextView>(R.id.typingIndicator).visibility =
                if (isTyping) android.view.View.VISIBLE else android.view.View.GONE
        }
    )


}