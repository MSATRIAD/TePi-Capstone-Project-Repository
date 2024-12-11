package com.example.tepiapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.tepiapp.MainActivity
import com.example.tepiapp.R
import com.example.tepiapp.ui.catalog.CatalogFragment
import com.example.tepiapp.ui.register.SignupActivity
import com.example.tepiapp.ui.forgot.ForgotActivity  // Import ForgotActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var edLoginEmail: TextInputEditText
    private lateinit var edLoginPassword: TextInputEditText
    private lateinit var signInButton: Button
    private lateinit var forgotPasswordText: TextView
    private lateinit var signUpText: TextView

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi Views
        edLoginEmail = findViewById(R.id.ed_login_email)
        edLoginPassword = findViewById(R.id.password)
        signInButton = findViewById(R.id.signInButton)
        forgotPasswordText = findViewById(R.id.forgotPassword)
        signUpText = findViewById(R.id.signUp)

        // Observasi status login
        loginViewModel.loginStatus.observe(this) { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }

        loginViewModel.isLoginSuccess.observe(this) { success ->
            if (success) {
                navigateToMainActivity()
            }
        }

        // Tombol Sign In klik event
        signInButton.setOnClickListener {
            loginViewModel.email.value = edLoginEmail.text.toString().trim()
            loginViewModel.password.value = edLoginPassword.text.toString().trim()
            loginViewModel.login()
        }

        // Forgot Password klik event
        forgotPasswordText.setOnClickListener {
            // Navigasi ke ForgotActivity
            val forgotPasswordIntent = Intent(this, ForgotActivity::class.java)
            startActivity(forgotPasswordIntent)
        }

        // Sign Up klik event
        signUpText.setOnClickListener {
            val signUpIntent = Intent(this, SignupActivity::class.java)
            startActivity(signUpIntent)
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Tutup LoginActivity agar tidak bisa kembali
    }
}
