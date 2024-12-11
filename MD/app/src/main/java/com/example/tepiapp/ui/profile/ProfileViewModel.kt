package com.example.tepiapp.ui.profile

import android.app.Application
import android.content.Context
import android.content.Intent
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

    private val _username = MutableLiveData<String>().apply {
        value = "Username"
    }
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>().apply {
        value = "username@gmail.com"
    }
    val email: LiveData<String> = _email

    private val _isDarkMode = MutableLiveData<Boolean>().apply {
        value = getStoredTheme()
    }
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    init {
        toggleTheme(_isDarkMode.value ?: false)
    }

    fun setDarkMode(isDarkMode: Boolean) {
        _isDarkMode.value = isDarkMode

        sharedPrefs.edit().putBoolean("dark_mode", isDarkMode).apply()

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
        return sharedPrefs.getBoolean("dark_mode", false)
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
