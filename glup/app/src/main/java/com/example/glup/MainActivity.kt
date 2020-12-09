package com.example.glup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_gps).setOnClickListener {
            openMapActivity()
        }

        findViewById<Button>(R.id.button_pay).setOnClickListener {
            openPaymentsActivity()
        }

        findViewById<Button>(R.id.button_profile).setOnClickListener {
            openProfileActivity()
        }

        findViewById<Button>(R.id.button_settings).setOnClickListener {
            openSettingsActivity()
        }
    }

    private fun openMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
    private fun openPaymentsActivity() {
        val intent = Intent(this, PaymentsActivity::class.java)
        startActivity(intent)
    }
    private fun openProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
    private fun openSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}