package com.example.endoapp.database

import kotlinx.serialization.Serializable

@Serializable
data class Faq(
    val question: String,
    val answer: String,
    val tag: String,
    val likes: Int,
    var expanded: Boolean = false,
) {
}