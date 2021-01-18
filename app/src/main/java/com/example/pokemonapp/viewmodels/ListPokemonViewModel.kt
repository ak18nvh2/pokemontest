package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.models.InformationPokemon
import com.example.pokemonapp.models.ListPokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPokemonViewModel: ViewModel() {
    var listPokemon: MutableLiveData<ListPokemon> = MutableLiveData()

    fun getListPokemon(callGet: Call<ListPokemon>) {

        callGet.enqueue(object : Callback<ListPokemon> {
            override fun onFailure(call: Call<ListPokemon>, t: Throwable) {
                if (callGet.isCanceled) {
                    // notification.value = "Canceled successful!"
                } else {
                    // notification.value = "Can't load data, please try again!"
                }
            }

            override fun onResponse(call: Call<ListPokemon>, response: Response<ListPokemon>) {
                if (response.isSuccessful) {
                    listPokemon.value = response.body()
                    //notification.value = "Load successful!"
                } else {
                    // notification.value = "Can't load data, please try again!"
                }
            }
        }
        )

    }

}