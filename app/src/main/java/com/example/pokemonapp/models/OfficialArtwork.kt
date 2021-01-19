package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class OfficialArtwork : Serializable {
    @SerializedName("front_default")
    @Expose
    var frontDefault: String? = null
}
