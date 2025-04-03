package com.example.pokeapi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokeapi.databinding.ActivityMainBinding
import com.example.pokeapi.recicler.MyAdapter
import com.example.pokeapi.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myViewModel: MainViewModel by viewModels()
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.rvPerros.layoutManager = LinearLayoutManager(this)
        myAdapter = MyAdapter()
        binding.rvPerros.adapter = myAdapter

        binding.bt2.setOnClickListener {
            val pokemon = binding.edt2.text.toString()
            if (pokemon.isEmpty()) {
                Toast.makeText(this, "Ingresa un nombre de Pokémon", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            myViewModel.recuperarPokemonsPorTipo(pokemon)  // Solo pasamos el tipo de Pokémon
        }

        myViewModel.pokemonImages.observe(this) { urls ->
            if (urls.isNotEmpty()) {
                myAdapter.clearList()  // Limpiar la lista antes de agregar nuevos Pokémon
                urls.forEach { url ->
                    myAdapter.addPokemon(url)  // Agregar cada URL al adaptador
                }
            } else {
                Toast.makeText(this, "No se encontraron Pokémon para este tipo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}