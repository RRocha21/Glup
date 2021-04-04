package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class RegistrationActivity : AppCompatActivity() {

    // initial variables

    private lateinit var regemail:EditText
    private lateinit var regpass: EditText
    private lateinit var reg_pass: EditText
    private lateinit var myAuth: FirebaseAuth

//------------------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        myAuth= FirebaseAuth.getInstance()
        // get what was written

        regemail = findViewById<EditText>(R.id.registration_email)
        regpass   = findViewById<EditText>(R.id.registration_password)
        reg_pass   = findViewById<EditText>(R.id.registration_password2)


        // When sign up button is pressed
        findViewById<Button>(R.id.button_next).setOnClickListener {
            RegisterUserActivity()

        }
        // When back button is pressed
        findViewById<Button>(R.id.button_old_user).setOnClickListener {
            openLoginActivity()
        }

    }


    private fun RegisterUserActivity() {

        if(!validatePass() or !validateEmail()) {
            return
        }

        // more variables and put the text in String format
        val email = regemail.text.toString()
        val pass = regpass.text.toString()

        createNewUser(email, pass)

        // going to next activity
        val intent = Intent(this, Registration2Activity::class.java)
        startActivity(intent)

    }


    private fun createNewUser(email: String?, password: String?) {

        myAuth.createUserWithEmailAndPassword(email, password)

    }

    private fun validatePass(): Boolean {
        val val_pass = regpass.text.toString()
        val val_pass2 = reg_pass.text.toString()
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

        if (val_pass.isEmpty()){
            regpass.setError("Field cannot be empty")
            return false;
        }
        else if (val_pass.count()<=6) {
            regpass.setError("Password must be bigger than 7")
            return false
        }
        else if (val_pass!=val_pass2){
            reg_pass.setError("Passwords must match")
            return false;
        }else {
            reg_pass.setError(null)
            regpass.setError(null)
            return true
        }


    }

    private fun validateEmail(): Boolean {
        val val_email = regemail.text.toString()

        if (val_email.isEmpty()){
            regemail.setError("Field cannot be empty")
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(val_email).matches()) {
            regemail.setError("Invalid Email address")
            return false
        }
        else {
            regemail.setError(null)
            return true
        }
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

 }

