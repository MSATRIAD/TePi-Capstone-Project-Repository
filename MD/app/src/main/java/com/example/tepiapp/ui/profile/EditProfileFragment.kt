package com.example.tepiapp.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tepiapp.R
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.pref.dataStore
import com.example.tepiapp.databinding.FragmentEditProfileBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private var selectedImageUri: Uri? = null

    private lateinit var profileViewModel: ProfileViewModel

    private val MAX_IMAGE_SIZE = 5 * 1024 * 1024

    private fun isImageSizeValid(uri: Uri): Boolean {
        val file = File(uri.path ?: "")
        return file.length() <= MAX_IMAGE_SIZE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            // Ambil token dari session
            val userPreference = UserPreference.getInstance(requireContext().dataStore)
            val token = userPreference.getSession().first().token
            val apiService = ApiConfig.getApiService(token)
            val userRepository = UserRepository.getInstance(userPreference, apiService)

            val factory = ProfileViewModelFactory(requireActivity().application, userRepository)
            profileViewModel = ViewModelProvider(this@EditProfileFragment, factory).get(ProfileViewModel::class.java)

            profileViewModel.fetchProfile()
        }

        profileViewModel.updateProfileStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Failed to update profile. Try again later.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Mengambil data profil pengguna (contoh menggunakan ViewModel)
        profileViewModel.profile.observe(viewLifecycleOwner) { profile ->
            binding.displayNameEditText.setText(profile.displayName)
            Glide.with(this)
                .load("${profile.profileImage}?timestamp=${System.currentTimeMillis()}")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_profile_black_24dp)
                .into(binding.profileImageView)
        }

        // Menangani tombol pilih gambar
        binding.selectImageButton.setOnClickListener {
            // Membuka galeri untuk memilih gambar
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }

        // Menangani tombol simpan perubahan
        binding.saveChangesButton.setOnClickListener {
            val newDisplayName = binding.displayNameEditText.text.toString()

            if (selectedImageUri != null && isImageSizeValid(selectedImageUri!!)) {
                profileViewModel.updateProfile(requireContext(), newDisplayName, selectedImageUri)
//                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Image size should be less than 5MB", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PICK_IMAGE) {
            selectedImageUri = data?.data
            if (selectedImageUri != null && !isImageSizeValid(selectedImageUri!!)) {
                Toast.makeText(requireContext(), "Image size should be less than 5MB", Toast.LENGTH_SHORT).show()
            } else {
                binding.profileImageView.setImageURI(selectedImageUri)
            }
//            binding.profileImageView.setImageURI(selectedImageUri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001
    }
}