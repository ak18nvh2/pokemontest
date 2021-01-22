package com.example.pokemonapp.viewmodels

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.models.InformationPokemon
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationPokemonViewModel : ViewModel() {
    var aPokemon: MutableLiveData<InformationPokemon?> = MutableLiveData()
    var amountOfPokemon: MutableLiveData<Int> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    var isSearching: MutableLiveData<Boolean> = MutableLiveData()

    fun getAPokemon(callGet: Call<InformationPokemon>) {
        callGet.enqueue(object : Callback<InformationPokemon> {
            override fun onFailure(call: Call<InformationPokemon>, t: Throwable) {
                if (callGet.isCanceled) {
                    aPokemon.value = null
                } else {
                    aPokemon.value = null
                }
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
                RetrofitClient.instance.getInformationAPokemon(
                    informationSearch
                )
            getAPokemon(call)
        }
    }


}