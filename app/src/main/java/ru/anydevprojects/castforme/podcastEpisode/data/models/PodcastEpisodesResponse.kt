package ru.anydevprojects.castforme.podcastEpisode.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PodcastEpisodesResponse(
    val status: String,
    val items: List<PodcastEpisodeDto>
)
