package com.example.tepiapp.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tepiapp.R
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.pref.dataStore
import com.example.tepiapp.databinding.ActivityEditProfileBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private var selectedImageUri: Uri? = null
    private lateinit var profileViewModel: ProfileViewModel
    private val MAX_IMAGE_SIZE = 5 * 1024 * 1024  // 5MB

    // Validate image size
    private fun isImageSizeValid(uri: Uri): Boolean {
        val file = File(uri.path ?: "")
        return file.length() <= MAX_IMAGE_SIZE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            finish()  // Navigate back
        }

        lifecycleScope.launch {
            // Ambil token dari session
            val userPreference = UserPreference.getInstance(applicationContext.dataStore)
            val token = userPreference.getSession().first().token
            val apiService = ApiConfig.getApiService(token)
            val userRepository = UserRepository.getInstance(userPreference, apiService)

            val factory = ProfileViewModelFactory(application, userRepository)
            profileViewModel = ViewModelProvider(this@EditProfileActivity, factory).get(ProfileViewModel::class.java)

            profileViewModel.fetchProfile()  // Fetch user profile when activity starts
        }

        // Observe updateProfileStatus from ViewModel
        profileViewModel.updateProfileStatus.observe(this) { success ->
            if (success) {
                finish()  // Go back if the update is successful
            } else {
                Toast.makeText(this, "Failed to update profile. Try again later.", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe profile data
        profileViewModel.profile.observe(this) { profile ->
            binding.displayNameEditText.setText(profile.displayName)  // Set the existing display name
            Glide.with(this)
                .load("${profile.profileImage}?timestamp=${System.currentTimeMillis()}")  // Prevent caching
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_profile_black_24dp)
                .into(binding.profileImageView)
        }

        // Handle image selection
        binding.selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }

        // Handle save button click to update profile
        binding.saveChangesButton.setOnClickListener {
            val newDisplayName = binding.displayNameEditText.text.toString()

            if (selectedImageUri != null && isImageSizeValid(selectedImageUri!!)) {
                profileViewModel.updateProfile(this, newDisplayName, selectedImageUri!!)
            } else {
                Toast.makeText(this, "Image size should be less than 5MB", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle the result when an image is selected
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PICK_IMAGE) {
            selectedImageUri = data?.data
            if (selectedImageUri != null && !isImageSizeValid(selectedImageUri!!)) {
                Toast.makeText(this, "Image size should be less than 5MB", Toast.LENGTH_SHORT).show()
            } else {
                binding.profileImageView.setImageURI(selectedImageUri)  // Display the selected image
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001
    }
}
