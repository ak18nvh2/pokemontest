package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class GenerationI {
    @SerializedName("red-blue")
    @Expose
    var redBlue: RedBlue? = null

    @SerializedName("yellow")
    @Expose
    var yellow: Yellow? = null
}
