package com.example.glup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Forgot2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot2)

        findViewById<Button>(R.id.verify_code).setOnClickListener(){
            openVerifyCode()
        }
    }

    private fun openVerifyCode() {
        val intent= Intent(this,Forgot3Activity::class.java)
        startActivity(intent)
    }

}