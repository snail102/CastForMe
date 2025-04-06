package ru.anydevprojects.castforme.podcastFeed.data.models

import androidx.room.Embedded
import ru.anydevprojects.castforme.podcastFeed.domain.models.PodcastFeed

data class PodcastFeedWithFavorite(
    @Embedded val podcastFeedEntity: PodcastFeedEntity,
    val isFavorite: Boolean
)


fun PodcastFeedWithFavorite.toDomain(): PodcastFeed = PodcastFeed(
    id = podcastFeedEntity.id,
    url = podcastFeedEntity.url,
    title = podcastFeedEntity.title,
    description = podcastFeedEntity.description,
    author = podcastFeedEntity.author,
    image = podcastFeedEntity.image,
    isFavorite = isFavorite
)
