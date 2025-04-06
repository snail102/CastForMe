package ru.anydevprojects.castforme.home.presentation.models

import ru.anydevprojects.castforme.podcastFeed.domain.models.PodcastFeed

sealed interface FavoriteItem {

    data object AllFavoritePodcastFeeds : FavoriteItem

    data class FavoritePodcastFeedItem(
        val id: Long,
        val imageUrl: String
    ) : FavoriteItem
}


fun PodcastFeed.toFavoriteItem(): FavoriteItem.FavoritePodcastFeedItem =
    FavoriteItem.FavoritePodcastFeedItem(
        id = id,
        imageUrl = image
    )