package com.example.endoapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

import com.example.endoapp.R

class Login : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login)

        // Buttons
        val backButton = findViewById<ImageButton>(R.id.back)
        backButton.setOnClickListener {
            finish()
        }
        val goButton = findViewById<Button>(R.id.goButton)
        goButton.setOnClickListener {
            // login function call

            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        // Input Fields
    }

    fun login(email: String, password: String, passwordConfirmation: String) {

    }
}