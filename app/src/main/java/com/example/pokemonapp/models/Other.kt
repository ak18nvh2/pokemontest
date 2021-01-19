package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Other : Serializable {
    @SerializedName("dream_world")
    @Expose
    var dreamWorld: DreamWorld? = null

    @SerializedName("official-artwork")
    @Expose
    var officialArtwork: OfficialArtwork? = null
}
