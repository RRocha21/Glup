package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<Button>(R.id.button_profile_balance).setOnClickListener {
            openBalanceActivity()
        }
        findViewById<Button>(R.id.button_profile_license_plates).setOnClickListener {
            openLicensePlatesActivity()
        }
        findViewById<Button>(R.id.button_profile_tickets).setOnClickListener {
            openTicketsActivity()
        }
    }

    private fun openBalanceActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun openLicensePlatesActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun openTicketsActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
