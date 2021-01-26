package com.example.pokemonapp.commons

import com.example.pokemonapp.R
import com.example.pokemonapp.api.RetrofitClient
import kotlinx.android.synthetic.main.item_list_pokemon.view.*

object Utility {

    const val KEY_SEARCH = 1
    const val KEY_DISPLAY = 2
    const val KEY_SHOW_DATA = 3
    const val KEY_SHOW_PROGRESSBAR = 4
    const val KEY_BUNDLE_INFORMATION_POKEMON = "1"
    const val KEY_LIST_BEFORE = 5
    const val KEY_LIST_AFTER = 6
    const val TIME_OUT_REQUEST_API = 5000L
    val Call_API = RetrofitClient.instance
    fun nameToImage(name: String): Int {
        when (name) {
            "water" -> {
                return R.drawable.water
            }
            "steel" -> {
                return R.drawable.steel
            }
            "rock" -> {
                return R.drawable.rock
            }
            "psychic" -> {
                return R.drawable.psychic
            }
            "poison" -> {
                return R.drawable.poison
            }
            "normal" -> {
                return R.drawable.normal
            }
            "ice" -> {
                return R.drawable.ice
            }
            "ground" -> {
                return R.drawable.ground
            }
            "grass" -> {
                return R.drawable.grass
            }
            "ghost" -> {
                return R.drawable.ghost
            }
            "flying" -> {
                return R.drawable.flying
            }
            "fire" -> {
                return R.drawable.fire
            }
            "fight" -> {
                return R.drawable.fight
            }
            "fairy" -> {
                return R.drawable.fairy
            }
            "electric" -> {
                return R.drawable.electric
            }
            "dragon" -> {
                return R.drawable.dragon
            }
            "dark" -> {
                return R.drawable.dark
            }
            "bug" -> {
                return R.drawable.bug
            }
        }
        return R.drawable.normal
    }

    fun nameToTag(name: String): Int {
        when (name) {
            "water" -> {
                return R.drawable.tag_water
            }
            "steel" -> {
                return R.drawable.tag_steel
            }
            "rock" -> {
                return R.drawable.tag_rock
            }
            "psychic" -> {
                return R.drawable.tag_psychic
            }
            "poison" -> {
                return R.drawable.tag_poison
            }
            "normal" -> {
                return R.drawable.tag_normal
            }
            "ice" -> {
                return R.drawable.tag_ice
            }
            "ground" -> {
                return R.drawable.tag_ground
            }
            "grass" -> {
                return R.drawable.tag_grass
            }
            "ghost" -> {
                return R.drawable.tag_ghost
            }
            "flying" -> {
                return R.drawable.tag_flying
            }
            "fire" -> {
                return R.drawable.tag_fire
            }
            "fight" -> {
                return R.drawable.tag_fight
            }
            "fairy" -> {
                return R.drawable.tag_fairy
            }
            "electric" -> {
                return R.drawable.tag_electric
            }
            "dragon" -> {
                return R.drawable.tag_dragon
            }
            "dark" -> {
                return R.drawable.tag_dark
            }
            "bug" -> {
                return R.drawable.tag_bug
            }
        }
        return R.drawable.tag_normal
    }

    fun nameToColor(name: String): Int {
        when (name) {
            "water" -> {
                return R.color.colorWater
            }
            "steel" -> {
                return R.color.colorSteel
            }
            "rock" -> {
                return R.color.colorRock
            }
            "psychic" -> {
                return R.color.colorPsychic
            }
            "poison" -> {
                return R.color.colorPoison
            }
            "normal" -> {
                return R.color.colorNormal
            }
            "ice" -> {
                return R.color.colorIce
            }
            "ground" -> {
                return R.color.colorGround
            }
            "grass" -> {
                return R.color.colorGrass
            }
            "ghost" -> {
                return R.color.colorGhost
            }
            "flying" -> {
                return R.color.colorFlying
            }
            "fire" -> {
                return R.color.colorFire
            }
            "fight" -> {
                return R.color.colorFight
            }
            "fairy" -> {
                return R.color.colorFairy
            }
            "electric" -> {
                return R.color.colorElectric
            }
            "dragon" -> {
                return R.color.colorDragon
            }
            "dark" -> {
                return R.color.colorDark
            }
            "bug" -> {
                return R.color.colorBug
            }
        }
        return R.color.colorNormal
    }

    fun linkToID(url: String): String {
        var id = ""
        for (i in url.length - 2 downTo 0) {
            if (url[i] == '/') {
                id = url.substring(i + 1, url.length - 1)
                break
            }
        }
        return id
    }

    fun Int.format() : String {
        return when {
            this < 10 -> {
                "#00$this"
            }
            this < 100 -> {
                "#0$this"
            }
            else -> {
                "#$this"
            }
        }
    }
}