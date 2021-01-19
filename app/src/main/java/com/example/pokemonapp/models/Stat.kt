package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Stat : Serializable {
    @SerializedName("base_stat")
    @Expose
    var baseStat: Int? = null

    @SerializedName("effort")
    @Expose
    var effort: Int? = null

    @SerializedName("stat")
    @Expose
    var stat: Stat_? = null
}
