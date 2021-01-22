package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.models.InformationAbilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationAbilityViewModel : ViewModel() {
    var aPokemonAbility: MutableLiveData<InformationAbilities?> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    var amountOfAbilities: MutableLiveData<Int> = MutableLiveData()
    fun getAPokemonAbilities(link: String) {
        var id = ""
        for (i in link.length - 2 downTo 0) {
            if (link[i] == '/') {
                id = link.substring(i + 1, link.length - 1)
                break
            }
        }
        val callGet =
            RetrofitClient.instance.getInformationAbilities(id)
        callGet.enqueue(object : Callback<InformationAbilities> {
            override fun onFailure(call: Call<InformationAbilities>, t: Throwable) {
                if (callGet.isCanceled) {
                    aPokemonAbility.value = null
                    notification.value = "Canceled successful!"
                } else {
                    aPokemonAbility.value = null
                    notification.value = "Can't load data, please try again!"
                }
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

    fun noticeGetAllInformationPokemonSuccessful() {
        notification.value = "Load successful!"
    }


}