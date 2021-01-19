package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.models.InformationPokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationPokemonViewModel: ViewModel() {
    var aPokemon: MutableLiveData<InformationPokemon> = MutableLiveData()
    var amountOfPokemon: MutableLiveData<Int> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    fun getAPokemon(callGet: Call<InformationPokemon>) {
        callGet.enqueue(object : Callback<InformationPokemon> {
            override fun onFailure(call: Call<InformationPokemon>, t: Throwable) {
                if (callGet.isCanceled) {
                    notification.value = "Canceled successful!"
                } else {
                    notification.value = "Can't load data, please try again!"
                }
            }
            override fun onResponse(call: Call<InformationPokemon>, response: Response<InformationPokemon>) {
                if (response.isSuccessful) {
                    aPokemon.value = response.body()
                } else {
                    aPokemon.value = null
                }
            }
        }
        )
    }
    fun noticeGetAllInformationPokemonSuccessful () {
        notification.value = "Load successful!"
    }
    fun getAPokemonNext() {
        amountOfPokemon.value = amountOfPokemon.value?.plus(1)
    }


}