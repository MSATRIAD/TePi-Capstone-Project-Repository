package com.example.tepiapp.ui.detail

import com.example.tepiapp.data.response.ListDetailItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tepiapp.data.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProductViewModel : ViewModel() {

    private lateinit var apiService: ApiService

    private val _productDetail = MutableLiveData<ListDetailItem?>()
    val productDetail: LiveData<ListDetailItem?> = _productDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun setApiService(apiService: ApiService) {
        this.apiService = apiService
    }

    fun getProductDetails(productId: String) {
        _isLoading.value = true
        apiService.getDetailProducts(productId).enqueue(object : Callback<ListDetailItem> {
            override fun onResponse(call: Call<ListDetailItem>, response: Response<ListDetailItem>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _productDetail.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<ListDetailItem>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Failure: ${t.message}"
            }
        })
    }
}

