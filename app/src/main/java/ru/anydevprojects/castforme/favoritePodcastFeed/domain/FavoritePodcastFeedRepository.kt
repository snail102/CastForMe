package ru.anydevprojects.castforme.favoritePodcastFeed.domain

import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.castforme.podcastFeed.domain.models.PodcastFeed

interface FavoritePodcastFeedRepository {

    suspend fun addToFavorite(podcastFeedId: Long): Result<Unit>

}