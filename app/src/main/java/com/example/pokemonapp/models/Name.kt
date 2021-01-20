package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Name: Serializable {
    @SerializedName("language")
    @Expose
    var language: Language? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}
