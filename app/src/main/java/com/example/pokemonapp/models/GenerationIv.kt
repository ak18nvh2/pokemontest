package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class GenerationIv : Serializable {
    @SerializedName("diamond-pearl")
    @Expose
    var diamondPearl: DiamondPearl? = null

    @SerializedName("heartgold-soulsilver")
    @Expose
    var heartgoldSoulsilver: HeartgoldSoulsilver? = null

    @SerializedName("platinum")
    @Expose
    var platinum: Platinum? = null
}
