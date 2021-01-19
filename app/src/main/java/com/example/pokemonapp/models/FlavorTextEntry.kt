package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class FlavorTextEntry: Serializable {
    @SerializedName("flavor_text")
    @Expose
    var flavorText: String? = null

    @SerializedName("language")
    @Expose
    var language: Language? = null

    @SerializedName("version")
    @Expose
    var version: Version? = null
}
