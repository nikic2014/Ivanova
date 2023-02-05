package com.example.kinopoisk

data class ParsFilm(
    val genres: List<Genre>,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val year: String
)
