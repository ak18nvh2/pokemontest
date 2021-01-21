package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.models.InformationEvolutions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationEvolutionsViewModel : ViewModel() {
    var aPokemonEvolutions: MutableLiveData<InformationEvolutions> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    var listPokemonName: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var listPokemonLevelEvolution: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    fun getListEvolution(linkEvolution: String) {
        var id = ""
        for (i in linkEvolution.length - 2 downTo 0) {
            if (linkEvolution[i] == '/') {
                id = linkEvolution.substring(i + 1, linkEvolution.length - 1)
                break
            }
        }
        val callGet = RetrofitClient.instance.getInformationEvolution(id)
        callGet.enqueue(object : Callback<InformationEvolutions> {
            override fun onFailure(call: Call<InformationEvolutions>, t: Throwable) {
                if (callGet.isCanceled) {
                    notification.value = "Canceled successful!"
                } else {
                    notification.value = "Can't load data, please try again!"
                }
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
        var eTo = evolutions.chain?.evolvesTo
        val arrName = ArrayList<String>()
        val arrLv = ArrayList<Int>()
        if (evolutions.chain?.species?.name != null) {
            arrName.add(evolutions.chain?.species?.name!!)
        }
        while (eTo != null && eTo.isNotEmpty()) {
            if (eTo[0].species?.name != null) {
                arrName.add(eTo[0].species?.name!!)
            } else {
                arrName.add("")
            }
            if (eTo[0].evolutionDetails?.get(0)?.minLevel != null) {
                arrLv.add(eTo[0].evolutionDetails?.get(0)?.minLevel!!)
            } else {
                arrLv.add(0)
            }
            eTo = eTo[0].evolvesTo
        }
        listPokemonLevelEvolution.value = arrLv
        listPokemonName.value = arrName
    }

    fun noticeGetSuccessful() {
        notification.value = "Load successful!"
    }
}