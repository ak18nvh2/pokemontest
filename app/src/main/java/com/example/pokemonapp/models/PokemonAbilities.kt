package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PokemonAbilities: Serializable {

    @SerializedName("is_hidden")
    @Expose
    var isHidden: Boolean? = null

    @SerializedName("pokemon")
    @Expose
    var pokemon: Pokemon? = null

    @SerializedName("slot")
    @Expose
    var slot: Int? = null
}
