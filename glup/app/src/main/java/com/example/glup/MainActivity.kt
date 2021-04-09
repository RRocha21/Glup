package com.example.glup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {

    private lateinit var mfname: TextView
    private lateinit var mbalance: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mfname = findViewById<TextView>(R.id.main_full_name)
        mbalance = findViewById<TextView>(R.id.main_payment_label)

        showdetails()

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

    private fun showdetails() {
        val user= FirebaseAuth.getInstance().currentUser

        val myRef= FirebaseDatabase.getInstance().getReference("users")



        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {

                    val balancefromDb = dataSnapshot.child(user.uid).child("balance").getValue<Int>()
                    val fullnamefromDb = dataSnapshot.child(user.uid).child("fullname").getValue<String>()

                    mbalance.text= Editable.Factory.getInstance().newEditable(balancefromDb.toString().plus("$"))
                    mfname.text = Editable.Factory.getInstance().newEditable(fullnamefromDb)

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        myRef.addValueEventListener(postListener)
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