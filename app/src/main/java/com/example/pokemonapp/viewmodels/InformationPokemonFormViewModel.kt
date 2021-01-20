package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.models.InformationPokemonForm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationPokemonFormViewModel : ViewModel() {
    var aPokemonForm: MutableLiveData<InformationPokemonForm> = MutableLiveData()
    var notification: MutableLiveData<String> = MutableLiveData()
    fun getAPokemonForm(id: String) {
        val callGet =
            RetrofitClient.instance.getInformationAPokemonForm(id)
        callGet.enqueue(object : Callback<InformationPokemonForm> {
            override fun onFailure(call: Call<InformationPokemonForm>, t: Throwable) {
                if (callGet.isCanceled) {
                    notification.value = "Canceled successful!"
                } else {
                    notification.value = "Can't load data, please try again!"
                }
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

    fun noticeGetAllInformationPokemonSuccessful() {
        notification.value = "Load successful!"
    }


}