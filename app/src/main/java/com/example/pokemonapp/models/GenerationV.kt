package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class GenerationV {
    @SerializedName("black-white")
    @Expose
    var blackWhite: BlackWhite? = null
}
