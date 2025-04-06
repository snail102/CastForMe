package ru.anydevprojects.castforme.favoritePodcastFeed.presentation.models

import ru.anydevprojects.castforme.podcastFeed.domain.models.PodcastFeed

data class FavoritePodcastFeedUi(
    val id: Long,
    val title: String,
    val imageUrl: String
)


fun PodcastFeed.toFavoritePodcastFeedUi() = FavoritePodcastFeedUi(
    id = id,
    title = title,
    imageUrl = image
)