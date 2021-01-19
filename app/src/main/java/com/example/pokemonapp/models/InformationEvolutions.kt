package com.example.pokemonapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class InformationEvolutions: Serializable {
    @SerializedName("baby_trigger_item")
    @Expose
    var babyTriggerItem: Any? = null

    @SerializedName("chain")
    @Expose
    var chain: Chain? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

}