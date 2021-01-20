package com.example.pokemonapp.views.fragments

import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.pokemonapp.R
import com.example.pokemonapp.models.InformationPokemon
import com.example.pokemonapp.models.InformationPokemonForm
import com.example.pokemonapp.models.InformationPokemonSpecies

class StatsFragment() : Fragment() {
    private var mInformationPokemonForm = InformationPokemonForm()
    private var mInformationPokemonSpecies = InformationPokemonSpecies()
    private var mInformationPokemon = InformationPokemon()
    private var mPrimaryColor = -1

    constructor(
        informationPokemon: InformationPokemon,
        informationPokemonSpecies: InformationPokemonSpecies,
        informationPokemonForm: InformationPokemonForm,
        primaryColor: Int
    ) : this() {
        this.mInformationPokemon = informationPokemon
        this.mInformationPokemonForm = informationPokemonForm
        this.mInformationPokemonSpecies = informationPokemonSpecies
        this.mPrimaryColor = primaryColor
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val t = inflater.inflate(R.layout.fragment_stats, container, false)
        val percentMale = (0..100).random()
        val percentFemale = 100 - percentMale
        val pbGender = t.findViewById<ProgressBar>(R.id.pb_Gender)
        val tvPercentMale = t.findViewById<TextView>(R.id.tv_percentMale)
        val tvPercentFemale = t.findViewById<TextView>(R.id.tv_percentFemale)
        tvPercentFemale.text = "$percentFemale%"
        tvPercentMale.text = "$percentMale%"
        pbGender.progress = percentMale

        val percentRate = (0..100).random()
        val pbRate = t.findViewById<ProgressBar>(R.id.pb_CaptureRate)
        val tvRate = t.findViewById<TextView>(R.id.tv_percentCaptureRate)
        tvRate.text = "$percentRate%"
        pbRate.progress = percentRate
        return t
    }


}