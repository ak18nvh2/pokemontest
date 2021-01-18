package com.example.pokemonapp

object Utility {
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
                return  R.drawable.ghost
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
        return  -1
    }
}