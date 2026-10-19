package com.example.endoapp.database

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val username: String,

    @SerialName("auth_hash")
    val authHash: String = "",
)