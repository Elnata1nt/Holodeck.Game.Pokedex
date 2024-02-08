package com.holodeck.pokedex_mobile.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.holodeck.pokedex_mobile.R
import com.holodeck.pokedex_mobile.api.ListPokemonApiResult
import com.holodeck.pokedex_mobile.api.PokeApiService
import com.holodeck.pokedex_mobile.list.PokemonItem
import com.holodeck.pokedex_mobile.list.PokemonListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service =  retrofit.create(PokeApiService::class.java)

        val call = service.listPokemon()

        call.enqueue(object : Callback<ListPokemonApiResult> {
            override fun onResponse(
                call: Call<ListPokemonApiResult>,
                response: Response<ListPokemonApiResult>
            ) {
                // Caso a requisiçao HTTP tenha sido bem sucedida
                Log.d("POKEMON_API", response.body().toString())

                response.body()?.let {

                    val pokemonItems = it.results.mapIndexed { index, result ->
                        val number = (index + 1).toString().padStart(3,'0')

                        PokemonItem(
                            result.name,
                            "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/$number.png"
                            )
                    }.toTypedArray()

                    val rvPokemon = findViewById<RecyclerView>(R.id.rvPokemon)
                    rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvPokemon.adapter = PokemonListAdapter(pokemonItems)
                }
            }

            override fun onFailure(call: Call<ListPokemonApiResult>, t: Throwable) {
                // Caso a requisiçao HTTP tenha falhado
                Log.e("POKEMON_API", "Erro ao carregar API.", t)
            }
        })
    }
}