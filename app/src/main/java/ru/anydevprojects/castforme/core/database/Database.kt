package ru.anydevprojects.castforme.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.anydevprojects.castforme.favoritePodcastFeed.data.FavoritePodcastFeedDao
import ru.anydevprojects.castforme.favoritePodcastFeed.data.models.FavoritePodcastFeedEntity
import ru.anydevprojects.castforme.podcastEpisode.data.PodcastEpisodeDao
import ru.anydevprojects.castforme.podcastEpisode.data.models.PodcastEpisodeEntity
import ru.anydevprojects.castforme.podcastFeed.data.PodcastFeedDao
import ru.anydevprojects.castforme.podcastFeed.data.models.PodcastFeedEntity

@Database(
    entities = [
        FavoritePodcastFeedEntity::class,
        PodcastFeedEntity::class,
        PodcastEpisodeEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun podcastFeedDao(): PodcastFeedDao
    abstract fun favoritePodcastFeedDao(): FavoritePodcastFeedDao
    abstract fun podcastEpisodeDao(): PodcastEpisodeDao
}