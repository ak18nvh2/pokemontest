package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.commons.Utility.Call_API
import com.example.pokemonapp.models.EvolvesTo
import com.example.pokemonapp.models.InformationEvolutions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationEvolutionsViewModel : ViewModel() {
    var aPokemonEvolutions: MutableLiveData<InformationEvolutions?> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    var listNameBefore: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var listNameAfter: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var listLevel: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    private val mMapInformationEvolutionName = mutableMapOf<String, ArrayList<String>>()
    private val mMapInformationEvolutionMinLevel = mutableMapOf<String, ArrayList<Int>>()
    private val mArrayListNamePokemon = ArrayList<String>()
    private val mArrNameBefore = ArrayList<String>()
    private val mArrNameAfter = ArrayList<String>()
    private val mArrLvMin = ArrayList<Int>()
    fun getListEvolution(linkEvolution: String) {
        val callGet =
            Call_API.getInformationEvolution(Utility.linkToID(linkEvolution))
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
            val arrName = mMapInformationEvolutionName[mArrayListNamePokemon[i]]
            val arrLv = mMapInformationEvolutionMinLevel[mArrayListNamePokemon[i]]
            arrName?.forEachIndexed { index, s ->
                mArrNameBefore.add(Utility.linkToID(mArrayListNamePokemon[i]))
                mArrNameAfter.add(Utility.linkToID(s))
                mArrLvMin.add(arrLv?.get(index)!!)
            }
        }
        listNameBefore.value = mArrNameBefore
        listNameAfter.value = mArrNameAfter
        listLevel.value = mArrLvMin
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
        mMapInformationEvolutionName[url] = mArrayListName
        mMapInformationEvolutionMinLevel[url] = mArrayListAge
    }

    fun noticeGetSuccessful() {
        notification.value = "Load successful!"
    }
}