package com.example.pokemonapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.adapter.ListPokemonAdapter
import com.example.pokemonapp.R
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.models.InformationPokemon
import com.example.pokemonapp.models.ListPokemon
import com.example.pokemonapp.viewmodels.InformationPokemonViewModel
import com.example.pokemonapp.viewmodels.ListPokemonViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), ListPokemonAdapter.IListPokemonWithActivity {
    private lateinit var mListPokemonViewModel: ListPokemonViewModel
    private lateinit var mInformationPokemonViewModel: InformationPokemonViewModel
    private lateinit var mListPokemon: ListPokemon
    private lateinit var mListPokemonAdapter: ListPokemonAdapter
    private var mArrayListInformationPokemon = ArrayList<InformationPokemon>()
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        registerLiveDataListener()
        val call = RetrofitClient.instance.getListPokemon(0, 20)
        mListPokemonViewModel.getListPokemon(call)
    }

    private fun init() {
        mListPokemonViewModel = ListPokemonViewModel()
        mInformationPokemonViewModel = InformationPokemonViewModel()
        mListPokemon = ListPokemon()
        mListPokemonAdapter = ListPokemonAdapter(this, this)
        rv_listPokemon.layoutManager = LinearLayoutManager(this)
        rv_listPokemon.adapter = mListPokemonAdapter

    }

    private fun registerLiveDataListener() {
        mListPokemonViewModel.listPokemon.observe(this, {
            mListPokemon = it
            val call =
                RetrofitClient.instance.getInformationAPokemon(mListPokemon.results?.get(count++)?.name!!)
            mInformationPokemonViewModel.getAPokemon(call)
        })

        mInformationPokemonViewModel.aPokemon.observe(this, {
            mArrayListInformationPokemon.add(it)
            if (mListPokemon.results?.size != null && count == mListPokemon.results?.size!!) {
                mListPokemonAdapter.setList(mArrayListInformationPokemon)
            } else {
                val call =
                    RetrofitClient.instance.getInformationAPokemon(mListPokemon.results?.get(count++)?.name!!)
                mInformationPokemonViewModel.getAPokemon(call)
            }
        })
    }

    override fun onItemClick(informationPokemon: InformationPokemon, pos: Int) {

    }
}