package com.example.tepiapp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputLayout

class MyEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        // Add a text changed listener to validate input on the fly
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
        // Validate based on input type (email or password)
        when {
            inputType == android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                    (parent as? TextInputLayout)?.apply {
                        error = "Format email tidak valid"
                    }
                } else {
                    (parent as? TextInputLayout)?.apply {
                        error = null
                    }
                }
            }
            inputType == android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD -> {
                if (input.length < 8) {
                    (parent as? TextInputLayout)?.apply {
                        error = "Password tidak boleh kurang dari 8 karakter"
                    }
                } else {
                    (parent as? TextInputLayout)?.apply {
                        error = null
                    }
                }
            }
            else -> {
                // No validation
                (parent as? TextInputLayout)?.apply {
                    error = null
                }
            }
        }
    }
}
