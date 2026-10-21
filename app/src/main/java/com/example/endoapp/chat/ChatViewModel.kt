package com.example.endoapp.chat

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.endoapp.database.User
import com.example.endoapp.state.UserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository = GeminiChatRepository(),
    private val context: Context
) : ViewModel() {

    private var nextId = 1L

    val user: User = UserDetails().getUserDetails(context)

    val GREETING: String = "Hi " + user.username + ", what would you like to ask about endometriosis?"
    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(
                id = nextId++,
                text = GREETING,
                isSentByMe = false
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    fun send(rawText: String) {
        val text = rawText.trim()
        if (text.isEmpty() || _isTyping.value) return

        _messages.update { it + ChatMessage(nextId++, text, isSentByMe = true) }

        viewModelScope.launch {
            _isTyping.value = true
            try {
                val reply = repository.getReply(text, _messages.value)
                _messages.update { it + ChatMessage(nextId++, reply, isSentByMe = false) }
            } catch (e: Exception) {
                _messages.update {
                    it + ChatMessage(nextId++, e.toString(), false)
                }
            } finally {
                _isTyping.value = false
            }
        }
    }
}