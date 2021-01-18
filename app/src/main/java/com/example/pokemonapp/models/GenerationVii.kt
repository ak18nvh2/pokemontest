package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class GenerationVii {
    @SerializedName("icons")
    @Expose
    var icons: Icons? = null

    @SerializedName("ultra-sun-ultra-moon")
    @Expose
    var ultraSunUltraMoon: UltraSunUltraMoon? = null
}
