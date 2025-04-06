package ru.anydevprojects.castforme.home.presentation.models

import ru.anydevprojects.castforme.podcastEpisode.domain.models.PodcastEpisode

data class EpisodeUi(
    val id: Long,
    val imageUrl: String,
    val name: String
)


fun PodcastEpisode.toUi() = EpisodeUi(
    id = id,
    imageUrl = image,
    name = title
)