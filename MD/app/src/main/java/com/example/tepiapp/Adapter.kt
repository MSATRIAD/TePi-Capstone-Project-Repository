package com.example.tepiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tepiapp.data.response.ListProductItem

class Adapter(private var productList: List<ListProductItem>) : RecyclerView.Adapter<Adapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.tvProductName)
        val productGrade: TextView = view.findViewById(R.id.tvProductGrade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val event = productList[position]

        holder.productName.text = event.name
        holder.productGrade.text = event.grade

//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
//            intent.putExtra("EXTRA_NAME", event.name)
//            intent.putExtra("EXTRA_DESCRIPTION", event.description)
//            intent.putExtra("EXTRA_OWNER", event.ownerName)
//            intent.putExtra("EXTRA_QUOTA", event.quota)
//            intent.putExtra("EXTRA_REGISTRANT", event.registrants)
//            intent.putExtra("EXTRA_BEGIN_TIME", event.beginTime)
//            intent.putExtra("EXTRA_END_TIME", event.endTime)
//            intent.putExtra("EXTRA_CITY_NAME", event.cityName)
//            intent.putExtra("EXTRA_IMAGE", event.mediaCover)
//            intent.putExtra("EXTRA_LINK", event.link)
//            intent.putExtra("EXTRA_ID", event.id.toString())
//            holder.itemView.context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateData(newProductList: List<ListProductItem>) {
        productList = newProductList
        notifyDataSetChanged()
    }
}