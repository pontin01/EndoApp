package com.example.endoapp.database

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    var username: String,
    var email: String,

    @SerialName("password_hash")
    var passwordHash: String? = null,

)
