package com.example.tepiapp.ui.save

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tepiapp.data.UserRepository

class SaveViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SaveViewModel::class.java)) {
            SaveViewModel(userRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}