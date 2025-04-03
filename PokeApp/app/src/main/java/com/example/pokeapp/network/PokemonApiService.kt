package com.example.pokeapp.network
import com.example.pokeapp.model.PokemonTypeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
interface PokemonApiService {
    @GET("type/{type}")
    fun getPokemonsByType(@Path("type") type: String): Call<PokemonTypeResponse>
}