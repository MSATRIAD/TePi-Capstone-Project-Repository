//package com.example.tepiapp
//
//import android.content.SharedPreferences
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.tepiapp.data.api.ApiService
//import com.example.tepiapp.ui.login.LoginViewModel
//
//class ViewModelFactory(
//    private val preferences: SharedPreferences,
//    private val apiService: ApiService
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            return LoginViewModel(preferences, apiService) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
