package com.example.tepiapp.ui.login

import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.response.LoginRequest
import com.example.tepiapp.data.response.LoginResponse
import com.google.android.play.integrity.internal.c
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val preferences: SharedPreferences) : ViewModel() {
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

        val apiService = ApiConfig.getApiService(requireContext())
        val loginRequest = LoginRequest(emailInput, passwordInput)

        // Panggil API menggunakan Retrofit
        viewModelScope.launch {
            apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null && !loginResponse.error) {
                            // Simpan token di SharedPreferences
                            preferences.edit().apply {
                                putString("user_token", loginResponse.token)
                                apply()
                            }
                            loginStatus.value = "Login successful: ${loginResponse.message}"
                            isLoginSuccess.value = true
                        } else {
                            loginStatus.value = loginResponse?.message ?: "Unknown error occurred"
                            isLoginSuccess.value = false
                        }
                    } else {
                        loginStatus.value = "Login failed: ${response.message()}"
                        isLoginSuccess.value = false
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginStatus.value = "Network error: ${t.message}"
                    isLoginSuccess.value = false
                }
            })
        }
    }

    fun getToken(): String? {
        return preferences.getString("user_token", null)
    }
}
