package ru.anydevprojects.castforme.favoritePodcastFeed.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anydevprojects.castforme.favoritePodcastFeed.data.FavoritePodcastFeedRepositoryImpl
import ru.anydevprojects.castforme.favoritePodcastFeed.domain.FavoritePodcastFeedRepository

@Module
@InstallIn(SingletonComponent::class)
interface FavoritePodcastFeedModule {

    @Binds
    fun bindAddFavoriteRepository(impl: FavoritePodcastFeedRepositoryImpl): FavoritePodcastFeedRepository
}