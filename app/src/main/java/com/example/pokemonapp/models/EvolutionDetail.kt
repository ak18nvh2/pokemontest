package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class EvolutionDetail: Serializable {
    @SerializedName("gender")
    @Expose
    var gender: Any? = null

    @SerializedName("held_item")
    @Expose
    var heldItem: Any? = null

    @SerializedName("item")
    @Expose
    var item: Any? = null

    @SerializedName("known_move")
    @Expose
    var knownMove: Any? = null

    @SerializedName("known_move_type")
    @Expose
    var knownMoveType: Any? = null

    @SerializedName("location")
    @Expose
    var location: Any? = null

    @SerializedName("min_affection")
    @Expose
    var minAffection: Any? = null

    @SerializedName("min_beauty")
    @Expose
    var minBeauty: Any? = null

    @SerializedName("min_happiness")
    @Expose
    var minHappiness: Any? = null

    @SerializedName("min_level")
    @Expose
    var minLevel: Int? = null

    @SerializedName("needs_overworld_rain")
    @Expose
    var needsOverworldRain: Boolean? = null

    @SerializedName("party_species")
    @Expose
    var partySpecies: Any? = null

    @SerializedName("party_type")
    @Expose
    var partyType: Any? = null

    @SerializedName("relative_physical_stats")
    @Expose
    var relativePhysicalStats: Any? = null

    @SerializedName("time_of_day")
    @Expose
    var timeOfDay: String? = null

    @SerializedName("trade_species")
    @Expose
    var tradeSpecies: Any? = null

    @SerializedName("trigger")
    @Expose
    var trigger: Trigger? = null

    @SerializedName("turn_upside_down")
    @Expose
    var turnUpsideDown: Boolean? = null
}
