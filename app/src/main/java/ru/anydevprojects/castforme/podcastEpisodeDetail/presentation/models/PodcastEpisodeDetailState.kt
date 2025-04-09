package ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.models

import ru.anydevprojects.castforme.podcastEpisodeDetail.domain.models.DownloadEpisodeState

data class PodcastEpisodeDetailState(
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val isPlaying: Boolean = false,
    val downloadEpisodeState: DownloadEpisodeState = DownloadEpisodeState.NotDownloaded
)