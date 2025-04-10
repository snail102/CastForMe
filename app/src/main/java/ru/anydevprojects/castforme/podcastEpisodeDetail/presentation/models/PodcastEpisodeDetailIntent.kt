package ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.models

sealed interface PodcastEpisodeDetailIntent {
    data object OnPlayStateChange: PodcastEpisodeDetailIntent
}