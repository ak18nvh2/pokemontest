package com.example.pokemonapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.adapter.EvolutionAdapter
import com.example.pokemonapp.models.InformationPokemon
import kotlinx.android.synthetic.main.fragment_evolutions.view.*

class EvolutionsFragment() : Fragment() {

    private var mListInformationPokemon: ArrayList<InformationPokemon> = ArrayList()
    private var mListMinLevel: ArrayList<Int> = ArrayList()
    private var mPrimaryColor = -1
    constructor(arrayListInformationPokemon: ArrayList<InformationPokemon>, arrayListLv: ArrayList<Int>, primaryColor: Int):this() {
        this.mListInformationPokemon = arrayListInformationPokemon
        this.mPrimaryColor = primaryColor
        this.mListMinLevel = arrayListLv
    }
    var mAdapter: EvolutionAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = EvolutionAdapter( requireActivity())
        return inflater.inflate(R.layout.fragment_evolutions, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_Evolutions.layoutManager = LinearLayoutManager(activity)
        view.rv_Evolutions.adapter = mAdapter
        mAdapter?.setList(mListInformationPokemon,mListMinLevel,mPrimaryColor)
    }

}