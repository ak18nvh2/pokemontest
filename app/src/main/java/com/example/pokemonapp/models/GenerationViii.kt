package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class GenerationViii : Serializable {
    @SerializedName("icons")
    @Expose
    var icons: Icons? = null
}
