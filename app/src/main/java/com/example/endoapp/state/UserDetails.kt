package com.example.endoapp.state

import android.content.Context
import com.example.endoapp.database.User
import java.io.File


class UserDetails {
    fun getUserDetails(context: Context) : User {
        /**
         * Gets the User's details from the userDetails in context.filesDir
         */
        val userDetailsFile = File(context.filesDir, "userDetails")

        if (!userDetailsFile.exists()) {

        }

        val lines = userDetailsFile.readLines()

        val email = lines.get(0)
        val username = lines.get(1)

        return User(email, username)
    }

    fun setUserDetails(user: User, context: Context) {
        /**
         * Stores the details for a user into userDetails in context.filesDir.
         */
        val userDetailsFile = File(context.filesDir, "userDetails")
        val input = user.email + "\n" + user.username

        userDetailsFile.writeText(input)
    }

    fun clearUserDetails(context: Context) {
        val userDetailsFile = File(context.filesDir, "userDetails")
        userDetailsFile.writeText("")
    }
}