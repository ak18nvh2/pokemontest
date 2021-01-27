package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ListPokemon() {
    constructor(listPokemon: ListPokemon): this() {
        this.results = listPokemon.results
        this.count = listPokemon.count
        this.next = listPokemon.next
        this.previous = listPokemon.previous
    }
    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("next")
    @Expose
    var next: String? = null

    @SerializedName("previous")
    @Expose
    var previous: String? = null

    @SerializedName("results")
    @Expose
    var results: List<ResultPokemon>? = null
}