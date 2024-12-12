package com.example.tepiapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.UserRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {

    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val signupStatus = MutableLiveData<String>()
    val isSignupSuccess = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun signup() {
        val emailInput = email.value?.trim() ?: ""
        val usernameInput = username.value?.trim() ?: ""
        val passwordInput = password.value?.trim() ?: ""
        val confirmPasswordInput = confirmPassword.value?.trim() ?: ""

        when {
            emailInput.isEmpty() || usernameInput.isEmpty() ||
                    passwordInput.isEmpty() || confirmPasswordInput.isEmpty() -> {
                updateSignupStatus("Please fill in all fields", false)
                return
            }
            passwordInput != confirmPasswordInput -> {
                updateSignupStatus("Passwords do not match", false)
                return
            }
        }

        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = userRepository.register(usernameInput, emailInput, passwordInput)
                isLoading.value = false
                if (response.error) {
                    updateSignupStatus(response.message, false)
                } else {
                    updateSignupStatus(response.message, true)
                }
            } catch (e: Exception) {
                isLoading.value = false
                updateSignupStatus("Error: ${e.localizedMessage}", false)
            }
        }
    }

    private fun updateSignupStatus(message: String, isSuccess: Boolean) {
        isSignupSuccess.postValue(isSuccess)
        signupStatus.postValue(message)
    }
}
