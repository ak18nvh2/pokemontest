package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailPokemonViewModel : ViewModel() {
    var hide: MutableLiveData<Int> = MutableLiveData()
    var show: MutableLiveData<Int> = MutableLiveData()
    var countNumberClickButtonHideShowInformation = 0
    fun showOrHideInformation() {
        countNumberClickButtonHideShowInformation++
        if (countNumberClickButtonHideShowInformation % 2 == 1) {
            hide.value = hide.value?.plus(1)
        } else {
            show.value = show.value?.plus(1)
        }
    }

}