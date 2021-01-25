package com.example.pokemonapp.views.activitys

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.pokemonapp.R
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.models.*
import com.example.pokemonapp.viewmodels.*
import com.example.pokemonapp.views.fragments.EvolutionsFragment
import com.example.pokemonapp.views.fragments.MovesFragment
import com.example.pokemonapp.views.fragments.StatsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_pokemon.*
import kotlinx.android.synthetic.main.dialog_processbar.*

class DetailPokemonActivity : AppCompatActivity(), View.OnClickListener {

    private val mListFragment: ArrayList<Fragment> = ArrayList()
    private val mFragmentManager: FragmentManager = supportFragmentManager
    private val mFragmentTransaction = mFragmentManager.beginTransaction()
    private var mArrayListMove = ArrayList<Move>()
    private var mInformationPokemon: InformationPokemon = InformationPokemon()
    private val mDetailPokemonViewModel = DetailPokemonViewModel()
    private val mInformationPokemonFormViewModel = InformationPokemonFormViewModel()
    private val mInformationPokemonSpeciesViewModel = InformationPokemonSpeciesViewModel()
    private val mInformationPokemonViewModel = InformationPokemonViewModel()
    private val mEvolutionsViewModel = InformationEvolutionsViewModel()
    private var mInformationPokemonSpecies = InformationPokemonSpecies()
    private var mInformationPokemonForm = InformationPokemonForm()
    private lateinit var mDialog: MaterialDialog
    private var mPrimaryColor: Int = -1
    private var mListInformationPokemonCurrent: ArrayList<InformationPokemon> = ArrayList()
    private var mListNamePokemonBefore = ArrayList<String>()
    private var mListNamePokemonAfter = ArrayList<String>()
    private var mListInformationPokemonBefore: ArrayList<InformationPokemon> = ArrayList()
    private var mListInformationPokemonAfter: ArrayList<InformationPokemon> = ArrayList()
    private var mArrayListMinLevelEvolutions = ArrayList<Int>()
    private var mListCurrentLoad = ArrayList<String>()
    private var mKeyLoad = Utility.KEY_LIST_BEFORE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pokemon)
        initView()
        registerLiveDataListenerOfDetailPokemonViewModel()
        registerLiveDataListenerOfEvolutionViewModel()
        registerLiveDataListenerOfInformationPokemonFormViewModel()
        registerLiveDataListenerOfInformationPokemonSpeciesViewModel()
        registerLiveDataListenerOfInformationPokemonViewModel()
    }

    private fun initView() {
        mDialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.dialog_processbar)
        mDialog.show()
        mDialog.setCancelable(false)
        mDialog.btn_Cancel.setOnClickListener() {
            mDialog.dismiss()
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

        if (mInformationPokemon.types?.get(0)?.type?.name != null) {
            img_tag.setImageResource(Utility.nameToTag(mInformationPokemon.types?.get(0)?.type?.name!!))
            mPrimaryColor = Utility.nameToColor(mInformationPokemon.types?.get(0)?.type?.name!!)
        }
        setFirstStateColor()
        if (mInformationPokemon.name != null) {
            tv_nameOfPokemon.text = mInformationPokemon.name?.capitalize()
            tv_Name.text = mInformationPokemon.name?.capitalize()

        }
        mInformationPokemon.id?.let {
            mInformationPokemonFormViewModel.getAPokemonForm("$it")
            mInformationPokemonSpeciesViewModel.getInformationPokemonSpecies("$it")
        }
        tv_nameOfPokemon.visibility = View.INVISIBLE
        btn_HideOrShowDetail.setOnClickListener(this)
        cv_Moves.setOnClickListener(this)
        cv_Evolutions.setOnClickListener(this)
        cv_Stats.setOnClickListener(this)

    }

    private fun setFirstStateColor() {
        lo_detailPokemon.setBackgroundColor(
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

    private fun registerLiveDataListenerOfDetailPokemonViewModel() {
        mDetailPokemonViewModel.hideState.observe(this, {
            img_avatar.visibility = View.GONE
            img_tag.visibility = View.GONE
            tv_Name.visibility = View.GONE
            tv_description.visibility = View.GONE
            tv_nameOfPokemon.visibility = View.VISIBLE
            gl_topOfCardView.setGuidelinePercent(0.1F)
        })

        mDetailPokemonViewModel.showState.observe(this, {
            img_avatar.visibility = View.VISIBLE
            img_tag.visibility = View.VISIBLE
            tv_Name.visibility = View.VISIBLE
            tv_description.visibility = View.VISIBLE
            tv_nameOfPokemon.visibility = View.GONE
            gl_topOfCardView.setGuidelinePercent(0.26F)
        })
    }

    private fun registerLiveDataListenerOfInformationPokemonFormViewModel() {

        mInformationPokemonFormViewModel.aPokemonForm.observe(this, {
            it?.let {
                mInformationPokemonForm = it
            }
        })
    }

    private fun registerLiveDataListenerOfInformationPokemonSpeciesViewModel() {
        mInformationPokemonSpeciesViewModel.aPokemonSpecies.observe(this, {
            it?.let {
                mInformationPokemonSpecies = it
                mInformationPokemonSpecies.evolutionChain?.url?.let { url ->
                    mEvolutionsViewModel.getListEvolution(
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
        mEvolutionsViewModel.aPokemonEvolutions.observe(this, {
            it?.let {
                mEvolutionsViewModel.getListPokemonEvolution(it)
            }
        })
        mEvolutionsViewModel.listLevel.observe(this, {
            this.mArrayListMinLevelEvolutions = it
            mInformationPokemonViewModel.amountOfPokemon.value = 0
        })
        mEvolutionsViewModel.listNameAfter.observe(this, {
            this.mListNamePokemonAfter = it
        })
        mEvolutionsViewModel.listNameBefore.observe(this, {
            this.mListNamePokemonBefore = it

        })
    }

    private fun registerLiveDataListenerOfInformationPokemonViewModel() {
        mInformationPokemonViewModel.amountOfPokemon.observe(this, {
            if (mListNamePokemonAfter.size == it) {
                if (mKeyLoad == Utility.KEY_LIST_BEFORE) {
                    mKeyLoad = Utility.KEY_LIST_AFTER
                    mInformationPokemonViewModel.amountOfPokemon.value = 0
                } else {
                    initFragment()
                }
            } else {
                if (mKeyLoad == Utility.KEY_LIST_BEFORE) {
                    val call =
                        RetrofitClient.instance.getInformationAPokemon(mListNamePokemonBefore[it])
                    mInformationPokemonViewModel.getAPokemon(call)
                } else {
                    val call =
                        RetrofitClient.instance.getInformationAPokemon(mListNamePokemonAfter[it])
                    mInformationPokemonViewModel.getAPokemon(call)
                }

            }
        })
        mInformationPokemonViewModel.aPokemon.observe(this, {
            if (it != null) {
                if (mKeyLoad == Utility.KEY_LIST_BEFORE) {
                    mListInformationPokemonBefore.add(it)
                    mInformationPokemonViewModel.getAPokemonNext()
                } else {
                    mListInformationPokemonAfter.add(it)
                    mInformationPokemonViewModel.getAPokemonNext()
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
        mDialog.dismiss()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_HideOrShowDetail -> {
                mDetailPokemonViewModel.showOrHideInformation()
            }
            R.id.cv_Stats -> {
                val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
                mListFragment.forEachIndexed { _, fragment ->
                    fragmentTransaction.hide(fragment)
                }
                fragmentTransaction.show(mListFragment[1])
                fragmentTransaction.commit()
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