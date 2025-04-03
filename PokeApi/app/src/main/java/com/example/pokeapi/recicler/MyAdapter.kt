package com.example.pokeapi.recicler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokeapi.R

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    // Lista para almacenar las URLs de las imágenes de Pokémon
    private val pokemonList = mutableListOf<String>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imV1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val url = pokemonList[position]
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.imageView)  // Cargar la imagen usando Glide
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun addPokemon(url: String) {
        pokemonList.add(url)
        notifyItemInserted(pokemonList.size - 1)
    }


    fun clearList() {
        pokemonList.clear()
        notifyDataSetChanged()
    }
}