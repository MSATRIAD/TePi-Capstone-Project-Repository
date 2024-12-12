package com.example.tepiapp.ui.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.api.ApiService
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.response.ResetPasswordRequest
import com.example.tepiapp.data.response.ResetPasswordResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ForgotViewModel(
    private val apiService: ApiService,
    private val userPreference: UserPreference // Add this line to accept UserPreference
) : ViewModel() {

    private val _resetPasswordResponse = MutableLiveData<ResetPasswordResponse?>()
    val resetPasswordResponse: LiveData<ResetPasswordResponse?> = _resetPasswordResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun resetPassword(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val request = ResetPasswordRequest(email)
                val response = apiService.resetPassword(request) // Calling the resetPassword API
                _resetPasswordResponse.value = response
            } catch (e: Exception) {
                _resetPasswordResponse.value = null
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
