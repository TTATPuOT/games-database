package ru.patriotovsky.gamesdatabase.models

import java.util.*

data class Poster(
    val id: Number,
    val title: String,
    val imageUrl: String,
    val developer: String,
    val releaseDate: Date
)