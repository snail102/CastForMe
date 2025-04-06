package ru.anydevprojects.castforme.podcastFeed.domain.models

data class PodcastFeed(
    val id: Long,
    val url: String,
    val title: String,
    val description: String,
    val author: String,
    val image: String,
    val isFavorite: Boolean
)
