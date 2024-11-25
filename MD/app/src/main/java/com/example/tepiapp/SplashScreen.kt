package com.example.tepiapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.tepiapp.ui.login.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Mengatur tampilan layar penuh untuk SplashScreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Menerapkan tema yang tersimpan (Light/Dark Mode)
        applySavedTheme()

        // Menunda navigasi ke LoginActivity selama 3 detik
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    // Fungsi untuk menerapkan tema yang tersimpan
    private fun applySavedTheme() {
        val sharedPrefs = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false) // Default adalah Light Mode
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
