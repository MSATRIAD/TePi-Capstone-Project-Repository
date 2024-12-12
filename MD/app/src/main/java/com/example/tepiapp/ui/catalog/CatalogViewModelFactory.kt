package com.example.tepiapp.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tepiapp.data.UserRepository

class CatalogViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
            CatalogViewModel(userRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
