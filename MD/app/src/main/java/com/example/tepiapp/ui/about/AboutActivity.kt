package com.example.tepiapp.ui.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.example.tepiapp.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Inisialisasi Toolbar
        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        // Atur aksi tombol navigasi
        topAppBar.setNavigationOnClickListener {
            // Kembali ke activity sebelumnya
            onBackPressed()
        }
    }
}
