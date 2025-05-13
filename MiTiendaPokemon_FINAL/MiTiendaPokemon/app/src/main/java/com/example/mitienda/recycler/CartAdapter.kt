package com.example.mitienda.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mitienda.databinding.ItemCarroBinding
import com.example.mitienda.models.ShoppingCartDto

class CartAdapter(private var cartItems: List<ShoppingCartDto>, private val onItemClick: (ShoppingCartDto) -> Unit) : RecyclerView.Adapter<CartView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartView {
        val binding = ItemCarroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartView(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CartView, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateData(newItems: List<ShoppingCartDto>) {
        cartItems = newItems
        notifyDataSetChanged()
    }
}