package com.example.endoapp.chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

//class OpenAIChatRepository(
//    private val apiKey: String
//) : ChatRepository {
//
//    private val client = OkHttpClient()
//
//    override suspend fun getReply(
//        text: String,
//        messages: List<ChatMessage>
//    ): String = withContext(Dispatchers.IO)
//
//}