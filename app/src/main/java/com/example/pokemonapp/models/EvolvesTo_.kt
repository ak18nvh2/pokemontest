package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class EvolvesTo_ : Serializable {
    @SerializedName("evolution_details")
    @Expose
    var evolutionDetails: List<EvolutionDetail>? = null

    @SerializedName("evolves_to")
    @Expose
    var evolvesTo: List<Any>? = null

    @SerializedName("is_baby")
    @Expose
    var isBaby: Boolean? = null

    @SerializedName("species")
    @Expose
    var species: Species? = null
}
