package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class GameIndice: Serializable {
    @SerializedName("game_index")
    @Expose
    var gameIndex: Int? = null

    @SerializedName("version")
    @Expose
    var version: Version? = null
}