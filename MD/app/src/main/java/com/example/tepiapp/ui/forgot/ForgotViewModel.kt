package com.example.tepiapp.ui.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForgotViewModel : ViewModel() {

    private val _resetStatus = MutableLiveData<Boolean>()
    val resetStatus: LiveData<Boolean> get() = _resetStatus

    fun resetPassword(email: String, newPassword: String) {
        // Simulating an API call or database interaction
        if (email.isNotEmpty() && newPassword.isNotEmpty()) {
            // Assume password reset is always successful
            _resetStatus.value = true
        } else {
            _resetStatus.value = false
        }
    }
}
