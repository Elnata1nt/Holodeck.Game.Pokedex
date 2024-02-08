package com.holodeck.pokedex_mobile.api

data class ListPokemonApiResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItemApi>
)