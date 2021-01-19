package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Abilities : Serializable {
    @SerializedName("ability")
    @Expose
    var ability: Ability? = null

    @SerializedName("is_hidden")
    @Expose
    var isHidden: Boolean? = null

    @SerializedName("slot")
    @Expose
    var slot: Int? = null
}