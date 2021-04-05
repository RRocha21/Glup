package com.example.glup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase




class Registration2Activity : AppCompatActivity() {

    private lateinit var regname: EditText
    private lateinit var reguser: EditText
    private lateinit var regnif: EditText
    private lateinit var regphone:EditText
    private lateinit var user: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration2)



        // get what was written
        regname  = findViewById<EditText>(R.id.registration_full_name)
        reguser  = findViewById<EditText>(R.id.registration_username)
        regnif   = findViewById<EditText>(R.id.registration_nif)
        regphone = findViewById<EditText>(R.id.registration_phone)

        // When sign up button is pressed
        findViewById<Button>(R.id.button_sign_up).setOnClickListener {
            RegisterUserActivity()

        }
        // When back button is pressed
        findViewById<Button>(R.id.button_old_user2).setOnClickListener {
            openLoginActivity()
        }

    }


    private fun RegisterUserActivity() {

        if(!validateName() or !validateUser() or  !validatePhone() or !validateNif()) {
            return
        }

        // Prepare the Authentication
        val user= FirebaseAuth.getInstance().currentUser

        // Prepare the database
        val database = FirebaseDatabase.getInstance()
        val newuser = database.getReference("users")


        // more variables and put the text in String format
        val name = regname.text.toString()
        val userid = reguser.text.toString()
        val nif = regnif.text.toString()
        val phone = regphone.text.toString()
        val balance = 0
        val image = R.drawable.user_icon_app

        val new_user = UserHelper(userid, name, nif, phone,balance, image)


        // write in the database
        newuser.child(user.uid).setValue(new_user)
        newuser.child(user.uid).child("license").child("1").setValue("1")
        newuser.child(user.uid).child("tickets").child("1").setValue("1")


        // going to next activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    private fun validateName(): Boolean {
        val val_name = regname.text.toString()

        if (val_name.isEmpty()){
            regname.setError("Field cannot be empty")
            return false;
        }
        else {
            regname.setError(null)
            return true;
        }
    }

    private fun validateUser(): Boolean {
        val val_user = reguser.text.toString()
        val noWhiteSpace = ".*\\S+.*"

        if (val_user.isEmpty()){
            reguser.setError("Field cannot be empty")
            return false;
        }else if (val_user.count()>=15 ){
            reguser.setError("Username too long")
            return false
        }else if (val_user.contains(" ")) {
            reguser.setError("Spaces are not allowed")
            return false
        } else {
            reguser.setError(null)
            return true;
        }
    }

    private fun validatePhone(): Boolean {
        val val_phone = regphone.text.toString()

        if (val_phone.isEmpty()) {
            regphone.setError(null)
            return true
        } else if (val_phone.count()>9 || val_phone.count()<9) {
            regphone.setError("Phone Number must be 9 numbers")
            return false
        } else {
            regphone.setError(null)
            return true
        }
    }

    private fun validateNif(): Boolean {
        val val_nif = regnif.text.toString()

        if (val_nif.isEmpty()) {
            regnif.setError(null)
            return true;
        }else if (val_nif.count()>9 || val_nif.count()<9) {
            regnif.setError("Nif must be 9 numbers")
            return false
        }else {
            regnif.setError(null)
            return true;
        }
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    data class UserHelper(val userid: String? = null, val fullname: String? = null, val nif: String? = null, val phoneno: String? = null, val balance: Int? = null, val image: Int? = null) {
        // Null default values create a no-argument default constructor, which is needed
        // for deserialization from a DataSnapshot.
    }

}