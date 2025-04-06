package ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.models

data class PodcastEpisodeDetailState(
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = ""
)