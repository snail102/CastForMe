package ru.anydevprojects.castforme.podcastFeedDetail.presentation.models

sealed interface PodcastFeedDetailIntent {
    data class OnPlayStateBtnClick(
        val episode: PodcastEpisodePreviewItem
    ): PodcastFeedDetailIntent
}