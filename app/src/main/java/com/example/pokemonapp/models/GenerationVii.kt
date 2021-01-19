package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class GenerationVii : Serializable {
    @SerializedName("icons")
    @Expose
    var icons: Icons? = null

    @SerializedName("ultra-sun-ultra-moon")
    @Expose
    var ultraSunUltraMoon: UltraSunUltraMoon? = null
}
