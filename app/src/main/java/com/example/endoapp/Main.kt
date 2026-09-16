package com.example.endoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

import com.example.endoapp.ui.Login
import com.example.endoapp.ui.SignUp

class Main : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)

        val loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val signUpButton = findViewById<Button>(R.id.signUp)
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}