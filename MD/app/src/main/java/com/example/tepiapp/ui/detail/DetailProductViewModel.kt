package com.example.tepiapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.response.ListDetailItem

class DetailProductViewModel : ViewModel() {

    private lateinit var userRepository: UserRepository

    private val _productDetail = MutableLiveData<ListDetailItem?>()
    val productDetail: LiveData<ListDetailItem?> = _productDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun setUserRepository(userRepository: UserRepository) {
        this.userRepository = userRepository
    }

    suspend fun getProductDetails(productId: String) {
        _isLoading.value = true
        try {
            val productDetail = userRepository.getDetailProducts(productId)  // Using UserRepository here
            _productDetail.value = productDetail
        } catch (e: Exception) {
            _errorMessage.value = e.message ?: "Unknown error"
        } finally {
            _isLoading.value = false
        }
    }
}
