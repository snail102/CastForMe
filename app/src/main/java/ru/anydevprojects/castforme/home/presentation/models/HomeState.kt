package ru.anydevprojects.castforme.home.presentation.models

data class HomeState(
    val isLoading: Boolean = false,
    val favoritePodcastState: FavoritePodcastState = FavoritePodcastState(),
    val episodes: List<EpisodeUi> = emptyList()
)