package ru.anydevprojects.castforme.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.anydevprojects.castforme.favoritePodcastFeed.data.FavoritePodcastFeedDao
import ru.anydevprojects.castforme.podcastEpisode.data.PodcastEpisodeDao
import ru.anydevprojects.castforme.podcastFeed.data.PodcastFeedDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "database"
        ).build()
    }

    @Singleton
    @Provides
    fun providePodcastFeedDao(db: AppDatabase): PodcastFeedDao {
        return db.podcastFeedDao()
    }

    @Singleton
    @Provides
    fun provideFavoritePodcastFeedDao(db: AppDatabase): FavoritePodcastFeedDao {
        return db.favoritePodcastFeedDao()
    }

    @Singleton
    @Provides
    fun providePodcastEpisodeDao(db: AppDatabase): PodcastEpisodeDao {
        return db.podcastEpisodeDao()
    }
}