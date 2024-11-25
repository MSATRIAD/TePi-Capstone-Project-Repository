package com.example.tepiapp.ui.profile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.appcompat.app.AppCompatDelegate

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs: SharedPreferences =
        application.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    // Live data for user details
    private val _username = MutableLiveData<String>().apply {
        value = "Username"
    }
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>().apply {
        value = "username@gmail.com"
    }
    val email: LiveData<String> = _email

    // Live data for Dark Mode setting
    private val _isDarkMode = MutableLiveData<Boolean>().apply {
        value = sharedPrefs.getBoolean("dark_mode", false) // Load the saved dark mode preference
    }
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    init {
        // Apply the saved theme on initialization
        toggleTheme(sharedPrefs.getBoolean("dark_mode", false))
    }

    // Function to update the Dark Mode state
    fun setDarkMode(isDarkMode: Boolean) {
        _isDarkMode.value = isDarkMode
        // Save the dark mode setting in SharedPreferences
        sharedPrefs.edit().putBoolean("dark_mode", isDarkMode).apply()
        // Apply the theme change
        toggleTheme(isDarkMode)
    }

    private fun toggleTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            // Enable dark mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            // Disable dark mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
