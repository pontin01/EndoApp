package com.example.endoapp.chat

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content

/**
 * Gemini-backed replies via Firebase AI Logic.
 * Drop-in replacement for FakeChatRepository.
 */
class GeminiChatRepository : ChatRepository {

    private val model = Firebase
        .ai(backend = GenerativeBackend.agentPlatform())
        .generativeModel(
            modelName = "gemini-3.5-flash",
            systemInstruction = content { text(SYSTEM_PROMPT) }
        )

    override suspend fun getReply(userMessage: String, history: List<ChatMessage>): String {
        // `history` already contains the message being sent (the ViewModel adds it first).
        // Gemini requires turns that start with "user" and strictly alternate user/model,
        // so drop leading model turns and merge consecutive same-role turns
        // (the greeting, or a user message whose reply failed earlier).
        val turns = mutableListOf<Pair<String, String>>() // role to text
        for (m in history.takeLast(MAX_HISTORY)) {
            val role = if (m.isSentByMe) "user" else "model"
            if (turns.isEmpty() && role == "model") continue
            if (turns.lastOrNull()?.first == role) {
                val last = turns.removeAt(turns.lastIndex)
                turns.add(role to (last.second + "\n" + m.text))
            } else {
                turns.add(role to m.text)
            }
        }

        // The final turn is the user's new message; everything before it is chat history.
        val finalText = turns.lastOrNull()?.takeIf { it.first == "user" }?.second ?: userMessage
        val prior = if (turns.lastOrNull()?.first == "user") turns.dropLast(1) else turns

        val chat = model.startChat(
            history = prior.map { (role, text) -> content(role) { text(text) } }
        )

        val reply = chat.sendMessage(finalText).text
        if (reply.isNullOrBlank()) throw IllegalStateException("Empty response from Gemini")
        return reply.trim()
    }

    private companion object {
        const val MAX_HISTORY = 10

        const val SYSTEM_PROMPT = """
            You are a warm, supportive assistant inside a health-companion app for people
            living with endometriosis. Help users reflect on symptoms, understand general
            information, and feel heard. Keep replies short, clear and kind.
            You are not a doctor: do not diagnose or recommend specific medications or doses.
            Encourage users to contact a healthcare professional for medical decisions, and
            to seek urgent care for severe or sudden symptoms.
        """
    }
}