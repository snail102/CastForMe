package ru.anydevprojects.castforme.podcastTransformer.domain

import ru.anydevprojects.castforme.podcastTransformer.domain.models.PodcastFeedTransformed

interface PodcastTransformer {

    suspend fun decode(content: String): Result<List<PodcastFeedTransformed>>

    suspend fun encode(podcastFeeds: List<PodcastFeedTransformed>): Result<String>
}