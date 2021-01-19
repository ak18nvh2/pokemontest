package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class GenerationIii : Serializable {
    @SerializedName("emerald")
    @Expose
    var emerald: Emerald? = null

    @SerializedName("firered-leafgreen")
    @Expose
    var fireredLeafgreen: FireredLeafgreen? = null

    @SerializedName("ruby-sapphire")
    @Expose
    var rubySapphire: RubySapphire? = null
}
