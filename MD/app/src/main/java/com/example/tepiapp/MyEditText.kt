package com.example.tepiapp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MyEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    var validationType: ValidationType = ValidationType.NONE

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateInput(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

    private fun validateInput(input: String) {
        when (validationType) {
            ValidationType.PASSWORD -> {
                if (input.length < 8) {
                    error = "Password tidak boleh kurang dari 8 karakter"
                } else {
                    error = null
                }
            }
            ValidationType.EMAIL -> {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                    error = "Format email tidak valid"
                } else {
                    error = null
                }
            }
            ValidationType.RE_ENTER_PASSWORD -> {
                // Validasi dilakukan pada Activity atau Fragment, karena butuh referensi password sebelumnya
            }
            ValidationType.NONE -> {
                // Tidak ada validasi
            }
        }
    }

    enum class ValidationType {
        PASSWORD, EMAIL, RE_ENTER_PASSWORD, NONE
    }
}