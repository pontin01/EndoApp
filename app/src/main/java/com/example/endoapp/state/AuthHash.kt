package com.example.endoapp.state

import android.content.Context
import java.io.File
import java.security.MessageDigest

class AuthHash {
    fun getAuthHashLocally(context: Context) : String {
        val file = File(context.filesDir, "authHash")

        if (!file.exists()) {
            setAuthHashLocally("", context)
            return ""
        }

        return file.readText()
    }

    fun setAuthHashLocally(authHash: String, context: Context) {
        val file = File(context.filesDir, "authHash")
        file.writeText(authHash)
    }

    fun createAuthHash(email: String, password: String) : String {
        return (email + password).sha256()
    }

    private fun String.sha256(): String {
        return MessageDigest.getInstance("SHA-256").digest(this.toByteArray()).fold("") {
                str, byte -> str + "%02x".format(byte)
        }
    }
}