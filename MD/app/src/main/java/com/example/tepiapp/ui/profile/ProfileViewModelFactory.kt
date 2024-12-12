package com.example.tepiapp.ui.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tepiapp.data.UserRepository

class ProfileViewModelFactory(
    private val application: Application,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(application, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
