package ru.patriotovsky.gamesdatabase.models

data class Game(
    val title: String,
    val imageUrl: String,
    val description: String,
    val generes: ArrayList<String>,
    val platforms: ArrayList<String>,
    val developers: ArrayList<String>,
    val stores: ArrayList<Store>
)
