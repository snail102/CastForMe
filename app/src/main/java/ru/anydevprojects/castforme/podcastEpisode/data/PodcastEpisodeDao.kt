package ru.anydevprojects.castforme.podcastEpisode.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.castforme.podcastEpisode.data.models.PodcastEpisodeEntity

@Dao
interface PodcastEpisodeDao {
    @Upsert
    suspend fun insert(podcastEpisodeEntity: PodcastEpisodeEntity)

    @Upsert
    suspend fun insert(podcastEpisodes: List<PodcastEpisodeEntity>)

    @Query("SELECT * FROM podcast_episode WHERE id = :id")
    fun getEpisodeFlow(id: Long): Flow<PodcastEpisodeEntity?>

    @Query("SELECT * FROM podcast_episode WHERE feed_id = :id")
    fun getEpisodesFlow(id: Long): Flow<List<PodcastEpisodeEntity>>

    @Query(
        """
        SELECT e.*
        FROM podcast_episode e
        JOIN favorite_podcast_feed s ON e.feed_id = s.podcast_id
        ORDER BY e.date_timestamp DESC;
    """
    )
    fun getAllEpisodesSubscriptions(): Flow<List<PodcastEpisodeEntity>>

    @Delete
    suspend fun delete(podcastEpisodeEntity: PodcastEpisodeEntity)
}