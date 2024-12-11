package com.example.tepiapp.ui.forgot

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tepiapp.R
import com.example.tepiapp.databinding.ActivityForgotBinding

class ForgotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotBinding
    private val forgotViewModel: ForgotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the title text for the reset screen
        binding.resetTitle.text = getString(R.string.reset_your_password)

        // Confirm button listener
        binding.confirmButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString().trim()
            val newPassword = binding.password.text.toString().trim()
            val reEnteredPassword = binding.resetPassword.text.toString().trim()

            if (validateInputs(email, newPassword, reEnteredPassword)) {
                forgotViewModel.resetPassword(email, newPassword)
                observeViewModel()
            }
        }
    }

    private fun validateInputs(email: String, password: String, confirmPassword: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                binding.emailLayout.error = getString(R.string.error_email_required)
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailLayout.error = getString(R.string.error_invalid_email)
                false
            }
            TextUtils.isEmpty(password) -> {
                binding.passwordLayout.error = getString(R.string.error_password_required)
                false
            }
            password.length < 6 -> {
                binding.passwordLayout.error = getString(R.string.error_password_too_short)
                false
            }
            password != confirmPassword -> {
                binding.reEnterLayout.error = getString(R.string.error_passwords_not_matching)
                false
            }
            else -> {
                // Clear any previous errors
                binding.emailLayout.error = null
                binding.passwordLayout.error = null
                binding.reEnterLayout.error = null
                true
            }
        }
    }

    private fun observeViewModel() {
        forgotViewModel.resetStatus.observe(this) { status ->
            if (status) {
                Toast.makeText(this, getString(R.string.password_reset_success), Toast.LENGTH_SHORT).show()
                finish() // Close activity on success
            } else {
                Toast.makeText(this, getString(R.string.password_reset_failed), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
