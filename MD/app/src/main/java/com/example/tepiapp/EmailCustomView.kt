package com.example.tepiapp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class EmailCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                error = if (s != null && !Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    "Format email tidak valid"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
