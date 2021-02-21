package com.example.pokemonapp.views.activitys

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.pokemonapp.R
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.commons.Utility.Call_API
import com.example.pokemonapp.models.*
import com.example.pokemonapp.viewmodels.*
import com.example.pokemonapp.views.fragments.EvolutionsFragment
import com.example.pokemonapp.views.fragments.MovesFragment
import com.example.pokemonapp.views.fragments.StatsFragment
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_pokemon.*
import kotlinx.android.synthetic.main.dialog_processbar.*
import kotlin.math.abs

class DetailPokemonActivity : AppCompatActivity(), View.OnClickListener {

    private val mListFragment: ArrayList<Fragment> = ArrayList()
    private val mFragmentManager: FragmentManager = supportFragmentManager
    private val mFragmentTransaction = mFragmentManager.beginTransaction()
    private var mArrayListMove = ArrayList<Move>()
    private var mInformationPokemon: InformationPokemon = InformationPokemon()
    private val mInformationPokemonViewModel = InformationPokemonViewModel()
    private var mInformationPokemonSpecies = InformationPokemonSpecies()
    private var mInformationPokemonForm = InformationPokemonForm()
    private lateinit var mDialog: MaterialDialog
    private var mPrimaryColor: Int = -1
    private var mListNamePokemonBefore = ArrayList<String>()
    private var mListNamePokemonAfter = ArrayList<String>()
    private var mListInformationPokemonBefore: ArrayList<InformationPokemon> = ArrayList()
    private var mListInformationPokemonAfter: ArrayList<InformationPokemon> = ArrayList()
    private var mArrayListMinLevelEvolutions = ArrayList<Int>()
    private var mKeyLoad = Utility.KEY_LIST_BEFORE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pokemon)


        initView()
        registerLiveDataListenerOfEvolutionViewModel()
        registerLiveDataListenerOfInformationPokemonFormViewModel()
        registerLiveDataListenerOfInformationPokemonSpeciesViewModel()
        registerLiveDataListenerOfInformationPokemonViewModel()
    }


    private fun dismissDialog() {
        mDialog.dismiss()
        lo_detailPokemon.alpha = 1F
    }

    private fun initView() {
        mDialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.dialog_processbar)
        mDialog.window?.setDimAmount(0F)
        lo_detailPokemon.alpha = 0.2F
        mDialog.show()
        mDialog.setCancelable(false)
        mDialog.btn_Cancel.setOnClickListener() {
            this.finish()
        }
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            mInformationPokemon =
                bundle.getSerializable(Utility.KEY_BUNDLE_INFORMATION_POKEMON) as InformationPokemon
        }
        if (mInformationPokemon.moves != null) {
            mArrayListMove = mInformationPokemon.moves as ArrayList<Move>
        }

        Picasso.with(this)
            .load(mInformationPokemon.sprites?.other?.officialArtwork?.frontDefault)
            .placeholder(R.drawable.egg)
            .into(img_avatar)
//
        if (mInformationPokemon.types?.get(0)?.type?.name != null) {
            img_tag.setImageResource(Utility.nameToTag(mInformationPokemon.types?.get(0)?.type?.name!!))
            mPrimaryColor = Utility.nameToColor(mInformationPokemon.types?.get(0)?.type?.name!!)
        }
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.WHITE)
        collapsing_toolbar_layout.setExpandedTitleColor(
            resources.getColor(
                R.color.transfer,
                this.theme
            )
        )
        collapsing_toolbar_layout.setContentScrimColor(
            resources.getColor(
                mPrimaryColor,
                this.theme
            )
        )
        window.statusBarColor = ContextCompat.getColor(this, mPrimaryColor)

        setFirstStateColor()
        if (mInformationPokemon.name != null) {
            collapsing_toolbar_layout.title = mInformationPokemon.name?.capitalize()
            tv_Name.text = mInformationPokemon.name?.capitalize()
        }
        mInformationPokemon.id?.let {
            this.mInformationPokemonViewModel.getAPokemonForm("$it")
            this.mInformationPokemonViewModel.getInformationPokemonSpecies("$it")
        }

        btn_backDetail.setOnClickListener(this)
        cv_Moves.setOnClickListener(this)
        cv_Evolutions.setOnClickListener(this)
        cv_Stats.setOnClickListener(this)

        ab_detailPokemon.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout!!.totalScrollRange == 0) {
                toolbar.elevation = 0F
                cl_content.setBackgroundColor(
                    applicationContext.resources.getColor(
                        mPrimaryColor,
                        this.theme
                    )
                )
            } else {
                cl_content.setBackgroundColor(resources.getColor(R.color.white_two,this.theme))
            }
        })
    }

    private fun setFirstStateColor() {
        collapsing_toolbar_layout.setBackgroundColor(
            applicationContext.resources.getColor(
                mPrimaryColor,
                this.theme
            )
        )

        cv_Stats.setCardBackgroundColor(
            applicationContext.resources.getColor(
                mPrimaryColor,
                this.theme
            )
        )
        tv_Stats.setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        )
        cv_Evolutions.cardElevation = 0F
        cv_Evolutions.setCardBackgroundColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    this,
                    R.color.white_two
                )
            )
        )
        tv_evolutions.setTextColor(
            applicationContext.resources.getColor(
                mPrimaryColor,
                this.theme
            )
        )
        cv_Moves.cardElevation = 0F
        cv_Moves.setCardBackgroundColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    this,
                    R.color.white_two
                )
            )
        )
        tv_moves.setTextColor(
            applicationContext.resources.getColor(
                mPrimaryColor,
                this.theme
            )
        )
    }


    private fun registerLiveDataListenerOfInformationPokemonFormViewModel() {

        this.mInformationPokemonViewModel.aPokemonForm.observe(this, {
            it?.let {
                mInformationPokemonForm = it
            }
        })
    }

    private fun registerLiveDataListenerOfInformationPokemonSpeciesViewModel() {
        this.mInformationPokemonViewModel.aPokemonSpecies.observe(this, {
            it?.let {
                mInformationPokemonSpecies = it
                mInformationPokemonSpecies.evolutionChain?.url?.let { url ->
                    this.mInformationPokemonViewModel.getListEvolution(
                        url
                    )
                }
                if (mInformationPokemonSpecies.flavorTextEntries?.get(0)?.flavorText != null) {
                    tv_description.text =
                        mInformationPokemonSpecies.flavorTextEntries?.get(0)?.flavorText
                }
            }
        })
    }

    private fun registerLiveDataListenerOfEvolutionViewModel() {
        this.mInformationPokemonViewModel.aPokemonEvolutions.observe(this, {
            it?.let {
                this.mInformationPokemonViewModel.getListPokemonEvolution(it)
            }
        })
        this.mInformationPokemonViewModel.listLevel.observe(this, {
            this.mArrayListMinLevelEvolutions = it
            this.mInformationPokemonViewModel.amountOfPokemon.value = 0
        })
        this.mInformationPokemonViewModel.listNameAfter.observe(this, {
            this.mListNamePokemonAfter = it
        })
        this.mInformationPokemonViewModel.listNameBefore.observe(this, {
            this.mListNamePokemonBefore = it
        })
    }

    private fun registerLiveDataListenerOfInformationPokemonViewModel() {
        this.mInformationPokemonViewModel.amountOfPokemon.observe(this, {
            if (mListNamePokemonAfter.size == it) {
                if (mKeyLoad == Utility.KEY_LIST_BEFORE) {
                    mKeyLoad = Utility.KEY_LIST_AFTER
                    this.mInformationPokemonViewModel.amountOfPokemon.value = 0
                } else {
                    initFragment()
                }
            } else {
                if (mKeyLoad == Utility.KEY_LIST_BEFORE) {
                    val call =
                        Call_API.getInformationAPokemon(mListNamePokemonBefore[it])
                    this.mInformationPokemonViewModel.getAPokemon(call)
                } else {
                    val call =
                        Call_API.getInformationAPokemon(mListNamePokemonAfter[it])
                    this.mInformationPokemonViewModel.getAPokemon(call)
                }

            }
        })
        this.mInformationPokemonViewModel.aPokemon.observe(this, {
            if (it != null) {
                if (mKeyLoad == Utility.KEY_LIST_BEFORE) {
                    mListInformationPokemonBefore.add(it)
                    this.mInformationPokemonViewModel.getAPokemonNext()
                } else {
                    mListInformationPokemonAfter.add(it)
                    this.mInformationPokemonViewModel.getAPokemonNext()
                }
            } else {
                initFragment()
            }
        })
    }

    private fun initFragment() {
        if (mArrayListMove != null) {
            if (mInformationPokemon.types?.get(0)?.type?.name != null) {
                mListFragment.add(
                    MovesFragment(
                        mArrayListMove,
                        Utility.nameToImage(mInformationPokemon.types?.get(0)?.type?.name!!)
                    )
                )
            } else {
                mListFragment.add(MovesFragment(mArrayListMove))
            }
        } else {
            mListFragment.add(MovesFragment())
        }
        mListFragment.add(
            StatsFragment(
                mInformationPokemon,
                mInformationPokemonSpecies,
                mInformationPokemonForm,
                mPrimaryColor
            )
        )
        mListFragment.add(
            EvolutionsFragment(
                mListInformationPokemonBefore,
                mListInformationPokemonAfter,
                mArrayListMinLevelEvolutions,
                mPrimaryColor
            )
        )
        // move 0 stat 1 evo 2
        mListFragment.forEach {
            mFragmentTransaction.add(R.id.fm_content, it)
            mFragmentTransaction.hide(it)
        }
        mFragmentTransaction.show(mListFragment[1])
        mFragmentTransaction.commit()
        dismissDialog()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_backDetail -> {
                this.finish()
            }
            R.id.cv_Stats -> {
                val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
                mListFragment.forEachIndexed { _, fragment ->
                    fragmentTransaction.hide(fragment)
                }
                fragmentTransaction.show(mListFragment[1])
                fragmentTransaction.commit()

                cv_Stats.cardElevation = 6F
                cv_Stats.setCardBackgroundColor(
                    applicationContext.resources.getColor(
                        mPrimaryColor,
                        this.theme
                    )
                )
                tv_Stats.setTextColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )
                )
                cv_Evolutions.cardElevation = 0F
                cv_Evolutions.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.white_two
                        )
                    )
                )
                tv_evolutions.setTextColor(
                    applicationContext.resources.getColor(
                        mPrimaryColor,
                        this.theme
                    )
                )
                cv_Moves.cardElevation = 0F
                cv_Moves.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.white_two
                        )
                    )
                )
                tv_moves.setTextColor(
                    applicationContext.resources.getColor(
                        mPrimaryColor,
                        this.theme
                    )
                )
            }
            R.id.cv_Evolutions -> {
                val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
                mListFragment.forEachIndexed { _, fragment ->
                    fragmentTransaction.hide(fragment)
                }
                fragmentTransaction.show(mListFragment[2])
                fragmentTransaction.commit()
                cv_Evolutions.cardElevation = 6F
                cv_Evolutions.setCardBackgroundColor(
                    applicationContext.resources.getColor(
                        mPrimaryColor,
                        this.theme
                    )
                )
                tv_evolutions.setTextColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )
                )
                cv_Stats.cardElevation = 0F
                cv_Stats.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.white_two
                        )
                    )
                )
                tv_Stats.setTextColor(
                    applicationContext.resources.getColor(
                        mPrimaryColor,
                        this.theme
                    )
                )
                cv_Moves.cardElevation = 0F
                cv_Moves.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.white_two
                        )
                    )
                )
                tv_moves.setTextColor(
                    applicationContext.resources.getColor(
                        mPrimaryColor,
                        this.theme
                    )
                )
            }
            R.id.cv_Moves -> {
                val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
                mListFragment.forEachIndexed { _, fragment ->
                    fragmentTransaction.hide(fragment)
                }
                fragmentTransaction.show(mListFragment[0])
                fragmentTransaction.commit()
                cv_Moves.cardElevation = 6F
                cv_Moves.setCardBackgroundColor(
                    applicationContext.resources.getColor(
                        mPrimaryColor,
                        this.theme
                    )
                )
                tv_moves.setTextColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )
                )
                cv_Evolutions.cardElevation = 0F
                cv_Evolutions.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.white_two
                        )
                    )
                )
                tv_evolutions.setTextColor(
                    applicationContext.resources.getColor(
                        mPrimaryColor,
                        this.theme
                    )
                )
                cv_Stats.cardElevation = 0f
                cv_Stats.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.white_two
                        )
                    )
                )
                tv_Stats.setTextColor(
                    applicationContext.resources.getColor(
                        mPrimaryColor,
                        this.theme
                    )
                )
            }
        }
    }
}