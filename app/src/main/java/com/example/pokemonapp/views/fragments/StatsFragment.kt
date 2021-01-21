package com.example.pokemonapp.views.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.adapter.AbilitiesAdapter
import com.example.pokemonapp.models.*
import com.example.pokemonapp.viewmodels.InformationAbilityViewModel
import com.squareup.picasso.Picasso

class StatsFragment() : Fragment() {
    private var mInformationPokemonForm = InformationPokemonForm()
    private var mInformationPokemonSpecies = InformationPokemonSpecies()
    private var mInformationPokemon = InformationPokemon()
    private var mPrimaryColor = -1
    private var mArrayListAbility = ArrayList<Abilities>()
    private var mArrayListItemAbilities = ArrayList<InformationItemAbilities>()
    private var mInformationAbilityViewModel = InformationAbilityViewModel()
    private lateinit var mAbilityAdapter: AbilitiesAdapter
    private val mArrayListTextViewOfInformationStats = ArrayList<TextView>()
    private val mArrayListProgressbarStats = ArrayList<ProgressBar>()
    private val mArrayListTextViewNumberStats = ArrayList<TextView>()

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
        registerLiveDataListener()

    }

    private fun registerLiveDataListener() {
        mInformationAbilityViewModel.amountOfAbilities.observe(this, {
            if (it == mArrayListAbility.size) {
                mAbilityAdapter.setList(mArrayListItemAbilities, this.mPrimaryColor)
            } else
                if (mArrayListAbility[it].ability?.url != null)
                    mInformationAbilityViewModel.getAPokemonAbilities(mArrayListAbility[it].ability?.url!!)
        })

        mInformationAbilityViewModel.aPokemonAbility.observe(this, {

            if (mInformationAbilityViewModel.amountOfAbilities.value != null && mArrayListAbility[mInformationAbilityViewModel.amountOfAbilities.value!!].ability?.name != null && it.flavorTextEntries?.get(
                    0
                )?.flavorText != null && mArrayListAbility[mInformationAbilityViewModel.amountOfAbilities.value!!].isHidden != null
            ) {
                mArrayListItemAbilities.add(
                    InformationItemAbilities(
                        mArrayListAbility[mInformationAbilityViewModel.amountOfAbilities.value!!].ability?.name!!,
                        it.flavorTextEntries?.get(0)?.flavorText!!,
                        mArrayListAbility[mInformationAbilityViewModel.amountOfAbilities.value!!].isHidden!!
                    )
                )
            }
            mInformationAbilityViewModel.getAPokemonAbilitiesNext()

        })
    }

    private fun init(t: View) {
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_percentFemale))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_percentCaptureRate))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_HP))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_ATK))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_DEF))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_SATK))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_SDEF))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_SPD))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Weaknesses))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Abilities))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_breeding))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_EggGroup))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_HatchTime))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Gender))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Capture))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Habitat))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Generation))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_CaptureRate))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_sprites))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_normal))
        mArrayListTextViewOfInformationStats.add(t.findViewById(R.id.tv_Shiny))

        mArrayListTextViewOfInformationStats.forEachIndexed { _, textView ->
            textView.setTextColor(
                requireContext().resources.getColor(
                    mPrimaryColor,
                    requireContext().theme
                )
            )
        }
        mArrayListProgressbarStats.add(t.findViewById(R.id.pb_HP))
        mArrayListProgressbarStats.add(t.findViewById(R.id.pb_ATK))
        mArrayListProgressbarStats.add(t.findViewById(R.id.pb_DEF))
        mArrayListProgressbarStats.add(t.findViewById(R.id.pb_SATK))
        mArrayListProgressbarStats.add(t.findViewById(R.id.pb_SDEF))
        mArrayListProgressbarStats.add(t.findViewById(R.id.pb_spd))
        mArrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfHP))
        mArrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfATK))
        mArrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfDEF))
        mArrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfSATK))
        mArrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfSDEF))
        mArrayListTextViewNumberStats.add(t.findViewById(R.id.tv_numberOfSPD))

        mArrayListProgressbarStats.forEachIndexed { _, progressBar ->
            progressBar.progressTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), mPrimaryColor))
        }

        mInformationPokemon.stats?.forEachIndexed { _, stat ->
            if (stat.baseStat != null) {
                when (stat.stat?.name) {
                    "hp" -> {
                        mArrayListProgressbarStats[0].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> mArrayListTextViewNumberStats[0].text =
                                "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> mArrayListTextViewNumberStats[0].text =
                                "0${stat.baseStat}"
                            else -> mArrayListTextViewNumberStats[0].text = stat.baseStat.toString()
                        }
                    }
                    "attack" -> {
                        mArrayListProgressbarStats[1].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> mArrayListTextViewNumberStats[1].text =
                                "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> mArrayListTextViewNumberStats[1].text =
                                "0${stat.baseStat}"
                            else -> mArrayListTextViewNumberStats[1].text = stat.baseStat.toString()
                        }
                    }
                    "defense" -> {
                        mArrayListProgressbarStats[2].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> mArrayListTextViewNumberStats[2].text =
                                "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> mArrayListTextViewNumberStats[2].text =
                                "0${stat.baseStat}"
                            else -> mArrayListTextViewNumberStats[2].text = stat.baseStat.toString()
                        }
                    }
                    "special-attack" -> {
                        mArrayListProgressbarStats[3].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> mArrayListTextViewNumberStats[3].text =
                                "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> mArrayListTextViewNumberStats[3].text =
                                "0${stat.baseStat}"
                            else -> mArrayListTextViewNumberStats[3].text = stat.baseStat.toString()
                        }
                    }
                    "special-defense" -> {
                        mArrayListProgressbarStats[4].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> mArrayListTextViewNumberStats[4].text =
                                "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> mArrayListTextViewNumberStats[4].text =
                                "0${stat.baseStat}"
                            else -> mArrayListTextViewNumberStats[4].text = stat.baseStat.toString()
                        }
                    }
                    "speed" -> {
                        mArrayListProgressbarStats[5].progress = stat.baseStat!!
                        when {
                            stat.baseStat!! < 10 -> mArrayListTextViewNumberStats[5].text =
                                "00${stat.baseStat}"
                            stat.baseStat!! < 100 -> mArrayListTextViewNumberStats[5].text =
                                "0${stat.baseStat}"
                            else -> mArrayListTextViewNumberStats[5].text = stat.baseStat.toString()
                        }
                    }
                }
            }

        }


        //----------abilities----------------------------------------------------------------
        val rvAbility = t.findViewById<RecyclerView>(R.id.rv_Abilities)
        mAbilityAdapter = AbilitiesAdapter(requireContext())
        rvAbility.layoutManager = LinearLayoutManager(activity)
        rvAbility.adapter = mAbilityAdapter
        if ((this.mInformationPokemon.abilities as ArrayList<Abilities>?) != null) {
            this.mArrayListAbility = (this.mInformationPokemon.abilities as ArrayList<Abilities>?)!!
            mInformationAbilityViewModel.amountOfAbilities.value = 0
        }
        //--------breeding---------------------------------------------------------------
        val tvPercentMale = t.findViewById<TextView>(R.id.tv_percentMale)
        val tvPercentFemale = t.findViewById<TextView>(R.id.tv_percentFemale)
        val pbGender = t.findViewById<ProgressBar>(R.id.pb_Gender)
        val tvStep = t.findViewById<TextView>(R.id.tv_Steps)
        val tvCycle = t.findViewById<TextView>(R.id.tv_Cycles)
        val tvEgg1 = t.findViewById<TextView>(R.id.tv_eggGroup1)
        val tvEgg2 = t.findViewById<TextView>(R.id.tv_eggGroup2)
        val percentRate = (0..100).random()
        val percentMale = (0..100).random()
        val steps = (0..5000).random()
        val cycles = (0..1000).random()
        val percentFemale = 100 - percentMale
        tvPercentFemale.text = "$percentFemale%"
        tvPercentMale.text = "$percentMale%"
        pbGender.progress = percentMale
        tvStep.text = "$steps Steps"
        tvCycle.text = "$cycles Cycles"

        if (mInformationPokemonSpecies.eggGroups?.size != null) {
            if (mInformationPokemonSpecies.eggGroups?.size!! == 1) {
                if (mInformationPokemonSpecies.eggGroups?.get(0)?.name != null) {
                    tvEgg1.text = mInformationPokemonSpecies.eggGroups?.get(0)?.name?.capitalize()
                }
            } else {
                if (mInformationPokemonSpecies.eggGroups?.get(0)?.name != null) {
                    tvEgg1.text = mInformationPokemonSpecies.eggGroups?.get(0)?.name?.capitalize()
                }
                if (mInformationPokemonSpecies.eggGroups?.get(1)?.name != null) {
                    tvEgg2.text = mInformationPokemonSpecies.eggGroups?.get(1)?.name?.capitalize()
                }
            }
        }
        //-----------------capture------------------------
        val tvHabitatContent = t.findViewById<TextView>(R.id.tv_habitatContent)
        val tvGenerationContent = t.findViewById<TextView>(R.id.tv_GenerationContent)
        val tvRate = t.findViewById<TextView>(R.id.tv_percentCaptureRate)
        val pbRate = t.findViewById<ProgressBar>(R.id.pb_CaptureRate)
        if (mInformationPokemonSpecies.habitat?.name != null) {
            tvHabitatContent.text = mInformationPokemonSpecies.habitat?.name?.capitalize()
        }
        if (mInformationPokemonSpecies.generation?.name != null) {
            tvGenerationContent.text = mInformationPokemonSpecies.generation?.name?.capitalize()
        }
        if (mInformationPokemonSpecies.captureRate != null) {
            tvRate.text = "${mInformationPokemonSpecies.captureRate}%"
            pbRate.progress = mInformationPokemonSpecies.captureRate!!
        }


        //-------------------------sprites------------------------------------
        val arrayImg = ArrayList<ImageView>()
        arrayImg.add(t.findViewById(R.id.img_normal))
        arrayImg.add(t.findViewById(R.id.img_shiny))

        arrayImg.forEach {
            Picasso.with(requireActivity())
                .load(mInformationPokemon.sprites?.other?.officialArtwork?.frontDefault)
                .placeholder(R.drawable.egg)
                .into(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val t = inflater.inflate(R.layout.fragment_stats, container, false)
        init(t)
        return t
    }


}