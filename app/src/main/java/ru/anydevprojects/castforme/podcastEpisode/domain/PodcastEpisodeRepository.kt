package ru.anydevprojects.castforme.podcastEpisode.domain

import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.castforme.podcastEpisode.domain.models.PodcastEpisode

interface PodcastEpisodeRepository {

    fun getPodcastEpisodeFlow(episodeId: Long): Flow<PodcastEpisode?>

    fun getPodcastEpisodesFlow(podcastId: Long): Flow<List<PodcastEpisode>>

    fun getAllEpisodesFavorites(): Flow<List<PodcastEpisode>>

    suspend fun getEpisodesByPodcastId(podcastId: Long): Result<List<PodcastEpisode>>

    suspend fun getEpisodesFromFavorites(): Result<List<PodcastEpisode>>

    suspend fun getEpisodeById(id: Long): Result<PodcastEpisode>
}