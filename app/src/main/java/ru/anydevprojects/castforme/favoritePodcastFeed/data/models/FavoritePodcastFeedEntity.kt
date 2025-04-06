package ru.anydevprojects.castforme.favoritePodcastFeed.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_podcast_feed",
    indices = [Index(value = ["podcast_id"], unique = true)]
)
data class FavoritePodcastFeedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "podcast_id")
    val podcastId: Long
)
