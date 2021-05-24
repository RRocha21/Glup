package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class SettingsActivity :AppCompatActivity(){
    //    lateinit var locationManager: LocationManager


    private lateinit var time: Button
    private lateinit var language: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        time = findViewById<Button>(R.id.tempo)
        language = findViewById<Button>(R.id.idioma)

        var time_read = 0.0



        time.setOnClickListener {
            val popupMenu:PopupMenu = PopupMenu(this, time)
            popupMenu.menu.add("1h00m")
            popupMenu.menu.add("0h30m")
            popupMenu.menu.add("0h15m")
            popupMenu.menu.add("0h10m")
            popupMenu.menu.add("0h05m")

            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                Toast.makeText(this, "You Selected  " + item.title, Toast.LENGTH_SHORT).show()
                time.setText(item.title)

                if (item.title.equals("1h00m")) time_read = 60.0
                if (item.title.equals("0h30m")) time_read = 30.0
                if (item.title.equals("0h15m")) time_read = 15.0
                if (item.title.equals("0h10m")) time_read = 10.0
                if (item.title.equals("0h05m")) time_read = 5.0


                true
            })
            true
            popupMenu.show()


            language.setOnClickListener{
                val popupMenu: PopupMenu = PopupMenu(this, language)
                popupMenu.menu.add("Português")
                popupMenu.menu.add("Inglês")
                popupMenu.menu.add("Espanhol")
                popupMenu.menu.add("Francês")
                popupMenu.menu.add("Alemão")

                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    Toast.makeText(this, "You Selected  " + item.title, Toast.LENGTH_SHORT).show()
                    language.setText(item.title)

                    true
                })
                true
                popupMenu.show()
            }
        }
    }
}
