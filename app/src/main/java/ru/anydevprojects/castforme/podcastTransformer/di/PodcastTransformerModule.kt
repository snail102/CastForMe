package ru.anydevprojects.castforme.podcastTransformer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anydevprojects.castforme.podcastTransformer.data.OpmlTransformer
import ru.anydevprojects.castforme.podcastTransformer.domain.PodcastTransformer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PodcastTransformerModule {

    @Binds
    @Singleton
    fun bindPodcastTransformer(impl: OpmlTransformer): PodcastTransformer
}