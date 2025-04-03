package com.example.pokeapi.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapi.MainActivity
import com.example.pokeapi.model.MainState

import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val myEstado = MainState()

    private val _pokemonImages = MutableLiveData<List<String>>()
    val pokemonImages: LiveData<List<String>> get() = _pokemonImages

    fun recuperarPokemonsPorTipo(tipo: String) {
        viewModelScope.launch {
            try {
                val respuesta = myEstado.recuperarPokemonsPorTipo(tipo)
                if (respuesta.isSuccessful) {
                    val pokemons = respuesta.body()?.pokemon?.map { it.pokemon.name } ?: emptyList()
                    if (pokemons.isNotEmpty()) {
                        // Obtener las imágenes de cada Pokémon
                        pokemons.forEach { pokemon ->
                            myEstado.recuperarPokemon(pokemon).sprites.front_default?.let {
                                myAdapter.addPokemon(it)
                            }
                        }
                    } else {
                        _pokemonImages.value = emptyList()
                    }
                } else {
                    _pokemonImages.value = emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _pokemonImages.value = emptyList()
            }
        }
    }
}

