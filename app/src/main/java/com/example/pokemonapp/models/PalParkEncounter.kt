package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PalParkEncounter: Serializable {

    @SerializedName("area")
    @Expose
    var area: Area? = null

    @SerializedName("base_score")
    @Expose
    var baseScore: Int? = null

    @SerializedName("rate")
    @Expose
    var rate: Int? = null
}
