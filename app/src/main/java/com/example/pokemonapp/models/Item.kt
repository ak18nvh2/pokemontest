package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Item {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
}