package ru.anydevprojects.castforme.core.player.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anydevprojects.castforme.core.player.data.PlayerImpl
import ru.anydevprojects.castforme.core.player.domain.Player
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PlayerModule {

    @Binds
    @Singleton
    fun bindPlayer(impl: PlayerImpl): Player
}