package ru.anydevprojects.castforme.podcastFeed.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anydevprojects.castforme.podcastFeed.data.PodcastFeedRepositoryImpl
import ru.anydevprojects.castforme.podcastFeed.domain.PodcastFeedRepository

@Module
@InstallIn(SingletonComponent::class)
interface PodcastFeedModule {

    @Binds
    fun bindPodcastFeedRepository(impl: PodcastFeedRepositoryImpl): PodcastFeedRepository
}