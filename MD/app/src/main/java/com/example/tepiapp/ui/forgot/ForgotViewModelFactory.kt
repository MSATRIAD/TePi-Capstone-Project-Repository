package com.example.tepiapp.ui.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.api.ApiService
import com.example.tepiapp.data.pref.UserPreference

class ForgotViewModelFactory(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForgotViewModel::class.java)) {
            return ForgotViewModel(apiService, userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
