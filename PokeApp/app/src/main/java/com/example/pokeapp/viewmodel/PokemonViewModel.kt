package com.example.pokeapp.viewmodel

import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeapp.model.Pokemon
import com.example.pokeapp.model.PokemonTypeResponse
import com.example.pokeapp.network.RetrofitClient
import com.example.pokeapp.repository.PokemonRepository
import retrofit2.Response
import javax.security.auth.callback.Callback

class PokemonViewModel : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getPokemonByType(type: String) {
        val apiService = RetrofitClient.apiService
        apiService.getPokemonByType(type).enqueue(object : retrofit2.Callback<Pokemon> {
            override fun onResponse(
                call: retrofit2.Call<Pokemon>,
                response: Response<Pokemon>
            ) {
                if (response.isSuccessful) {
                    val pokemons = response.body()?.pokemon?.map { it.pokemon } ?: emptyList()
                    _pokemonList.value = pokemons
                } else {
                    _errorMessage.value = "Tipo no válido o error en la API"
                }
            }

            override fun onFailure(call: retrofit2.Call<Pokemon>, t: Throwable) {
                _errorMessage.value = "Error: ${t.message}"
            }
        })
    }
}
