package com.example.pokeapp.model

data class Pokemon(val name: String, val url: String)

data class PokemonTypeSlot(val pokemon: Pokemon)

data class PokemonTypeResponse(val pokemon: List<PokemonTypeSlot>)