package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.commons.Utility.Call_API
import com.example.pokemonapp.models.InformationPokemon
import com.example.pokemonapp.models.ListPokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationPokemonViewModel : ViewModel() {
    var aPokemon: MutableLiveData<InformationPokemon?> = MutableLiveData()
    var amountOfPokemon: MutableLiveData<Int> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    var isSearching: MutableLiveData<Boolean> = MutableLiveData()
    var listPokemon: MutableLiveData<ListPokemon?> = MutableLiveData()

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
                    notification.value = "Can't load list pokemon, please try again!"
                    listPokemon.value = null
                }
            }
        }
        )
    }

    fun getNextListPokemon(link: String) {
        var limit = 0
        var offset = 0
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
        offset = link.substring(idLeftOfNumberOffset, idRightOfNumberOffset).toInt()
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
                    notification.value = "Can't load data, please try again!"
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