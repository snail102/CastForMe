package ru.anydevprojects.castforme.favoritePodcastFeed.presentation.models

data class FavoritePodcastFeedState(
    val isLoading: Boolean = false,
    val podcasts: List<FavoritePodcastFeedUi> = emptyList()
)