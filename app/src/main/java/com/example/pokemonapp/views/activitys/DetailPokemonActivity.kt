package com.example.pokemonapp.views.activitys

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.pokemonapp.R
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.models.InformationPokemon
import com.example.pokemonapp.models.Move
import com.example.pokemonapp.views.fragments.MovesFragment
import com.example.pokemonapp.views.fragments.StatsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_pokemon.*


class DetailPokemonActivity : AppCompatActivity(), View.OnClickListener {

    private val mListFragment : ArrayList<Fragment> = ArrayList()
    private val mFragmentManager: FragmentManager = supportFragmentManager
    private val mFragmentTransaction = mFragmentManager.beginTransaction()
    private var mArrayListMove  = ArrayList<Move>()
    private lateinit var mInformationPokemon: InformationPokemon
    private var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pokemon)
        init()
    }

    @SuppressLint("ResourceAsColor")
    private fun init() {
        val intent = intent
        val bundle = intent.extras
        if( bundle != null ){
            mInformationPokemon = bundle.getSerializable("IP") as InformationPokemon
        }

        mArrayListMove = mInformationPokemon.moves as ArrayList<Move>

        mListFragment.add(StatsFragment())
        if (mInformationPokemon.types?.get(0)?.type?.name!= null) {
            mListFragment.add(MovesFragment(mArrayListMove,Utility.nameToImage(mInformationPokemon.types?.get(0)?.type?.name!!)))
        } else {
            mListFragment.add(MovesFragment(mArrayListMove))
        }


        mFragmentTransaction.add(R.id.fm_content, mListFragment[0])
        mFragmentTransaction.hide(mListFragment[0])

        mFragmentTransaction.add(R.id.fm_content, mListFragment[1])
        mFragmentTransaction.hide(mListFragment[1])

        mFragmentTransaction.show(mListFragment[0])
        mFragmentTransaction.commit()
















        tv_nameOfPokemon.visibility = View.INVISIBLE
        btn_HideOrShowDetail.setOnClickListener(this)
        cv_Moves.setOnClickListener(this)
        cv_Evolutions.setOnClickListener(this)
        cv_Stats.setOnClickListener(this)
        Picasso.with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/10036.png")
            .into(img_avatar)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_HideOrShowDetail -> {
                count++
                if (count % 2 == 1) {
                    img_avatar.visibility = View.GONE
                    img_tag.visibility = View.GONE
                    tv_Name.visibility = View.GONE
                    tv_description.visibility = View.GONE
                    tv_topOfInformation.visibility = View.GONE
                    tv_nameOfPokemon.visibility = View.VISIBLE
                } else {
                    img_avatar.visibility = View.VISIBLE
                    img_tag.visibility = View.VISIBLE
                    tv_Name.visibility = View.VISIBLE
                    tv_description.visibility = View.VISIBLE
                    tv_topOfInformation.visibility = View.VISIBLE
                    tv_nameOfPokemon.visibility = View.GONE
                }

            }
            R.id.cv_Stats -> {
                val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
                mListFragment.forEachIndexed { _, fragment ->
                    fragmentTransaction.hide(fragment)
                }
                fragmentTransaction.show(mListFragment[0])
                fragmentTransaction.commit()
                cv_Stats.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_sky_blue
                        )
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
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_sky_blue
                        )
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
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_sky_blue
                        )
                    )
                )
            }
            R.id.cv_Evolutions -> {
//                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//                listFragment.forEachIndexed { _, fragment ->
//                    fragmentTransaction.hide(fragment)
//                }
//                fragmentTransaction.show(listFragment[1])
//                fragmentTransaction.commit()
                cv_Evolutions.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_sky_blue
                        )
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
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_sky_blue
                        )
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
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_sky_blue
                        )
                    )
                )
            }
            R.id.cv_Moves -> {
                val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
                mListFragment.forEachIndexed { _, fragment ->
                    fragmentTransaction.hide(fragment)
                }
                fragmentTransaction.show(mListFragment[1])
                fragmentTransaction.commit()
                cv_Moves.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_sky_blue
                        )
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
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_sky_blue
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
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_sky_blue
                        )
                    )
                )
            }
        }
    }
}