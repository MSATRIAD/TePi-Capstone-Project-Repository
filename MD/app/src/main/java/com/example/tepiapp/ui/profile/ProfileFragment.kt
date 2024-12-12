package com.example.tepiapp.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tepiapp.R
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.databinding.FragmentProfileBinding
import com.example.tepiapp.ui.about.AboutActivity
import com.example.tepiapp.ui.login.LoginActivity
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.api.ApiService
import com.example.tepiapp.data.pref.dataStore
import com.example.tepiapp.di.Injection
import com.example.tepiapp.ui.catalog.CatalogViewModel
import com.example.tepiapp.ui.catalog.CatalogViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            // Ambil token dari session
            val userPreference = UserPreference.getInstance(requireContext().dataStore)
            val token = userPreference.getSession().first().token // first() dipanggil dalam coroutine
            val apiService = ApiConfig.getApiService(token)
            val userRepository = UserRepository.getInstance(userPreference, apiService)

            val factory = ProfileViewModelFactory(requireActivity().application, userRepository)
            profileViewModel = ViewModelProvider(this@ProfileFragment, factory).get(ProfileViewModel::class.java)

            profileViewModel.fetchProfile()
        }

        // Amati data profil
        profileViewModel.profile.observe(viewLifecycleOwner) { profile ->
            binding.username.text = profile.displayName
            binding.email.text = profile.email

            // Muat gambar profil menggunakan Glide
            Glide.with(this)
//                .load(profile.profileImage)
                .load("${profile.profileImage}?timestamp=${System.currentTimeMillis()}")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_profile_black_24dp) // Gambar default
                .into(binding.profileImage)
        }

        // Amati perubahan pada Dark Mode
        val switchDarkMode: Switch = binding.switchDarkMode
        profileViewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
            switchDarkMode.isChecked = isDarkMode
        }

        // Atur pengendali untuk Dark Mode
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            profileViewModel.setDarkMode(isChecked)
        }

        // Atur tindakan untuk tombol About Us
        binding.aboutUsButton.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }

        // Atur tindakan untuk tombol Sign Out
        binding.signOutButton.setOnClickListener {
            profileViewModel.logout()

            val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)

            activity?.finish()
        }

        binding.editProfile.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}