package ru.anydevprojects.castforme.podcastFeed.domain

import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.castforme.podcastFeed.domain.models.PodcastFeed

interface PodcastFeedRepository {

    fun getFavoritePodcastFeedsFlow(): Flow<List<PodcastFeed>>

    fun getPodcastFeedByIdFlow(id: Long): Flow<PodcastFeed>

    suspend fun getPodcastFeedByUrl(url: String): Result<PodcastFeed>

    suspend fun fetchPodcastFeedById(id: Long): Result<Unit>

}