package com.example.pokemonapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Versions : Serializable {
    @SerializedName("generation-i")
    @Expose
    var generationI: GenerationI? = null

    @SerializedName("generation-ii")
    @Expose
    var generationIi: GenerationIi? = null

    @SerializedName("generation-iii")
    @Expose
    var generationIii: GenerationIii? = null

    @SerializedName("generation-iv")
    @Expose
    var generationIv: GenerationIv? = null

    @SerializedName("generation-v")
    @Expose
    var generationV: GenerationV? = null

    @SerializedName("generation-vi")
    @Expose
    var generationVi: GenerationVi? = null

    @SerializedName("generation-vii")
    @Expose
    var generationVii: GenerationVii? = null

    @SerializedName("generation-viii")
    @Expose
    var generationViii: GenerationViii? = null
}
