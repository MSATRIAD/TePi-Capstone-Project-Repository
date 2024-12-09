package com.example.tepiapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.databinding.FragmentProfileBinding
import com.example.tepiapp.ui.about.AboutActivity
import com.example.tepiapp.ui.login.LoginActivity
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.api.ApiService
import com.example.tepiapp.data.pref.dataStore

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val token =
            "your_token_here"

        val apiService: ApiService = ApiConfig.getApiService(token)

        val userPreference = UserPreference.getInstance(requireContext().dataStore)

        val userRepository = UserRepository.getInstance(userPreference, apiService)

        val factory = ProfileViewModelFactory(requireActivity().application, userRepository)
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        profileViewModel.username.observe(viewLifecycleOwner) { username ->
            binding.username.text = username
        }
        profileViewModel.email.observe(viewLifecycleOwner) { email ->
            binding.email.text = email
        }

        val switchDarkMode: Switch = binding.switchDarkMode
        profileViewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
            switchDarkMode.isChecked = isDarkMode
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            profileViewModel.setDarkMode(isChecked)
        }

        binding.aboutUsButton.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }

        binding.signOutButton.setOnClickListener {
            profileViewModel.logout()

            val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)

            activity?.finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
