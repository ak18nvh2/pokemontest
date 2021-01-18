package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class HeldItem {

    @SerializedName("item")
    @Expose
    var item: Item? = null

    @SerializedName("version_details")
    @Expose
    var versionDetails: List<VersionDetail>? = null
}