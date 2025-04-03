package com.example.pokeapi.network

import com.example.pokeapi.model.Pokemon
import com.example.pokeapi.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {
    @GET("type/{type}")
    suspend fun getPokemonsByType(@Path("type") type: String): Response<PokemonTypeResponse>
}
