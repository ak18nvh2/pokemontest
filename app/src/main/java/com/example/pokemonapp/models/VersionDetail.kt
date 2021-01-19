package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class VersionDetail : Serializable {
    @SerializedName("rarity")
    @Expose
    var rarity: Int? = null

    @SerializedName("version")
    @Expose
    var version: Version? = null
}