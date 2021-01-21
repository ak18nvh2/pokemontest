package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class EffectEntry: Serializable {
    @SerializedName("effect")
    @Expose
    var effect: String? = null

    @SerializedName("language")
    @Expose
    var language: Language? = null

    @SerializedName("short_effect")
    @Expose
    var shortEffect: String? = null
}
