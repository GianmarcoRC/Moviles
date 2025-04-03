package com.example.pokeapi.model

import com.example.pokeapi.network.PokemonApiService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainState {
    private val baseUrl = "https://pokeapi.co/api/v2/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(PokemonApiService::class.java)

    // Nueva función para recuperar Pokémon por tipo
    suspend fun recuperarPokemonsPorTipo(tipo: String): Response<PokemonTypeResponse> {
        return apiService.getPokemonsByType(tipo)
    }


}

