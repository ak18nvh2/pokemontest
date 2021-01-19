package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Genera: Serializable {
    @SerializedName("genus")
    @Expose
    var genus: String? = null

    @SerializedName("language")
    @Expose
    var language: Language? = null
}
