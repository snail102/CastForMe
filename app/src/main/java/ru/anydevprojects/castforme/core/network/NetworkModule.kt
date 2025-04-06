package ru.anydevprojects.castforme.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {


    companion object {
        @Provides
        @Singleton
        fun provideHttpClient(): HttpClient {
            return getNetworkClient()
        }
    }

}