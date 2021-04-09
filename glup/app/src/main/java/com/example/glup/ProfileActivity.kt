package com.example.glup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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

        if(!validateName() or !validateEmail() or !validatePhone() or !validateNif()) {
            return
        }

        isDetailsChanged()
        isEmailChanged()

        Toast.makeText(this, "Details have changed sucessfully",Toast.LENGTH_LONG).show()

//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)

    }
    
    private fun validateEmail(): Boolean {
        val val_email = pemail.text.toString()

        if (val_email.isEmpty()){
            pemail.setError("Field cannot be empty")
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(val_email).matches()) {
            pemail.setError("Invalid Email address")
            return false
        }
        else {
            pemail.setError(null)
            return true
        }
    }

    private fun validateName(): Boolean {
        val val_name = pname.text.toString()

        if (val_name.isEmpty()){
            pname.setError("Field cannot be empty")
            return false;
        }
        else {
            pname.setError(null)
            return true;
        }
    }

    private fun validatePhone(): Boolean {
        val val_phone = pphone.text.toString()

        if (val_phone.isEmpty()) {
            pphone.setError(null)
            return true
        } else if (val_phone.count()>9 || val_phone.count()<9) {
            pphone.setError("Phone Number must be 9 numbers")
            return false
        } else {
            pphone.setError(null)
            return true
        }
    }

    private fun validateNif(): Boolean {
        val val_nif = pnif.text.toString()

        if (val_nif.isEmpty()) {
            pnif.setError(null)
            return true;
        }else if (val_nif.count()>9 || val_nif.count()<9) {
            pnif.setError("Nif must be 9 numbers")
            return false
        }else {
            pnif.setError(null)
            return true;
        }
    }

    private fun isEmailChanged(): Boolean {
        val user= FirebaseAuth.getInstance().currentUser

        val emailfromDb = user.email

        if(!pemail.equals(emailfromDb)){
            user.updateEmail(pemail.text.toString())
            Toast.makeText(this, "Details have changed sucessfully",Toast.LENGTH_LONG).show()
            return true
        } else {
            return false
        }

    }

    private fun isDetailsChanged() {
        val user= FirebaseAuth.getInstance().currentUser

        val myRef= FirebaseDatabase.getInstance().getReference("users")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    val phonefromDb = dataSnapshot.child(user.uid).child("phoneno").getValue<String>()
                    val namefromDb = dataSnapshot.child(user.uid).child("fullname").getValue<String>()
                    val niffromDb = dataSnapshot.child(user.uid).child("nif").getValue<String>()

                    if(!pphone.equals(phonefromDb)) {
                        myRef.child(user.uid).child("phoneno").setValue(pphone.text.toString())
                    }

                    if(!pnif.equals(niffromDb)) {
                        myRef.child(user.uid).child("nif").setValue(pnif.text.toString())
                    }

                    if(!pname.equals(namefromDb)) {
                        myRef.child(user.uid).child("fullname").setValue(pname.text.toString())
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)
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