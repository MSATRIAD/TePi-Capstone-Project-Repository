package com.example.tepiapp.ui.profile

import android.app.Application
import android.content.Context
import android.content.Intent
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
                // Make sure image size is valid
                if (imageUri != null && !isImageSizeValid(context, imageUri)) {
                    throw Exception("Image size should be less than 5MB")
                }

                // Call the repository method to update the profile
                val response = userRepository.editProfile(displayName, imageUri, context)
                if (response != null) {
                    _profileUpdateResult.postValue(response)
                    _updateProfileStatus.postValue(true)
                } else {
                    _updateProfileStatus.postValue(false)
                }
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
