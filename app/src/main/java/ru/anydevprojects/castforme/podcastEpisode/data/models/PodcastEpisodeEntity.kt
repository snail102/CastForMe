package ru.anydevprojects.castforme.podcastEpisode.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.anydevprojects.castforme.podcastEpisode.domain.models.PodcastEpisode

@Entity(tableName = "podcast_episode")
data class PodcastEpisodeEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val description: String,
    @ColumnInfo(name = "date_timestamp")
    val dateTimestamp: Long,
    @ColumnInfo(name = "enclosure_url")
    val enclosureUrl: String,
    @ColumnInfo(name = "enclosure_type")
    val enclosureType: String,
    @ColumnInfo(name = "enclosure_length")
    val enclosureLength: Int,
    val duration: Int?,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "feed_id")
    val feedId: Long
)

fun PodcastEpisodeEntity.toDomain(): PodcastEpisode = PodcastEpisode(
    id = this.id,
    title = this.title,
    description = this.description,
    dateTimestamp = this.dateTimestamp,
    enclosureUrl = this.enclosureUrl,
    enclosureType = this.enclosureType,
    enclosureLength = this.enclosureLength,
    duration = this.duration?.toLong(),
    image = this.image,
    feedId = this.feedId
)


fun PodcastEpisode.toEntity(): PodcastEpisodeEntity = PodcastEpisodeEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    dateTimestamp = this.dateTimestamp,
    enclosureUrl = this.enclosureUrl,
    enclosureType = this.enclosureType,
    enclosureLength = this.enclosureLength,
    duration = this.duration?.toInt(),
    image = this.image,
    feedId = this.feedId
)

