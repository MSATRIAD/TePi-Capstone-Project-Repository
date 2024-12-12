package com.example.tepiapp.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tepiapp.R
import com.example.tepiapp.data.response.ListProductItem

class SaveProductsAdapter(
    private var productList: List<ListProductItem>,
    private val onItemClick: (ListProductItem) -> Unit
) : RecyclerView.Adapter<SaveProductsAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.tvProductName)
        val productGrade: TextView = view.findViewById(R.id.tvProductGrade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false) // Ensure the layout matches
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        // Set the product name
        holder.productName.text = product.name

        // Safely handle null grades
        val grade = product.grade ?: "No Grade" // Provide a fallback text if grade is null
        holder.productGrade.text = grade

        // Set the click listener
        holder.itemView.setOnClickListener {
            onItemClick(product) // Pass product to the callback function
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // Method to update the list of products
    fun updateData(newProductList: List<ListProductItem>) {
        productList = newProductList
        notifyDataSetChanged() // Notify the adapter to refresh the UI
    }
}
