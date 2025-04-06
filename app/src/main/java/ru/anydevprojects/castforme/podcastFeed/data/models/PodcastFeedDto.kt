package ru.anydevprojects.castforme.podcastFeed.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.anydevprojects.castforme.podcastFeed.domain.models.PodcastFeed

@Serializable
data class PodcastFeedDto(
    @SerialName("id")
    val id: Long,

    @SerialName("title")
    val title: String,

    @SerialName("url")
    val url: String,

    @SerialName("link")
    val link: String,

    @SerialName("description")
    val description: String,

    @SerialName("image")
    val image: String,

    @SerialName("author")
    val author: String,

    @SerialName("episodeCount")
    val episodeCount: Int
)


fun PodcastFeedDto.toDomain(isFavorite: Boolean) = PodcastFeed(
    id = id,
    url = url,
    title = title,
    description = description,
    author = author,
    image = image,
    isFavorite = isFavorite
)
