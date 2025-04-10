package ru.anydevprojects.castforme.podcastFeed.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.castforme.podcastFeed.data.models.PodcastFeedEntity
import ru.anydevprojects.castforme.podcastFeed.data.models.PodcastFeedWithFavorite

@Dao
interface PodcastFeedDao {
    @Upsert
    suspend fun insert(podcastFeedEntity: PodcastFeedEntity)

    @Query("SELECT * FROM podcast_feed WHERE id = :id")
    fun getPodcastFeedFlowById(id: Long): Flow<PodcastFeedEntity?>

    @Query(
        """
        SELECT p.*
        FROM podcast_feed p
        JOIN favorite_podcast_feed s ON p.id = s.podcast_id;
    """
    )
    fun getFavoritePodcasts(): Flow<List<PodcastFeedEntity>>

    @Query(
        """
        SELECT p.*, 
               CASE 
                   WHEN s.podcast_id IS NOT NULL THEN 1 
                   ELSE 0 
               END AS isFavorite
        FROM podcast_feed p
        LEFT JOIN favorite_podcast_feed s ON p.id = s.podcast_id
        WHERE p.id = :podcastId
    """
    )
    fun getPodcastWithFavorite(podcastId: Long): Flow<PodcastFeedWithFavorite?>


    @Query(
        """
        SELECT * FROM podcast_feed
        WHERE id = (
            SELECT feed_id FROM podcast_episode WHERE id = :id
        )
    """
    )
    suspend fun getPodcastNameByEpisodeId(id: Long): PodcastFeedEntity?



}