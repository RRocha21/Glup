package com.example.glup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Button callSignUp;

        findViewById<Button>(R.id.button_sign_in).setOnClickListener {
            openMainActivity()
        }

        findViewById<Button>(R.id.button_new_user).setOnClickListener {
            openRegistrationActivity()
        }

        callSignUp = findViewById<Button>(R.id.button_new_user)

    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun openRegistrationActivity() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }
}