package com.example.endoapp

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class Main : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = TextView(this)
        text.text = "Hello, Android!"

        setContentView(text)
    }
}

fun main() {
    println("Hello, world!")
}