package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class EvolutionChain: Serializable {
    @SerializedName("url")
    @Expose
    var url: String? = null
}
