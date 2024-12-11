package com.example.tepiapp.ui.profile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.response.EditProfileResponse
import com.example.tepiapp.data.response.ProfileResponse
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.cancellation.CancellationException

class ProfileViewModel(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    private val _profile = MutableLiveData<ProfileResponse>()
    val profile: LiveData<ProfileResponse> = _profile

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

    fun fetchProfile() {
        viewModelScope.launch {
            try {
                val profileResponse = userRepository.getProfile()
                _profile.value = profileResponse

                _username.value = profileResponse.displayName
                _email.value = profileResponse.email
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _profileUpdateResult = MutableLiveData<EditProfileResponse>()
    val profileUpdateResult: LiveData<EditProfileResponse> = _profileUpdateResult

    private val _updateProfileStatus = MutableLiveData<Boolean>()
    val updateProfileStatus: LiveData<Boolean> = _updateProfileStatus


    fun updateProfile(context: Context, displayName: String, imageUri: Uri?) {
        viewModelScope.launch {
            try {
                if (imageUri != null && !isImageSizeValid(context, imageUri)) {
                    throw Exception("Image size should be less than 5MB")
                }
                val response = userRepository.editProfile(displayName, imageUri, context)
                _profileUpdateResult.postValue(response)
                _updateProfileStatus.postValue(true)
            } catch (e: CancellationException) {
                Log.e("ProfileViewModel", "Coroutine was cancelled: ${e.message}")
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error updating profile: ${e.message}")
                _updateProfileStatus.postValue(false)
            }
        }
    }

    private fun isImageSizeValid(context: Context, uri: Uri): Boolean {
        val file = File(uri.path ?: "")
        return file.length() <= 5 * 1024 * 1024 // 5MB dalam byte
    }
}
