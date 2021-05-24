package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class BalanceActivity :AppCompatActivity(){
    private lateinit var mbalance: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        mbalance = findViewById<TextView>(R.id.saldo)
        showdetails()

    }

    private fun showdetails() {
        val user= FirebaseAuth.getInstance().currentUser

        val myRef= FirebaseDatabase.getInstance().getReference("users")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {

                    val balancefromDb = dataSnapshot.child(user.uid).child("balance").getValue<Double>()

                    mbalance.text= Editable.Factory.getInstance().newEditable(balancefromDb.toString().plus("$"))

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        myRef.addValueEventListener(postListener)
    }
}