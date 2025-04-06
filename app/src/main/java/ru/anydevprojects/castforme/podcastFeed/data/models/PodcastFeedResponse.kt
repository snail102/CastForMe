package ru.anydevprojects.castforme.podcastFeed.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PodcastFeedResponse(
    val status: String,
    val feed: PodcastFeedDto
)
