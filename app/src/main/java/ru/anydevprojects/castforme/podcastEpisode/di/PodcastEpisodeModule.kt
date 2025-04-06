package ru.anydevprojects.castforme.podcastEpisode.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anydevprojects.castforme.podcastEpisode.data.PodcastEpisodeRepositoryImpl
import ru.anydevprojects.castforme.podcastEpisode.domain.PodcastEpisodeRepository

@Module
@InstallIn(SingletonComponent::class)
interface PodcastEpisodeModule {

    @Binds
    fun bindPodcastEpisodeRepository(impl: PodcastEpisodeRepositoryImpl): PodcastEpisodeRepository
}