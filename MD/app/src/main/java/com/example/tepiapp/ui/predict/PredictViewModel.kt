package com.example.tepiapp.ui.predict

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PredictViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is Scan Fragment"
//    }
//    val text: LiveData<String> = _text

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> get() = _imageUri

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun clearImageUri() {
        _imageUri.value = null
    }
}