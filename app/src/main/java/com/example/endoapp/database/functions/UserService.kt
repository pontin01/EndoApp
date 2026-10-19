package com.example.endoapp.database.functions

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.endoapp.R
import com.example.endoapp.database.User
import com.example.endoapp.database.supabase
import com.example.endoapp.state.AuthHash
import com.example.endoapp.state.UserDetails
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import java.io.File
import java.security.MessageDigest

class UserService {
    suspend fun getAuthHash(email: String) : String {
        val user = supabase.from("user")
            .select(columns = Columns.list("auth_hash")) {
                filter {
                    eq("email", email)
                }
            }
            .decodeSingle<User>()

        return user.authHash
    }

    suspend fun getUser(authHash: String) : User {
        val user = supabase.from("user")
            .select(columns = Columns.list("email,username")) {
                filter {
                    eq("auth_hash", authHash)
                }
            }
            .decodeSingle<User>()

        return user
    }

    suspend fun createUser(email: String, username: String, password: String, context: Context) {
        val authHash = AuthHash().createAuthHash(email, password)

        // Create User on Supabase database
        val user = User(email, username, authHash)
        supabase.from("user")
            .insert(user)

        // create authHash and userDetails for app
        AuthHash().setAuthHashLocally(authHash, context)
        UserDetails().setUserDetails(user, context)
    }


}