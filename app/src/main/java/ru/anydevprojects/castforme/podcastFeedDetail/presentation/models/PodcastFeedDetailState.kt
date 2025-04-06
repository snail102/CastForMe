package ru.anydevprojects.castforme.podcastFeedDetail.presentation.models

data class PodcastFeedDetailState(
    val isLoading: Boolean = false,
    val podcastFeedDetailUi: PodcastFeedDetailUi = PodcastFeedDetailUi(),
    val episodes: List<PodcastEpisodePreviewItem> = emptyList()
)