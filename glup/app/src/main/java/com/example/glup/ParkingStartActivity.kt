package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class ParkingStartActivity : AppCompatActivity() {

    private lateinit var localvalue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)


//        findViewById<Button>(R.id.next_forgot).setOnClickListener(){
//            openNext()
//        }

//        val maxCounter: Long = 300000
//        val diff: Long = 1000
//
//        object : CountDownTimer(maxCounter, diff) {
//            override fun onTick(millisUntilFinished: Long) {
//                val diff = maxCounter - millisUntilFinished
//                mTextField.setText("seconds completed: " + diff / 1000)
//                //here you can have your logic to set text to edittext
//            }
//
//            override fun onFinish() {
//                mTextField.setText("done!")
//            }
//        }.start()
//
//    }

    fun showdetails() {

        val myRef= FirebaseDatabase.getInstance().getReference("locals")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    val localvaluefromDb = dataSnapshot.child("Universidade de Aveiro").child("localvalue").getValue<String>()

                    localvalue.text= Editable.Factory.getInstance().newEditable(localvaluefromDb.toString().plus("€"))
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        myRef.addValueEventListener(postListener)


        fun openNext() {
        val intent=Intent(this, Forgot2Activity::class.java)
        startActivity(intent)
    }


}}}