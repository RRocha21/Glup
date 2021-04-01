package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val switch: Switch = findViewById(R.id.switch_dark)
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                openforgotpass()
            } else {
                // The toggle is disabled
            }
        }
    }
    private fun openforgotpass() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
