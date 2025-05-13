package com.example.mitienda.recycler

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mitienda.R
import com.example.mitienda.models.Category
import com.example.mitienda.models.Product

class ProductView(itemView: View, private val onProductClick: (Product) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.textViewProductName)
    private val priceTextView: TextView = itemView.findViewById(R.id.textViewProductPrice)
    private val imageView: ImageView = itemView.findViewById(R.id.imageViewProduct)

    private lateinit var currentProduct: Product

    init {
        itemView.setOnClickListener {
            onProductClick(currentProduct)
        }
    }

    fun bind(product: Product, categoryName: String) {
        currentProduct = product
        nameTextView.text = product.name
        priceTextView.text = "Price: ${product.price}€"

        val fullImageUrl = "http://10.0.2.2:8000${product.image}"
        Log.d("GLIDE", "Cargando imagen desde: $fullImageUrl")

        Glide.with(itemView.context)
            .load(fullImageUrl)
//            .placeholder(R.drawable.placeholder_image)
//            .error(R.drawable.error_image)
            .into(imageView)
    }

}
