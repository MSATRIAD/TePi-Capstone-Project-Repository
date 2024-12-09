package com.example.tepiapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tepiapp.EmailCustomView
import com.example.tepiapp.MainActivity
import com.example.tepiapp.PasswordCustomView
import com.example.tepiapp.R
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.pref.dataStore
import com.example.tepiapp.ui.register.SignupActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    private lateinit var edLoginEmail: EmailCustomView
    private lateinit var edLoginPassword: PasswordCustomView
    private lateinit var signInButton: Button
    private lateinit var signUpText: TextView

    private val loginViewModel: LoginViewModel by viewModels {
        val pref = UserPreference.getInstance(applicationContext.dataStore)
        val token = runBlocking { pref.getSession().first().token }

        // Pass UserPreference and ApiService to the factory correctly
        LoginViewModelFactory(
            pref,  // Pass UserPreference (not SharedPreferences)
            ApiConfig.getApiService(token)  // Pass ApiService from ApiConfig
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize the views
        edLoginEmail = findViewById(R.id.ed_login_email)
        edLoginPassword = findViewById(R.id.password)
        signInButton = findViewById(R.id.signInButton)
        signUpText = findViewById(R.id.signUp)

        loginViewModel.apply {
            loginStatus.observe(this@LoginActivity) { status ->
                Toast.makeText(this@LoginActivity, status, Toast.LENGTH_SHORT).show()
            }

            isLoginSuccess.observe(this@LoginActivity) { success ->
                if (success) navigateToMainActivity()
            }
        }

        signInButton.setOnClickListener {
            loginViewModel.email.value = edLoginEmail.text.toString()
            loginViewModel.password.value = edLoginPassword.text.toString()
            loginViewModel.login()
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
