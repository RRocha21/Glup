package com.example.glup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)


        findViewById<Button>(R.id.next_forgot).setOnClickListener(){
            openNext()
        }


    }

    private fun openNext() {
        val intent=Intent(this,Forgot2Activity::class.java)
        startActivity(intent)
    }


}