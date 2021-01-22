package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.models.ListPokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPokemonViewModel : ViewModel() {
    var listPokemon: MutableLiveData<ListPokemon?> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    var callGet: Call<ListPokemon>? = null
    fun getListPokemon(offset: Int, limit: Int) {
        callGet = RetrofitClient.instance.getListPokemon(offset, limit)
        callGet!!.enqueue(object : Callback<ListPokemon> {
            override fun onFailure(call: Call<ListPokemon>, t: Throwable) {
                if (callGet!!.isCanceled) {
                    notification.value = "Canceled successful!"
                    listPokemon.value = null
                } else {
                    notification.value = "Can't load list pokemon, please try again!"
                    listPokemon.value = null
                }
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
        var countEqual = 0
        for (i in link.length - 1 downTo 0) {
            if (link[i] == '=') {
                countEqual++
                if (countEqual == 2) {
                    idLeftOfNumberOffset = i + 1
                    break
                }
            }
        }
        offset = link.substring(idLeftOfNumberOffset, idRightOfNumberOffset).toInt()
        getListPokemon(offset, limit)
    }

}