package com.example.pokemonapp.models

import com.example.pokemonapp.commons.Utility
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class InformationPokemon  (
    @SerializedName("abilities")
    @Expose
    var abilities: List<Abilities>? = null,

    @SerializedName("base_experience")
    @Expose
    var baseExperience: Int? = null,

    @SerializedName("forms")
    @Expose
    var forms: List<Form>? = null,

    @SerializedName("game_indices")
    @Expose
    var gameIndices: List<GameIndice>? = null,

    @SerializedName("height")
    @Expose
    var height: Int? = null,

    @SerializedName("held_items")
    @Expose
    var heldItems: List<HeldItem>? = null,

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("is_default")
    @Expose
    var isDefault: Boolean? = null,

    @SerializedName("location_area_encounters")
    @Expose
    var locationAreaEncounters: String? = null,

    @SerializedName("moves")
    @Expose
    var moves: List<Move>? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("order")
    @Expose
    var order: Int? = null,

    @SerializedName("species")
    @Expose
    var species: Species? = null,

    @SerializedName("sprites")
    @Expose
    var sprites: Sprites? = null,

    @SerializedName("stats")
    @Expose
    var stats: List<Stat>? = null,

    @SerializedName("types")
    @Expose
    var types: List<Type>? = null,

    @SerializedName("weight")
    @Expose
    var weight: Int? = null,

    var itemViewType: Int = Utility.KEY_SHOW_DATA
) : Serializable