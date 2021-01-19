package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Move : Serializable {

    @SerializedName("move")
    @Expose
    var move: Move_? = null

    @SerializedName("version_group_details")
    @Expose
    var versionGroupDetails: List<VersionGroupDetail>? = null
}