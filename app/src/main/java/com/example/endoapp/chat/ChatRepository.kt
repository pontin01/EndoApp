package com.example.endoapp.chat

import kotlinx.coroutines.delay

/**
 * This is the ONLY class you swap out to change where replies come from.
 * The ViewModel and UI don't care what's behind it.
 */
interface ChatRepository {
    /** Send the user's message and return the reply. Throw on failure. */
    suspend fun getReply(userMessage: String, history: List<ChatMessage>): String
}

/** Offline stand-in so you can test the UI right now. */
class FakeChatRepository : ChatRepository {
    override suspend fun getReply(userMessage: String, history: List<ChatMessage>): String {
        delay(1200) // simulate network
        return "Thanks for sharing. I've noted: \"$userMessage\". " +
                "Would you like to log your symptoms as well?"
    }
}

/*
 * ---- Option A: your own backend (Retrofit) ----
 *
 * interface ChatApi {
 *     @POST("chat") suspend fun send(@Body body: ChatRequest): ChatResponse
 * }
 * class ApiChatRepository(private val api: ChatApi) : ChatRepository {
 *     override suspend fun getReply(userMessage: String, history: List<ChatMessage>) =
 *         api.send(ChatRequest(userMessage, history.takeLast(20).map { it.text })).reply
 * }
 *
 * ---- Option B: person-to-person chat (Firebase Firestore) ----
 * Change the interface to expose a Flow<List<ChatMessage>> and
 * suspend fun send(text), using addSnapshotListener on
 * chats/{chatId}/messages ordered by "timestamp".
 */