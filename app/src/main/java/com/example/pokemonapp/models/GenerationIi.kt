package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class GenerationIi : Serializable {
    @SerializedName("crystal")
    @Expose
    var crystal: Crystal? = null

    @SerializedName("gold")
    @Expose
    var gold: Gold? = null

    @SerializedName("silver")
    @Expose
    var silver: Silver? = null

}
