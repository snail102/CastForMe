package ru.anydevprojects.castforme.podcastFeed.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.anydevprojects.castforme.podcastFeed.domain.models.PodcastFeed

@Entity(tableName = "podcast_feed")
data class PodcastFeedEntity(
    @PrimaryKey
    val id: Long,
    val url: String,
    val title: String,
    val description: String,
    val image: String,
    val author: String
)


fun PodcastFeed.toEntity() = PodcastFeedEntity(
    id = id,
    url = url,
    title = title,
    description = description,
    image = image,
    author = author
)

fun PodcastFeedEntity.toDomain(isFavorite: Boolean): PodcastFeed {
    return PodcastFeed(
        id = id,
        url = url,
        title = title,
        description = description,
        image = image,
        author = author,
        isFavorite = isFavorite
    )
}