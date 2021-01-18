package com.example.pokemonapp.api

import com.example.pokemonapp.models.InformationPokemon
import com.example.pokemonapp.models.ListPokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("pokemon/{id}")
    fun getInformationAPokemon(
        @Path("id") pokemonID: String
    ): Call<InformationPokemon>

    @GET("pokemon")
    fun getListPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<ListPokemon>

}
