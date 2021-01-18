package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class GenerationVi {
    @SerializedName("omegaruby-alphasapphire")
    @Expose
    var omegarubyAlphasapphire: OmegarubyAlphasapphire? = null

    @SerializedName("x-y")
    @Expose
    var xY: XY? = null
}
