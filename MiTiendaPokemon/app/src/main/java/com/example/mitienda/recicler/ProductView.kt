package com.example.mitienda.recicler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mitienda.R
import com.example.mitienda.model.Product

class ProductView(itemView: View, private val onProductClick: (Product) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.textViewProductName)
    private val priceTextView: TextView = itemView.findViewById(R.id.textViewPrice)
    private val imagenView: ImageView = itemView.findViewById(R.id.imageViewProduct)

    private lateinit var currentProduct: Product

    init {
        itemView.setOnClickListener {
            onProductClick(currentProduct)
        }
    }

    fun bind(product: Product, mostrarEncabezadoCategoria: Boolean) {
        currentProduct = product
        nameTextView.text = product.name
        priceTextView.text = "Precio: ${product.price}€"
        Glide.with(itemView.context)
            .load(product.image)
            .into(imagenView)
    }
}
