package ru.anydevprojects.castforme.podcastEpisode.domain.models

data class PodcastEpisode(
    val id: Long,
    val title: String,
    val description: String,
    val dateTimestamp: Long,
    val enclosureUrl: String,
    val enclosureType: String,
    val enclosureLength: Int,
    val duration: Long?,
    val image: String,
    val feedId: Long
)
