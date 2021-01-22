package com.example.pokemonapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailPokemonViewModel : ViewModel() {
    var hideState: MutableLiveData<Int> = MutableLiveData()
    var showState: MutableLiveData<Int> = MutableLiveData()
    var numberOfTimesClickButtonHideShowInformation = 0
    fun showOrHideInformation() {
        numberOfTimesClickButtonHideShowInformation++
        if (numberOfTimesClickButtonHideShowInformation % 2 == 1) {
            hideState.value = hideState.value?.plus(1)
        } else {
            showState.value = showState.value?.plus(1)
        }
    }

}