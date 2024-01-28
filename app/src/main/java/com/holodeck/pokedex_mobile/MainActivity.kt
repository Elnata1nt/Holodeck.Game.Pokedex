package com.holodeck.pokedex_mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.http.GET

data class ListPokemonResult(
    val name: String,
    val url: String
)

data class ListPokemonApiReseult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: Array<ListPokemonResult>
)

interface PokeApiService {
    //https://pokeapi.co/api/v2/pokemon?limit=20&offset=0
    //Base: https://pokeapi.co/api/v2/
    // Endpoint (Rota): pokemon?limit=20&offset=0
    @GET("pokemon?limit=20&offset=0")
    fun listPokemon(): Call<ListPokemonApiReseult>
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}