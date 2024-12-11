package com.example.tepiapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginStatus = MutableLiveData<String>()
    val isLoginSuccess = MutableLiveData<Boolean>()

    // Fungsi untuk validasi login
    fun login() {
        val emailInput = email.value?.trim()
        val passwordInput = password.value?.trim()

        if (emailInput.isNullOrEmpty() || passwordInput.isNullOrEmpty()) {
            loginStatus.value = "Please fill in both fields"
            isLoginSuccess.value = false
        } else {
            // Validasi dummy
            if (emailInput == "test@example.com" && passwordInput == "password") {
                loginStatus.value = "Login successful"
                isLoginSuccess.value = true
            } else {
                loginStatus.value = "Invalid email or password"
                isLoginSuccess.value = false
            }
        }
    }
}
