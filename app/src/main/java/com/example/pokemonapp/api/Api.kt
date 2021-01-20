package com.example.pokemonapp.api

import com.example.pokemonapp.models.*
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

    @GET("pokemon-form/{id}")
    fun getInformationAPokemonForm(
        @Path("id") pokemonID: String
    ): Call<InformationPokemonForm>

    @GET("pokemon-species/{id}")
    fun getInformationAPokemonSpecies(
        @Path("id") pokemonID: String
    ): Call<InformationPokemonSpecies>

    @GET("evolution-chain/{id}")
    fun getInformationEvolution(
        @Path("id") pokemonID: String
    ): Call<InformationEvolutions>
}
