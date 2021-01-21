package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class InformationAbilities: Serializable {
    @SerializedName("effect_changes")
    @Expose
    var effectChanges: List<Any>? = null

    @SerializedName("effect_entries")
    @Expose
    var effectEntries: List<EffectEntry>? = null

    @SerializedName("flavor_text_entries")
    @Expose
    var flavorTextEntries: List<FlavorTextEntryAbilities>? = null

    @SerializedName("generation")
    @Expose
    var generation: Generation? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("is_main_series")
    @Expose
    var isMainSeries: Boolean? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("names")
    @Expose
    var names: List<Name>? = null

    @SerializedName("pokemon")
    @Expose
    var pokemon: List<PokemonAbilities>? = null
}