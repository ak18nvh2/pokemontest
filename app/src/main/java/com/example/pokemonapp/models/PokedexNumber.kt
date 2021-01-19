package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PokedexNumber: Serializable {

    @SerializedName("entry_number")
    @Expose
    var entryNumber: Int? = null

    @SerializedName("pokedex")
    @Expose
    var pokedex: Pokedex? = null
}
