package ru.anydevprojects.castforme.podcastEpisode.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PodcastEpisodeByIdResponse(
    val status: String,
    val id: Long,
    val episode: PodcastEpisodeDto
)
