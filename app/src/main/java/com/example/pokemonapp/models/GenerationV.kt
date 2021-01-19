package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class GenerationV : Serializable {
    @SerializedName("black-white")
    @Expose
    var blackWhite: BlackWhite? = null
}
