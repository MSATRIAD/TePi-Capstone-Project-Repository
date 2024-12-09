// SignupViewModel.kt
package com.example.tepiapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.response.RegisterRequest
import com.example.tepiapp.data.response.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {

    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val signupStatus = MutableLiveData<String>()
    val isSignupSuccess = MutableLiveData<Boolean>()

    fun signup() {
        val emailInput = email.value?.trim()
        val usernameInput = username.value?.trim()
        val passwordInput = password.value?.trim()
        val confirmPasswordInput = confirmPassword.value?.trim()

        if (emailInput.isNullOrEmpty() || usernameInput.isNullOrEmpty() || passwordInput.isNullOrEmpty() || confirmPasswordInput.isNullOrEmpty()) {
            signupStatus.value = "Please fill in all fields"
            isSignupSuccess.value = false
            return
        }

        if (passwordInput != confirmPasswordInput) {
            signupStatus.value = "Passwords do not match"
            isSignupSuccess.value = false
            return
        }

        // Use viewModelScope.launch to call the register function in a coroutine
        viewModelScope.launch {
            try {
                val response = userRepository.register(usernameInput, emailInput, passwordInput)
                signupStatus.value = response.message
                isSignupSuccess.value = !response.error
            } catch (e: Exception) {
                signupStatus.value = "Error: ${e.localizedMessage}"
                isSignupSuccess.value = false
            }
        }

    }
}
