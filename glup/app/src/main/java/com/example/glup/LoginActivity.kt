package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class LoginActivity : AppCompatActivity() {

    private lateinit var logemail: EditText
    private lateinit var logpass: EditText
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.button_forgot).setOnClickListener {
            openforgotpass()
        }

        findViewById<Button>(R.id.button_new_user).setOnClickListener {
            openRegistrationActivity()
        }

        logemail  = findViewById<EditText>(R.id.login_email)
        logpass   = findViewById<EditText>(R.id.login_password)

        auth= FirebaseAuth.getInstance()

        findViewById<Button>(R.id.button_sign_in).setOnClickListener {
           ValidateLogin()
        }

    }

    private fun openforgotpass() {
        val intent = Intent(this, ForgotActivity::class.java)
        startActivity(intent)
    }

    private fun ValidateLogin() {

        if(!validateUser() or !validatePass()){
            return
        } else {
            isUser()
        }

    }


    private fun isUser() {
        val emailEntered = logemail.text.toString()
        val passEntered = logpass.text.toString()

        auth.signInWithEmailAndPassword(emailEntered, passEntered)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        openMainActivity()
                    } else {
                        Toast.makeText(this, "Error !!" + task.exception, Toast.LENGTH_LONG).show()
                    }
                }
    }




    private fun validateUser(): Boolean {
        val val_email = logemail.text.toString()

        if (val_email.isEmpty()){
            logemail.setError("Field cannot be empty")
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(val_email).matches()) {
            logemail.setError("Invalid Email address")
            return false
        }
        else {
            logemail.setError(null)
            return true
        }
    }

    private fun validatePass(): Boolean {
        val val_pass = logpass.text.toString()
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

        if (val_pass.isEmpty()){
            logpass.setError("Field cannot be empty")
            return false;
        }
        else {
            logpass.setError(null)
            return true;
        }
    }

    private fun openRegistrationActivity() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun openMainActivity(passname: String? = null) {

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name", passname)
        startActivity(intent)
    }
}