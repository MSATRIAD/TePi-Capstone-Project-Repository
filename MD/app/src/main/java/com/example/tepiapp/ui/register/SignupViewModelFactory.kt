package com.example.tepiapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.api.ApiService
import com.example.tepiapp.data.pref.UserPreference

class SignupViewModelFactory(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(UserRepository.getInstance(userPreference, apiService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}