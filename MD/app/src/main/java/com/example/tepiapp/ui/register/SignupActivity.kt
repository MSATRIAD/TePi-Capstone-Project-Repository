package com.example.tepiapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tepiapp.R
import com.example.tepiapp.ui.catalog.CatalogFragment
import com.example.tepiapp.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputEditText

class SignupActivity : AppCompatActivity() {
    private lateinit var edSignUpEmail: TextInputEditText
    private lateinit var edSignUpUsername: TextInputEditText
    private lateinit var edSignUpPassword: TextInputEditText
    private lateinit var edConfirmPassword: TextInputEditText
    private lateinit var signUpButton: Button
    private lateinit var signInText: TextView

    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        edSignUpEmail = findViewById(R.id.ed_sign_up_email)
        edSignUpUsername = findViewById(R.id.ed_sign_up_username)
        edSignUpPassword = findViewById(R.id.ed_sign_up_password)
        edConfirmPassword = findViewById(R.id.ed_confirm_password)
        signUpButton = findViewById(R.id.signUpButton)
        signInText = findViewById(R.id.signIn)

        signupViewModel.signupStatus.observe(this) { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }

        signupViewModel.isSignupSuccess.observe(this) { success ->
            if (success) {
                val intent = Intent(this, CatalogFragment::class.java)
                startActivity(intent)
                finish()
            }
        }

        signUpButton.setOnClickListener {
            signupViewModel.email.value = edSignUpEmail.text.toString().trim()
            signupViewModel.username.value = edSignUpUsername.text.toString().trim()
            signupViewModel.password.value = edSignUpPassword.text.toString().trim()
            signupViewModel.confirmPassword.value = edConfirmPassword.text.toString().trim()
            signupViewModel.signup()
        }

        signInText.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
    }
}
