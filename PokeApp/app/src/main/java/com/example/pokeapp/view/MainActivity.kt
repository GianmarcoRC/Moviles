package com.example.pokeapp.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.view.adapter.PokemonAdapter
import com.example.pokeapp.viewmodel.PokemonViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var editTextType: EditText
    private lateinit var buttonSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var viewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextType = findViewById(R.id.editTextType)
        buttonSearch = findViewById(R.id.buttonSearch)
        recyclerView = findViewById(R.id.recyclerView)

        pokemonAdapter = PokemonAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = pokemonAdapter

        viewModel = ViewModelProvider(this)[PokemonViewModel::class.java]

        viewModel.pokemonList.observe(this) { pokemons ->
            pokemonAdapter.updateData(pokemons)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
        }

        buttonSearch.setOnClickListener {
            val type = editTextType.text.toString().lowercase().trim()
            if (type.isNotEmpty()) {
                viewModel.getPokemonByType(type)
            } else {
                Toast.makeText(this, "Introduce un tipo válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
