package com.example.glup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)


        findViewById<Button>(R.id.next_forgot).setOnClickListener(){
            openValidateCode()
        }


    }

    private fun openValidateCode() {
        val intent=Intent(this,Forgot2Activity::class.java)
        startActivity(intent)
    }


}