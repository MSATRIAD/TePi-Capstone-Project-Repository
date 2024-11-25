package com.example.tepiapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val signupStatus = MutableLiveData<String>()
    val isSignupSuccess = MutableLiveData<Boolean>()

    // Fungsi untuk validasi pendaftaran
    fun signup() {
        val emailInput = email.value?.trim()
        val usernameInput = username.value?.trim()
        val passwordInput = password.value?.trim()
        val confirmPasswordInput = confirmPassword.value?.trim()

        if (emailInput.isNullOrEmpty() || usernameInput.isNullOrEmpty() || passwordInput.isNullOrEmpty() || confirmPasswordInput.isNullOrEmpty()) {
            signupStatus.value = "Please fill in all fields"
            isSignupSuccess.value = false
        } else if (passwordInput != confirmPasswordInput) {
            signupStatus.value = "Passwords do not match"
            isSignupSuccess.value = false
        } else {
            // Logika registrasi berhasil
            signupStatus.value = "Sign up successful"
            isSignupSuccess.value = true
        }
    }
}
