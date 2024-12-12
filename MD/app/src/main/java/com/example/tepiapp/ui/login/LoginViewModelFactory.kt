package com.example.tepiapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tepiapp.data.api.ApiService
import com.example.tepiapp.data.pref.UserPreference

class LoginViewModelFactory(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userPreference, apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
