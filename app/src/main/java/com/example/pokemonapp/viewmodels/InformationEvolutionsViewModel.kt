package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.models.EvolvesTo
import com.example.pokemonapp.models.InformationEvolutions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationEvolutionsViewModel : ViewModel() {
    var aPokemonEvolutions: MutableLiveData<InformationEvolutions?> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    var listPokemonName: MutableLiveData<ArrayList<String>?> = MutableLiveData()
    var listPokemonLevelEvolution: MutableLiveData<ArrayList<Int>?> = MutableLiveData()
    var listNameBefore: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var listNameAfter: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var listLevel: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    private val mMapInformationEvolution = mutableMapOf<String, ArrayList<String>>()
    private val mMapInformationAgeEvolution = mutableMapOf<String, ArrayList<Int>>()
    private val mArrayListNamePokemon = ArrayList<String>()
    private val arrNameBefore = ArrayList<String>()
    private val arrNameAfter = ArrayList<String>()
    private val arrLvMin = ArrayList<Int>()
    fun getListEvolution(linkEvolution: String) {
        val callGet =
            RetrofitClient.instance.getInformationEvolution(Utility.linkToID(linkEvolution))
        callGet.enqueue(object : Callback<InformationEvolutions> {
            override fun onFailure(call: Call<InformationEvolutions>, t: Throwable) {
                if (callGet.isCanceled) {
                    notification.value = "Canceled successful!"
                } else {
                    notification.value = "Can't load data, please try again!"
                }
                aPokemonEvolutions.value = null
            }

            override fun onResponse(
                call: Call<InformationEvolutions>,
                response: Response<InformationEvolutions>
            ) {
                if (response.isSuccessful) {
                    aPokemonEvolutions.value = response.body()
                } else {
                    aPokemonEvolutions.value = null
                }
            }
        }
        )
    }

    fun getListPokemonEvolution(evolutions: InformationEvolutions) {
        val eTo = evolutions.chain?.evolvesTo
        evolutions.chain?.species?.url?.let {
            getNameAndLevelEvolution(
                eTo as ArrayList<EvolvesTo>,
                it
            )
        }
        for (i in mArrayListNamePokemon.lastIndex downTo 0) {
            val arrName = mMapInformationEvolution[mArrayListNamePokemon[i]]
            val arrLv = mMapInformationAgeEvolution[mArrayListNamePokemon[i]]
            arrName?.forEachIndexed { index, s ->
                arrNameBefore.add(Utility.linkToID(mArrayListNamePokemon[i]))
                arrNameAfter.add(Utility.linkToID(s))
                arrLvMin.add(arrLv?.get(index)!!)
            }
        }
        listNameBefore.value = arrNameBefore
        listNameAfter.value = arrNameAfter
        listLevel.value = arrLvMin
    }

    private fun getNameAndLevelEvolution(eTo: ArrayList<EvolvesTo>, url: String) {
        val mArrayListName = ArrayList<String>()
        val mArrayListAge = ArrayList<Int>()
        eTo.forEachIndexed { _, evolvesTo ->
            evolvesTo.species?.url?.let {
                getNameAndLevelEvolution(
                    evolvesTo.evolvesTo as ArrayList<EvolvesTo>,
                    it
                )
                mArrayListName.add(it)
            }
            if (evolvesTo.evolutionDetails?.get(0)?.minLevel != null) {
                mArrayListAge.add(evolvesTo.evolutionDetails?.get(0)?.minLevel!!)
            } else {
                mArrayListAge.add(-1)
            }
        }
        mArrayListNamePokemon.add(url)
        mMapInformationEvolution[url] = mArrayListName
        mMapInformationAgeEvolution[url] = mArrayListAge
    }


    fun noticeGetSuccessful() {
        notification.value = "Load successful!"
    }
}