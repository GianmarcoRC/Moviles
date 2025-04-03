package com.example.pokeapi.model

data class PokemonTypeResponse(
    val pokemon: List<PokemonEntry>
)

data class PokemonEntry(
    val pokemon: PokemonInfo
)

data class PokemonInfo(
    val name: String,
    val url: String
)
