package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class RedBlue : Serializable {
    @SerializedName("back_default")
    @Expose
    var backDefault: String? = null

    @SerializedName("back_gray")
    @Expose
    var backGray: String? = null

    @SerializedName("front_default")
    @Expose
    var frontDefault: String? = null

    @SerializedName("front_gray")
    @Expose
    var frontGray: String? = null
}
