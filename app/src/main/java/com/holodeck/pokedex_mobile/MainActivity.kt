package com.holodeck.pokedex_mobile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


data class ListPokemonResult(
    val name: String,
    val url: String
)

// POJO -> Plain Old Java Object

data class ListPokemonApiResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ListPokemonResult>
)

interface PokeApiService {

    // https://pokeapi.co/api/v2/pokemon?limit=20&offset=0
    // Base: https://pokeapi.co/api/v2/
    // Endpoint (Rota): pokemon?limit=20&offset=0
    @GET("pokemon?limit=50&offset=0")
    fun listPokemon(): Call<ListPokemonApiResult>
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val ivImage = findViewById<ImageView>(R.id.ivImage)
//        Glide.with(ivImage).load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/001.png").into(ivImage)

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

//                val tvName = findViewById<TextView>(R.id.tvName)

                response.body()?.let {
                    // TODO: Transformar lista da resultados da Api em lista de itens da Adapter
                    // TODO: Carregar a RecyclerView com itens do Adapter

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

//                    tvName.text = ""
//
//                    it.results.forEach { pokemon ->
//                        tvName.append(pokemon.name + "\n")
//                    }
                }
            }

            override fun onFailure(call: Call<ListPokemonApiResult>, t: Throwable) {
                // Caso a requisiçao HTTP tenha falhado
                Log.e("POKEMON_API", "Erro ao carregar API.", t)
            }


        })



    }
}