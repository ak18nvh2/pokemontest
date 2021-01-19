package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Type : Serializable {
    @SerializedName("slot")
    @Expose
    var slot: Int? = null

    @SerializedName("type")
    @Expose
    var type: Type_? = null
}
