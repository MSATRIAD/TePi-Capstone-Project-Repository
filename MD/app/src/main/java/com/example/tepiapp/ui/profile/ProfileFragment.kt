package com.example.tepiapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tepiapp.databinding.FragmentProfileBinding
import com.example.tepiapp.ui.about.AboutActivity
import com.example.tepiapp.ui.login.LoginActivity

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

        // Initialize ViewModel
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Observe profile information (username and email)
        profileViewModel.username.observe(viewLifecycleOwner) { username ->
            binding.username.text = username
        }
        profileViewModel.email.observe(viewLifecycleOwner) { email ->
            binding.email.text = email
        }

        // Observe Dark Mode preference
        val switchDarkMode: Switch = binding.switchDarkMode
        profileViewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
            switchDarkMode.isChecked = isDarkMode
        }

        // Handle Dark Mode toggle
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            profileViewModel.setDarkMode(isChecked)
        }

        // Navigate to About Us
        binding.aboutUsButton.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }

        binding.signOutButton.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
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
