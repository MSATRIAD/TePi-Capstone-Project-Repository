package com.example.tepiapp.ui.forgot

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.datastore.preferences.preferencesDataStore
import com.example.tepiapp.R
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.api.ApiService
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.pref.dataStore
import com.example.tepiapp.databinding.ActivityForgotBinding


class ForgotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotBinding
    private lateinit var forgotViewModel: ForgotViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UserPreference and ApiService
        val userPreference = UserPreference.getInstance(applicationContext.dataStore)
        val token = "" // Retrieve the actual token if available
        val apiService = ApiConfig.getApiService(token)

        // Create ViewModel factory and initialize ForgotViewModel
        val factory = ForgotViewModelFactory(apiService, userPreference)
        forgotViewModel = ViewModelProvider(this, factory).get(ForgotViewModel::class.java)

        binding.confirmButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                forgotViewModel.resetPassword(email)  // This triggers the resetPassword function in the ViewModel
            } else {
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        forgotViewModel.resetPasswordResponse.observe(this, Observer { response ->
            if (response != null) {
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed to reset password. Please try again.", Toast.LENGTH_LONG).show()
            }
        })

        forgotViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                // Show loading indicator
            } else {
                // Hide loading indicator
            }
        })
    }
}
