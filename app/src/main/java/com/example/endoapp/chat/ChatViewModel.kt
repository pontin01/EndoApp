package com.example.endoapp.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository = FakeChatRepository()
) : ViewModel() {

    private var nextId = 1L

    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(
                id = nextId++,
                text = "Hi Jess 👋 I'm here to help. Ask a question or tell me how you're feeling today.",
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
                    it + ChatMessage(nextId++, "Sorry, something went wrong. Please try again.", false)
                }
            } finally {
                _isTyping.value = false
            }
        }
    }
}