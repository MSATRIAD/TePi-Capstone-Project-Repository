package com.example.tepiapp.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.response.LoginRequest
import com.example.tepiapp.data.response.LoginResponse
import com.example.tepiapp.data.api.ApiService
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.pref.UserModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginStatus = MutableLiveData<String>()
    val isLoginSuccess = MutableLiveData<Boolean>()

    fun login() {
        val emailInput = email.value?.trim()
        val passwordInput = password.value?.trim()

        if (emailInput.isNullOrEmpty() || passwordInput.isNullOrEmpty()) {
            loginStatus.value = "Please fill in both fields"
            isLoginSuccess.value = false
            return
        }

        viewModelScope.launch {
            try {
                val response = apiService.login(LoginRequest(emailInput, passwordInput))
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && !loginResponse.error) {
                        val userModel = UserModel(
                            email = emailInput,
                            token = loginResponse.token,
                            isLogin = true
                        )
                        saveSession(userModel)
                        loginStatus.value = "Login successful: ${loginResponse.message}"
                        isLoginSuccess.value = true
                    } else {
                        loginStatus.value = loginResponse?.message ?: "Invalid credentials"
                        isLoginSuccess.value = false
                    }
                } else {
                    loginStatus.value = "Login failed: ${response.message()}"
                    isLoginSuccess.value = false
                }
            } catch (e: IOException) {
                loginStatus.value = "Network error: ${e.message}"
                isLoginSuccess.value = false
            } catch (e: HttpException) {
                loginStatus.value = "Server error: ${e.message}"
                isLoginSuccess.value = false
            }
        }
    }

    private suspend fun saveSession(userModel: UserModel) {
        userPreference.saveSession(userModel)  // Save user session with token
    }
}
