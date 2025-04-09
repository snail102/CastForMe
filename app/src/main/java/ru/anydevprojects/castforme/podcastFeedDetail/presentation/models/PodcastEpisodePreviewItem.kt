package ru.anydevprojects.castforme.podcastFeedDetail.presentation.models

import ru.anydevprojects.castforme.podcastEpisode.domain.models.PodcastEpisode

data class PodcastEpisodePreviewItem(
    val id: Long = 0,
    val name: String = "",
    val imageUrl: String = "",
    val isPlaying: Boolean = false
)

fun PodcastEpisode.toPreviewItem(
    isPlaying: Boolean
): PodcastEpisodePreviewItem {
    return PodcastEpisodePreviewItem(
        id = id, name = title, imageUrl = image, isPlaying = isPlaying
    )
}
