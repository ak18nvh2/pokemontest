package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class FlavorTextEntryAbilities: Serializable {
    @SerializedName("flavor_text")
    @Expose
    var flavorText: String? = null

    @SerializedName("language")
    @Expose
    var language: Language? = null

    @SerializedName("version_group")
    @Expose
    var versionGroup: VersionGroup? = null
}
