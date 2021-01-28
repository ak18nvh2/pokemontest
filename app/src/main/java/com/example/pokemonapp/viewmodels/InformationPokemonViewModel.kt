package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.commons.Utility.Call_API
import com.example.pokemonapp.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationPokemonViewModel : ViewModel() {
    var aPokemon: MutableLiveData<InformationPokemon?> = MutableLiveData()
    var amountOfPokemon: MutableLiveData<Int> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    var isSearching: MutableLiveData<Boolean> = MutableLiveData()
    var listPokemon: MutableLiveData<ListPokemon?> = MutableLiveData()
    var aPokemonAbility: MutableLiveData<InformationAbilities?> = MutableLiveData()
    var amountOfAbilities: MutableLiveData<Int> = MutableLiveData()
    var aPokemonEvolutions: MutableLiveData<InformationEvolutions?> = MutableLiveData()
    var listNameBefore: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var listNameAfter: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var listLevel: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    private val mMapInformationEvolutionName = mutableMapOf<String, ArrayList<String>>()
    private val mMapInformationEvolutionMinLevel = mutableMapOf<String, ArrayList<Int>>()
    private val mArrayListNamePokemon = ArrayList<String>()
    private val mArrNameBefore = ArrayList<String>()
    private val mArrNameAfter = ArrayList<String>()
    private val mArrLvMin = ArrayList<Int>()
    var aPokemonForm: MutableLiveData<InformationPokemonForm?> = MutableLiveData()
    var aPokemonSpecies: MutableLiveData<InformationPokemonSpecies?> = MutableLiveData()
    fun getInformationPokemonSpecies(id: String) {
        val callGet =
            Call_API.getInformationAPokemonSpecies(id)
        callGet.enqueue(object : Callback<InformationPokemonSpecies> {
            override fun onFailure(call: Call<InformationPokemonSpecies>, t: Throwable) {
                if (callGet.isCanceled) {
                    notification.value = "Canceled successful!"
                } else {
                    notification.value = "Can't load data, please try again!"
                }
                aPokemonSpecies.value = null
            }

            override fun onResponse(
                call: Call<InformationPokemonSpecies>,
                response: Response<InformationPokemonSpecies>
            ) {
                if (response.isSuccessful) {
                    aPokemonSpecies.value = response.body()
                } else {
                    aPokemonSpecies.value = null
                }
            }
        }
        )
    }

    fun getAPokemonForm(id: String) {
        val callGet =
            Call_API.getInformationAPokemonForm(id)
        callGet.enqueue(object : Callback<InformationPokemonForm> {
            override fun onFailure(call: Call<InformationPokemonForm>, t: Throwable) {
                if (callGet.isCanceled) {
                    notification.value = "Canceled successful!"
                } else {
                    notification.value = "Can't load data, please try again!"
                }
                aPokemonForm.value = null
            }

            override fun onResponse(
                call: Call<InformationPokemonForm>,
                response: Response<InformationPokemonForm>
            ) {
                if (response.isSuccessful) {
                    aPokemonForm.value = response.body()
                } else {
                    aPokemonForm.value = null
                }
            }
        }
        )
    }

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

    fun getAPokemonAbilities(link: String) {
        val callGet =
            Call_API.getInformationAbilities(Utility.linkToID(link))
        callGet.enqueue(object : Callback<InformationAbilities> {
            override fun onFailure(call: Call<InformationAbilities>, t: Throwable) {
                if (callGet.isCanceled) {
                    notification.value = "Canceled successful!"
                } else {
                    notification.value = "Can't load data, please try again!"
                }
                aPokemonAbility.value = null
            }

            override fun onResponse(
                call: Call<InformationAbilities>,
                response: Response<InformationAbilities>
            ) {
                if (response.isSuccessful) {
                    aPokemonAbility.value = response.body()
                } else {
                    aPokemonAbility.value = null
                }
            }
        }
        )
    }

    fun getAPokemonAbilitiesNext() {
        amountOfAbilities.value = amountOfAbilities.value?.plus(1)
    }

    fun getListPokemon(callGet: Call<ListPokemon>) {
        callGet.enqueue(object : Callback<ListPokemon> {
            override fun onFailure(call: Call<ListPokemon>, t: Throwable) {
                if (callGet.isCanceled) {
                    notification.value = "Canceled successful!"
                } else {
                    notification.value = "Can't load list pokemon, please try again!"
                }
                listPokemon.value = null
            }

            override fun onResponse(call: Call<ListPokemon>, response: Response<ListPokemon>) {
                if (response.isSuccessful) {
                    listPokemon.value = response.body()
                } else {
                    listPokemon.value = null
                }
            }
        }
        )
    }

    fun getNextListPokemon(link: String) {
        var limit = 0
        var idRightOfNumberOffset = -1
        var idLeftOfNumberOffset = -1
        for (i in link.length - 1 downTo 0) {
            if (link[i] == '=') {
                limit = link.substring(i + 1).toInt()
                break
            }
        }
        for (i in link.length - 1 downTo 0) {
            if (link[i] == '&') {
                idRightOfNumberOffset = i
                break
            }
        }
        var numberOfCountEqual = 0
        for (i in link.length - 1 downTo 0) {
            if (link[i] == '=') {
                numberOfCountEqual++
                if (numberOfCountEqual == 2) {
                    idLeftOfNumberOffset = i + 1
                    break
                }
            }
        }
        var offset = link.substring(idLeftOfNumberOffset, idRightOfNumberOffset).toInt()
        val callGet = Call_API.getListPokemon(offset, limit)
        getListPokemon(callGet)
    }

    fun getAPokemon(callGet: Call<InformationPokemon>) {
        callGet.enqueue(object : Callback<InformationPokemon> {
            override fun onFailure(call: Call<InformationPokemon>, t: Throwable) {
                aPokemon.value = null
            }
            override fun onResponse(
                call: Call<InformationPokemon>,
                response: Response<InformationPokemon>
            ) {
                if (response.isSuccessful) {
                    aPokemon.value = response.body()
                } else {
                    aPokemon.value = null
                }
            }
        }
        )
    }

    fun noticeGetAllInformationPokemonSuccessful() {
        notification.value = "Load successful!"
    }

    fun getAPokemonNext() {
        amountOfPokemon.value = amountOfPokemon.value?.plus(1)
    }

    fun searchAPokemon(input: String) {
        val informationSearch = input.toLowerCase().trim()
        if (informationSearch == "") {
            notification.value = "Please type name or id of Pokemon!"
        } else {
            isSearching.value = true
            val call =
                Call_API.getInformationAPokemon(
                    informationSearch
                )
            getAPokemon(call)
        }
    }
}