package com.holodeck.pokedex_mobile.api

import retrofit2.Call
import retrofit2.http.GET

interface PokeApiService {

    // https://pokeapi.co/api/v2/pokemon?limit=20&offset=0
    // Base: https://pokeapi.co/api/v2/
    // Endpoint (Rota): pokemon?limit=20&offset=0
    @GET("pokemon?limit=10000&offset=0")
    fun listPokemon(): Call<ListPokemonApiResult>
}