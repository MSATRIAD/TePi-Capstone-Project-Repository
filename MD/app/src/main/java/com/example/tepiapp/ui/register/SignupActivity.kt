package com.example.tepiapp.ui.register

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tepiapp.EmailCustomView
import com.example.tepiapp.MyEditText
import com.example.tepiapp.PasswordCustomView
import com.example.tepiapp.R
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.pref.dataStore
import com.example.tepiapp.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SignupActivity : AppCompatActivity() {

    private lateinit var edSignUpEmail: EmailCustomView
    private lateinit var edSignUpUsername: TextInputEditText
    private lateinit var edSignUpPassword: PasswordCustomView
    private lateinit var edConfirmPassword: MyEditText
    private lateinit var signUpButton: Button
    private lateinit var progressBar: ProgressBar

    private val signupViewModel: SignupViewModel by viewModels {
        val pref = UserPreference.getInstance(applicationContext.dataStore)
        val token = runBlocking { pref.getSession().first().token }

        SignupViewModelFactory(
            pref,
            ApiConfig.getApiService(token)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Views
        edSignUpEmail = findViewById(R.id.ed_sign_up_email)
        edSignUpUsername = findViewById(R.id.ed_sign_up_username)
        edSignUpPassword = findViewById(R.id.ed_sign_up_password)
        edConfirmPassword = findViewById(R.id.ed_confirm_password)
        signUpButton = findViewById(R.id.signUpButton)
        progressBar = findViewById(R.id.progressBar)

        // Initialize Sign In TextView
        val signInTextView: TextView = findViewById(R.id.signIn)
        signInTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Handle sign-up button click
        signUpButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            val email = edSignUpEmail.text.toString().trim()
            val username = edSignUpUsername.text.toString().trim()
            val password = edSignUpPassword.text.toString().trim()
            val confirmPassword = edConfirmPassword.text.toString().trim()

            // Input validation
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                progressBar.visibility = View.GONE
                showAlertDialog(
                    title = "Kesalahan",
                    message = "Semua kolom harus diisi."
                )
            } else if (password != confirmPassword) {
                progressBar.visibility = View.GONE
                showAlertDialog(
                    title = "Kesalahan",
                    message = "Password dan Konfirmasi Password tidak cocok."
                )
            } else {
                signupViewModel.email.value = email
                signupViewModel.username.value = username
                signupViewModel.password.value = password
                signupViewModel.confirmPassword.value = confirmPassword
                signupViewModel.signup()
            }
        }

        signupViewModel.signupStatus.observe(this) { status ->
            progressBar.visibility = View.GONE

            // Ambil nilai langsung dari LiveData untuk memastikan status sinkron
            val isSuccess = signupViewModel.isSignupSuccess.value ?: false

            if (isSuccess) {
                // Tampilkan dialog sukses
                showAlertDialog(
                    title = "Pendaftaran Berhasil",
                    message = status,
                    onPositiveAction = {
                        // Navigasi ke LoginActivity
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            } else {
                // Tampilkan dialog gagal
                showAlertDialog(
                    title = "Pendaftaran Gagal",
                    message = status
                )
            }
        }
    }

    private fun showAlertDialog(title: String, message: String, onPositiveAction: (() -> Unit)? = null) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                onPositiveAction?.invoke() // Invoke the action if provided
            }
            create()
            show()
        }
    }
}
