package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.models.InformationPokemonSpecies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationPokemonSpeciesViewModel : ViewModel() {
    var aPokemonSpecies: MutableLiveData<InformationPokemonSpecies?> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    fun getInformationPokemonSpecies(id: String) {
        val callGet =
            RetrofitClient.instance.getInformationAPokemonSpecies(id)
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

    fun noticeGetSuccessful() {
        notification.value = "Load successful!"
    }
}