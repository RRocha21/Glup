package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


class RegistrationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        findViewById<Button>(R.id.button_sign_up).setOnClickListener {
            val regname  = findViewById<EditText>(R.id.registration_full_name).text.toString()
            val reguser  = findViewById<EditText>(R.id.registration_username).text.toString()
            val regemail = findViewById<EditText>(R.id.registration_email).text.toString()
            val regpss  = findViewById<EditText>(R.id.registration_password).text.toString()
            val regnif   = findViewById<EditText>(R.id.registration_nif).text.toString()
            val regphone = findViewById<EditText>(R.id.registration_phone).text.toString()
            openMainActivity()
        }

        findViewById<Button>(R.id.button_old_user).setOnClickListener {
            openLoginActivity()
        }

    }


    private fun openMainActivity() {
        // variables
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}