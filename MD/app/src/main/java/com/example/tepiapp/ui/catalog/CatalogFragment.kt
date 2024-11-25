package com.example.tepiapp.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tepiapp.Adapter
import com.example.tepiapp.databinding.FragmentCatalogBinding

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: Adapter
    private lateinit var catalogViewModel: CatalogViewModel
//    private var productList: List<ListItem> = listOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val catalogViewModel =
            ViewModelProvider(this).get(CatalogViewModel::class.java)

        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        catalogViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}