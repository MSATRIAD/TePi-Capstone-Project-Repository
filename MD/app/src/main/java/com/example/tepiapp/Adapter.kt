package com.example.tepiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tepiapp.data.response.ListProductItem

class Adapter(private var productList: List<ListProductItem>, private val onItemClick: (ListProductItem) -> Unit) : RecyclerView.Adapter<Adapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.tvProductName)
//        val nutrigradeLabel: TextView = view.findViewById(R.id.tvNutrigradeLabel)
        val productGrade: TextView = view.findViewById(R.id.tvProductGrade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        // Set the product name and nutrigrade label
        holder.productName.text = product.name.uppercase()
//        holder.nutrigradeLabel.text = holder.itemView.context.getString(R.string.nutrigrade_label)

        // Safely handle null grades
        holder.productGrade.text = "Nutriscore: ${product.grade}"

        // Set the click listener
        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // Method to update the list
    fun updateData(newProductList: List<ListProductItem>) {
        productList = newProductList
        notifyDataSetChanged()
    }
}
