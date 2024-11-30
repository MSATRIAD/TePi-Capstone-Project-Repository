package com.example.tepiapp.ui.catalog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.response.ListProductItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogViewModel : ViewModel() {

    private val _productList = MutableLiveData<List<ListProductItem>>()
    val productList: LiveData<List<ListProductItem>> = _productList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    companion object {
        private const val TAG = "CatalogViewModel"
    }

    init {
        fetchProducts()
    }

    fun fetchProducts(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getProducts()
        client.enqueue(object : Callback<List<ListProductItem>> {
            override fun onResponse(call: Call<List<ListProductItem>>, response: Response<List<ListProductItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _productList.value = response.body() ?: emptyList()
                    Log.d(TAG, "Fetched products: ${_productList.value}")
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ListProductItem>>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Failure: ${t.message}"
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}