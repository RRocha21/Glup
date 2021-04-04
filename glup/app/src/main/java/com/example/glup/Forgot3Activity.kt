package com.example.glup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Forgot3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot3)

        findViewById<Button>(R.id.next_forgot3).setOnClickListener(){
            openVerifyCode()
        }
    }

    private fun openVerifyCode() {
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

}