package com.example.glup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query


class LoginActivity : AppCompatActivity() {

    private lateinit var loguser: EditText
    private lateinit var logpass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.button_forgot).setOnClickListener {
            openforgotpass()
        }


        loguser  = findViewById<EditText>(R.id.login_username)
        logpass   = findViewById<EditText>(R.id.login_password)

        findViewById<Button>(R.id.button_sign_in).setOnClickListener {
           ValidateLogin()
        }

        findViewById<Button>(R.id.button_new_user).setOnClickListener {
            openRegistrationActivity()
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


        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    private fun isUser() {
        val usernameEntered = loguser.text.toString()
        val passEntered = logpass.text.toString()

        val myRef = FirebaseDatabase.getInstance().getReference("users")

        val checkUser: Query = myRef.orderByChild("username").equalTo(usernameEntered)

    }
    private fun validateUser(): Boolean {
        val val_user = loguser.text.toString()
        val noWhiteSpace = ".*\\S+.*"

        if (val_user.isEmpty()){
            loguser.setError("Field cannot be empty")
            return false;
        } else {
            loguser.setError(null)
            return true;
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
}