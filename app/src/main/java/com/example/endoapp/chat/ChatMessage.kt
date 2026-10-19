package com.example.endoapp.chat

data class ChatMessage(
    val id: Long,
    val text: String,
    val isSentByMe: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)