package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Variety : Serializable {
    @SerializedName("is_default")
    @Expose
    var isDefault: Boolean? = null

    @SerializedName("pokemon")
    @Expose
    var pokemon: Pokemon? = null
}
