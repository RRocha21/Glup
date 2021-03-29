package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class RegistrationActivity : AppCompatActivity() {

    // initial variables
    private lateinit var regname: EditText
    private lateinit var reguser: EditText
    private lateinit var regemail:EditText
    private lateinit var regpass: EditText
    private lateinit var regnif: EditText
    private lateinit var regphone:EditText

//------------------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // get what was written
        regname  = findViewById<EditText>(R.id.registration_full_name)
        reguser  = findViewById<EditText>(R.id.registration_username)
        regemail = findViewById<EditText>(R.id.registration_email)
        regpass   = findViewById<EditText>(R.id.registration_password)
        regnif   = findViewById<EditText>(R.id.registration_nif)
        regphone = findViewById<EditText>(R.id.registration_phone)

        // When sign up button is pressed
        findViewById<Button>(R.id.button_sign_up).setOnClickListener {
            openMainActivity()
        }
        // When back button is pressed
        findViewById<Button>(R.id.button_old_user).setOnClickListener {
            openLoginActivity()
        }

    }


    private fun openMainActivity() {
        // Prepare the database
        val database = FirebaseDatabase.getInstance()
        val newuser = database.getReference("users")

        // more variables and put the text in String format
        val name = regname.text.toString()
        val userid = reguser.text.toString()
        val email = regemail.text.toString()
        val pass = regpass.text.toString()
        val nif = regnif.text.toString()
        val phone = regphone.text.toString()

        // sending the info to data class
        val new_user = UserHelper(userid,name,email,pass,nif,phone)

        // write in the database
        newuser.child(userid).setValue(new_user)

        // going to next activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    data class UserHelper(val userid: String? = null, val fullname:String? = null, val email: String? = null, val password: String? = null, val nif: String? = null, val phoneno: String? = null) {
        // Null default values create a no-argument default constructor, which is needed
        // for deserialization from a DataSnapshot.
    }
 }