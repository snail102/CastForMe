package ru.anydevprojects.castforme.home.presentation.models

import androidx.compose.runtime.Immutable

@Immutable
data class FavoritePodcastState(
    val isLoading: Boolean = false,
    val favoritePodcastFeeds: List<FavoriteItem> = emptyList()
)