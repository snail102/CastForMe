package ru.anydevprojects.castforme.favoritePodcastFeed.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import ru.anydevprojects.castforme.favoritePodcastFeed.data.models.FavoritePodcastFeedEntity

@Dao
interface FavoritePodcastFeedDao {

    @Upsert
    suspend fun insert(favoritePodcastFeedEntity: FavoritePodcastFeedEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(favoritePodcastFeeds: List<FavoritePodcastFeedEntity>)

    @Query("SELECT * FROM favorite_podcast_feed")
    suspend fun getAllFavorite(): List<FavoritePodcastFeedEntity>

    @Query("DELETE FROM favorite_podcast_feed WHERE podcast_id = :podcastId")
    suspend fun deleteByPodcastId(podcastId: Long)

    @Query("SELECT * FROM favorite_podcast_feed WHERE podcast_id=:podcastId ")
    suspend fun getByPodcastId(podcastId: Long): FavoritePodcastFeedEntity?

    @Delete
    suspend fun delete(favoritePodcastFeedEntity: FavoritePodcastFeedEntity)
}