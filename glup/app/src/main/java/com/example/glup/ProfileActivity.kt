package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import org.w3c.dom.Text

class ProfileActivity: AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var pname: EditText
    private lateinit var pemail: EditText
    private lateinit var ppass: EditText
    private lateinit var pnif: EditText
    private lateinit var pphone: EditText
    private lateinit var pbalance: TextView
    private lateinit var pfname: TextView
    private lateinit var puser: TextView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        pfname = findViewById<TextView>(R.id.full_name)
        puser = findViewById<TextView>(R.id.user_name)
        pbalance = findViewById<TextView>(R.id.payment_label)
        pemail = findViewById<EditText>(R.id.change_email)
        ppass   = findViewById<EditText>(R.id.change_pass)
        pnif   = findViewById<EditText>(R.id.change_nif)
        pphone   = findViewById<EditText>(R.id.change_phone)
        pname   = findViewById<EditText>(R.id.change_name)


        showdetails()

        findViewById<Button>(R.id.button_profile_balance).setOnClickListener {
            openBalanceActivity()
        }
        findViewById<Button>(R.id.button_profile_license_plates).setOnClickListener {
            openLicensePlatesActivity()
        }
        findViewById<Button>(R.id.button_profile_tickets).setOnClickListener {
            openTicketsActivity()
        }
        findViewById<Button>(R.id.button_profile_photo).setOnClickListener {
            openPhotoActivity()
        }
        findViewById<Button>(R.id.update_profile).setOnClickListener{
            updateprofile()
        }
    }

    private fun showdetails() {
        val user= FirebaseAuth.getInstance().currentUser

        val myRef= FirebaseDatabase.getInstance().getReference("users")

        val emailfromDb = user.email


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    val balancefromDb = dataSnapshot.child(user.uid).child("balance").getValue<Int>()
                    val fullnamefromDb = dataSnapshot.child(user.uid).child("fullname").getValue<String>()
                    val imagefromDb = dataSnapshot.child(user.uid).child("image").getValue<Int>()
                    val niffromDb = dataSnapshot.child(user.uid).child("nif").getValue<String>()
                    val phonefromDb = dataSnapshot.child(user.uid).child("phoneno").getValue<String>()
                    val useridfromDb = dataSnapshot.child(user.uid).child("userid").getValue<String>()

                    pbalance.text=Editable.Factory.getInstance().newEditable(balancefromDb.toString().plus("$"))
                    pfname.text = Editable.Factory.getInstance().newEditable(fullnamefromDb)
                    puser.text = Editable.Factory.getInstance().newEditable(useridfromDb)
                    pname.text = Editable.Factory.getInstance().newEditable(fullnamefromDb)
                    pnif.text = Editable.Factory.getInstance().newEditable(niffromDb)
                    pphone.text = Editable.Factory.getInstance().newEditable(phonefromDb)
                    pemail.text=Editable.Factory.getInstance().newEditable(emailfromDb)

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                }
        }
        myRef.addValueEventListener(postListener)
    }

    private fun updateprofile() {

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
    private fun openPhotoActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
