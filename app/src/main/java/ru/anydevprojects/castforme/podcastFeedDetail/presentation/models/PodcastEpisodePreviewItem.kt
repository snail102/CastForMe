package ru.anydevprojects.castforme.podcastFeedDetail.presentation.models

import ru.anydevprojects.castforme.podcastEpisode.domain.models.PodcastEpisode

data class PodcastEpisodePreviewItem(
    val id: Long = 0,
    val name: String = "",
    val imageUrl: String = "",
    val audioUrl: String = "",
    val duration: Long = 0L,
    val isPlaying: Boolean = false
)

fun PodcastEpisode.toPreviewItem(
    isPlaying: Boolean
): PodcastEpisodePreviewItem {
    return PodcastEpisodePreviewItem(
        id = id,
        name = title,
        imageUrl = image,
        isPlaying = isPlaying,
        audioUrl = enclosureUrl,
        duration = duration ?: 0L
    )
}
