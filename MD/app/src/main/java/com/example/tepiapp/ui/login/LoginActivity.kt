package com.example.tepiapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tepiapp.EmailCustomView
import com.example.tepiapp.MainActivity
import com.example.tepiapp.PasswordCustomView
import com.example.tepiapp.R
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.pref.dataStore
import com.example.tepiapp.ui.forgot.ForgotActivity
import com.example.tepiapp.ui.register.SignupActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    private lateinit var edLoginEmail: EmailCustomView
    private lateinit var edLoginPassword: PasswordCustomView
    private lateinit var signInButton: Button
    private lateinit var signUpText: TextView
    private lateinit var forgotPasswordText: TextView // New view for forgot password
    private lateinit var progressBar: ProgressBar

    private val loginViewModel: LoginViewModel by viewModels {
        val pref = UserPreference.getInstance(applicationContext.dataStore)
        val token = runBlocking { pref.getSession().first().token }

        LoginViewModelFactory(
            pref,
            ApiConfig.getApiService(token)
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
        forgotPasswordText = findViewById(R.id.forgotPasswordText) // Initialize forgot password text view
        progressBar = findViewById(R.id.progressBar)

        // Observe LiveData from ViewModel
        loginViewModel.apply {
            loginStatus.observe(this@LoginActivity) { status ->
                Toast.makeText(this@LoginActivity, status, Toast.LENGTH_SHORT).show()
            }

            isLoginSuccess.observe(this@LoginActivity) { success ->
                if (success) {
                    showAlertDialog("Berhasil", loginViewModel.successMessage) {
                        navigateToMainActivity()
                    }
                }
            }

            isLoading.observe(this@LoginActivity) { isLoading ->
                toggleLoading(isLoading)
            }
        }

        // Set up button click listeners
        signInButton.setOnClickListener {
            loginViewModel.email.value = edLoginEmail.text.toString().trim()
            loginViewModel.password.value = edLoginPassword.text.toString().trim()
            loginViewModel.login()
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        // Add listener for Forgot Password link
        forgotPasswordText.setOnClickListener {
            val intent = Intent(this, ForgotActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            val pref = UserPreference.getInstance(applicationContext.dataStore)
            val session = pref.getSession().first()
            if (session.token.isNotEmpty()) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            signInButton.isEnabled = false // Prevent multiple clicks
        } else {
            progressBar.visibility = View.GONE
            signInButton.isEnabled = true
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showAlertDialog(title: String, message: String, onPositiveAction: (() -> Unit)? = null) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                onPositiveAction?.invoke()
                dialog.dismiss()
            }
        }
        builder.create().show()
    }
}
