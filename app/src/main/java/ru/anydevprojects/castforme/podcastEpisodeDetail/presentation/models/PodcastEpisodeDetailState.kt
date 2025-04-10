package ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.models

import ru.anydevprojects.castforme.podcastEpisodeDetail.domain.models.DownloadEpisodeState
import ru.anydevprojects.castforme.ui.common.playerStateButton.PlayerUiState

data class PodcastEpisodeDetailState(
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val playerUiState: PlayerUiState = PlayerUiState.Pause,
    val downloadEpisodeState: DownloadEpisodeState = DownloadEpisodeState.NotDownloaded
)