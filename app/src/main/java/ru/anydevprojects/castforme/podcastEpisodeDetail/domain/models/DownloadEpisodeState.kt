package ru.anydevprojects.castforme.podcastEpisodeDetail.domain.models

sealed interface DownloadEpisodeState {
    data object NotDownloaded : DownloadEpisodeState

    data class ProcessDownload(val float: Float) : DownloadEpisodeState

    data object Downloaded : DownloadEpisodeState
}