package com.example.pokeapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import com.example.pokeapp.model.Pokemon


class PokemonAdapter(private var pokemons: List<Pokemon>) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textPokemonName: TextView = itemView.findViewById(R.id.textPokemonName)
        val imagePokemon: ImageView = itemView.findViewById(R.id.imagePokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.textPokemonName.text = pokemon.name.capitalize()

        // Carga la imagen con Glide usando el ID del Pokémon extraído del URL
        val pokemonId = pokemon.url.split("/").filter { it.isNotEmpty() }.last()
        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.imagePokemon)
    }

    override fun getItemCount(): Int = pokemons.size

    fun updateData(newList: List<Pokemon>) {
        pokemons = newList
        notifyDataSetChanged()
    }
}