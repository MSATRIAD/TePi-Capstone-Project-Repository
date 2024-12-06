package com.example.tepiapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.response.RegisterRequest
import com.example.tepiapp.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {

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

        val request = RegisterRequest(usernameInput, emailInput, passwordInput)

        ApiConfig.getApiService().register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        signupStatus.value = it.message
                        isSignupSuccess.value = !it.error
                    }
                } else {
                    signupStatus.value = "Failed to register: ${response.message()}"
                    isSignupSuccess.value = false
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                signupStatus.value = "Error: ${t.localizedMessage}"
                isSignupSuccess.value = false
            }
        })
    }

//    val email = MutableLiveData<String>()
//    val username = MutableLiveData<String>()
//    val password = MutableLiveData<String>()
//    val confirmPassword = MutableLiveData<String>()
//    val signupStatus = MutableLiveData<String>()
//    val isSignupSuccess = MutableLiveData<Boolean>()
//
//    // Fungsi untuk validasi pendaftaran
//    fun signup() {
//        val emailInput = email.value?.trim()
//        val usernameInput = username.value?.trim()
//        val passwordInput = password.value?.trim()
//        val confirmPasswordInput = confirmPassword.value?.trim()
//
//        if (emailInput.isNullOrEmpty() || usernameInput.isNullOrEmpty() || passwordInput.isNullOrEmpty() || confirmPasswordInput.isNullOrEmpty()) {
//            signupStatus.value = "Please fill in all fields"
//            isSignupSuccess.value = false
//        } else if (passwordInput != confirmPasswordInput) {
//            signupStatus.value = "Passwords do not match"
//            isSignupSuccess.value = false
//        } else {
//            // Logika registrasi berhasil
//            signupStatus.value = "Sign up successful"
//            isSignupSuccess.value = true
//        }
//    }
}
