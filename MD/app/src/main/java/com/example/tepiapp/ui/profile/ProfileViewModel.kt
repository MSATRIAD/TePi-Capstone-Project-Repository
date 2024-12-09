package com.example.tepiapp.ui.profile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    private val sharedPrefs: SharedPreferences =
        application.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    // Live data for user details
    private val _username = MutableLiveData<String>().apply {
        value = "Username"  // You should fetch the real username from a repository or API
    }
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>().apply {
        value = "username@gmail.com"  // You should fetch the real email from a repository or API
    }
    val email: LiveData<String> = _email

    // Live data for Dark Mode setting
    private val _isDarkMode = MutableLiveData<Boolean>().apply {
        value = getStoredTheme() // Load the saved dark mode preference from the shared preferences
    }
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    init {
        // Apply the saved theme on initialization
        toggleTheme(_isDarkMode.value ?: false)
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
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getStoredTheme(): Boolean {
        return sharedPrefs.getBoolean("dark_mode", false) // Retrieve the saved dark mode preference
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
