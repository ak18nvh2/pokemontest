package com.example.pokemonapp.views.fragments

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pokemonapp.R
import com.example.pokemonapp.models.InformationPokemon
import com.example.pokemonapp.models.InformationPokemonForm
import com.example.pokemonapp.models.InformationPokemonSpecies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_stats.*

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
        val tvPercentMale = t.findViewById<TextView>(R.id.tv_percentMale)
        val tvPercentFemale = t.findViewById<TextView>(R.id.tv_percentFemale)
        val tvRate = t.findViewById<TextView>(R.id.tv_percentCaptureRate)
        val arrayListTextViewOfInformationStats = ArrayList<TextView>()
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_percentFemale))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_percentCaptureRate))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_HP))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_ATK))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_DEF))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_SATK))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_SDEF))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_SPD))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Weaknesses))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Abilities))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_torrent))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_rainDish))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_breeding))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_EggGroup))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_HatchTime))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Gender))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Capture))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Habitat))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Generation))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_CaptureRate))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_sprites))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_normal))
        arrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Shiny))

        arrayListTextViewOfInformationStats.forEachIndexed { _, textView ->
            textView.setTextColor(
                requireContext().resources.getColor(
                    mPrimaryColor,
                    requireContext().theme
                )
            )
        }

        val arrayListProgressbarStats = ArrayList<ProgressBar>()
        arrayListProgressbarStats.add(t.findViewById(R.id.pb_HP))
        arrayListProgressbarStats.add(t.findViewById(R.id.pb_ATK))
        arrayListProgressbarStats.add(t.findViewById(R.id.pb_DEF))
        arrayListProgressbarStats.add(t.findViewById(R.id.pb_SATK))
        arrayListProgressbarStats.add(t.findViewById(R.id.pb_SDEF))
        arrayListProgressbarStats.add(t.findViewById(R.id.pb_spd))

        val arrayListTextViewNumberStats = ArrayList<TextView>()
        arrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfHP))
        arrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfATK))
        arrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfDEF))
        arrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfSATK))
        arrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfSDEF))
        arrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfSPD))

        arrayListProgressbarStats.forEachIndexed { _, progressBar ->
            progressBar.progressTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), mPrimaryColor))
        }

        mInformationPokemon.stats?.forEachIndexed { _, stat ->
            if (stat.baseStat != null) {
                when (stat.stat?.name) {
                    "hp" -> {
                        arrayListProgressbarStats[0].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> arrayListTextViewNumberStats[0].text = "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> arrayListTextViewNumberStats[0].text = "0${stat.baseStat}"
                            else -> arrayListTextViewNumberStats[0].text = stat.baseStat.toString()
                        }
                    }
                    "attack" -> {
                        arrayListProgressbarStats[1].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> arrayListTextViewNumberStats[1].text = "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> arrayListTextViewNumberStats[1].text = "0${stat.baseStat}"
                            else -> arrayListTextViewNumberStats[1].text = stat.baseStat.toString()
                        }
                    }
                    "defense" -> {
                        arrayListProgressbarStats[2].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> arrayListTextViewNumberStats[2].text = "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> arrayListTextViewNumberStats[2].text = "0${stat.baseStat}"
                            else -> arrayListTextViewNumberStats[2].text = stat.baseStat.toString()
                        }
                    }
                    "special-attack" -> {
                        arrayListProgressbarStats[3].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> arrayListTextViewNumberStats[3].text = "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> arrayListTextViewNumberStats[3].text = "0${stat.baseStat}"
                            else -> arrayListTextViewNumberStats[3].text = stat.baseStat.toString()
                        }
                    }
                    "special-defense" -> {
                        arrayListProgressbarStats[4].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> arrayListTextViewNumberStats[4].text = "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> arrayListTextViewNumberStats[4].text = "0${stat.baseStat}"
                            else -> arrayListTextViewNumberStats[4].text = stat.baseStat.toString()
                        }
                    }
                    "speed" -> {
                        arrayListProgressbarStats[5].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> arrayListTextViewNumberStats[5].text = "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> arrayListTextViewNumberStats[5].text = "0${stat.baseStat}"
                            else -> arrayListTextViewNumberStats[5].text = stat.baseStat.toString()
                        }
                    }
                }
            }

        }

        var tvContentTorrent = t.findViewById<TextView>(R.id.tv_torrentContent)
        var tvContentRainDish = t.findViewById<TextView>(R.id.tv_rainDishContent)



        val percentMale = (0..100).random()
        val percentFemale = 100 - percentMale
        val pbGender = t.findViewById<ProgressBar>(R.id.pb_Gender)
        tvPercentFemale.text = "$percentFemale%"
        tvPercentMale.text = "$percentMale%"
        pbGender.progress = percentMale
        pbGender.progressTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), mPrimaryColor))

        val percentRate = (0..100).random()
        val pbRate = t.findViewById<ProgressBar>(R.id.pb_CaptureRate)
        tvRate.text = "$percentRate%"
        pbRate.progress = percentRate

        val tvStep = t.findViewById<TextView>(R.id.tv_Steps)
        val tvCycle = t.findViewById<TextView>(R.id.tv_Cycles)

        tvStep.text = "$percentFemale Steps"
        tvCycle.text = "$percentMale Cycles"

        val arrayImg = ArrayList<ImageView>()
        arrayImg.add(t.findViewById(R.id.img_normal))
        arrayImg.add(t.findViewById(R.id.img_shiny))

        arrayImg.forEach {
            Picasso.with(requireActivity())
                .load(mInformationPokemon.sprites?.other?.officialArtwork?.frontDefault)
                .placeholder(R.drawable.egg)
                .into(it)
        }
        return t
    }


}