package com.example.endoapp.database.functions

import com.example.endoapp.database.SupabaseClient
import com.example.endoapp.database.User
import io.github.jan.supabase.postgrest.from

class UserService(private val user: User) {

    private val supabase = SupabaseClient.supabase

    suspend fun usernameExists(): Boolean {
        return supabase
            .from("user")
            .select {
                filter {
                    eq("username", user.username)
                }
            }
            .decodeList<User>()
            .isNotEmpty()
    }

    suspend fun emailExists(): Boolean {
        return supabase
            .from("user")
            .select {
                filter {
                    eq("email", user.email)
                }
            }
            .decodeList<User>()
            .isNotEmpty()
    }

    suspend fun createUser() {
        supabase
            .from("user")
            .insert(
                user
            )
    }
}