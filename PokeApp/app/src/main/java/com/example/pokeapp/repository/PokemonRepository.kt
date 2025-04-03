package com.example.pokeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokeapp.model.Pokemon
import com.example.pokeapp.model.PokemonTypeResponse
import com.example.pokeapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository {
    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>> get() = _pokemons

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchPokemonsByType(type: String) {
        ApiClient.apiService.getPokemonsByType(type)
            .enqueue(object : Callback<PokemonTypeResponse> {
                override fun onResponse(
                    call: Call<PokemonTypeResponse>,
                    response: Response<PokemonTypeResponse>
                ) {
                    if (response.isSuccessful) {
                        _pokemons.value = response.body()?.pokemon?.map { it.pokemon } ?: emptyList()
                    } else {
                        _error.value = "Tipo inválido o no encontrado"
                    }
                }

                override fun onFailure(call: Call<PokemonTypeResponse>, t: Throwable) {
                    _error.value = "Error: ${t.message}"
                }
            })
    }
}