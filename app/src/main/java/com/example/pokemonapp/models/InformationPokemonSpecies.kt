package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class InformationPokemonSpecies: Serializable {
    @SerializedName("base_happiness")
    @Expose
    var baseHappiness: Int? = null

    @SerializedName("capture_rate")
    @Expose
    var captureRate: Int? = null

    @SerializedName("color")
    @Expose
    var color: Color? = null

    @SerializedName("egg_groups")
    @Expose
    var eggGroups: List<EggGroup>? = null

    @SerializedName("evolution_chain")
    @Expose
    var evolutionChain: EvolutionChain? = null

    @SerializedName("evolves_from_species")
    @Expose
    var evolvesFromSpecies: EvolvesFromSpecies? = null

    @SerializedName("flavor_text_entries")
    @Expose
    var flavorTextEntries: List<FlavorTextEntry>? = null

    @SerializedName("form_descriptions")
    @Expose
    var formDescriptions: List<Any>? = null

    @SerializedName("forms_switchable")
    @Expose
    var formsSwitchable: Boolean? = null

    @SerializedName("gender_rate")
    @Expose
    var genderRate: Int? = null

    @SerializedName("genera")
    @Expose
    var genera: List<Genera>? = null

    @SerializedName("generation")
    @Expose
    var generation: Generation? = null

    @SerializedName("growth_rate")
    @Expose
    var growthRate: GrowthRate? = null

    @SerializedName("habitat")
    @Expose
    var habitat: Habitat? = null

    @SerializedName("has_gender_differences")
    @Expose
    var hasGenderDifferences: Boolean? = null

    @SerializedName("hatch_counter")
    @Expose
    var hatchCounter: Int? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("is_baby")
    @Expose
    var isBaby: Boolean? = null

    @SerializedName("is_legendary")
    @Expose
    var isLegendary: Boolean? = null

    @SerializedName("is_mythical")
    @Expose
    var isMythical: Boolean? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("names")
    @Expose
    var names: List<Name>? = null

    @SerializedName("order")
    @Expose
    var order: Int? = null

    @SerializedName("pal_park_encounters")
    @Expose
    var palParkEncounters: List<PalParkEncounter>? = null

    @SerializedName("pokedex_numbers")
    @Expose
    var pokedexNumbers: List<PokedexNumber>? = null

    @SerializedName("shape")
    @Expose
    var shape: Shape? = null

    @SerializedName("varieties")
    @Expose
    var varieties: List<Variety>? = null
}