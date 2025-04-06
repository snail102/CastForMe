package ru.anydevprojects.castforme.podcastEpisode.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.anydevprojects.castforme.podcastEpisode.domain.models.PodcastEpisode

@Serializable
data class PodcastEpisodeDto(
    @SerialName("id")
    val id: Long,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("datePublished")
    val dateTimestamp: Long,

    @SerialName("enclosureUrl")
    val enclosureUrl: String,

    @SerialName("enclosureType")
    val enclosureType: String,

    @SerialName("enclosureLength")
    val enclosureLength: Int,

    @SerialName("duration")
    val duration: Int?,

    @SerialName("feedImage")
    val feedImage: String,

    @SerialName("image")
    val image: String,

    @SerialName("feedId")
    val feedId: Long
)


fun PodcastEpisodeDto.toDomain(): PodcastEpisode = PodcastEpisode(
    id = this.id,
    title = this.title,
    description = this.description,
    dateTimestamp = this.dateTimestamp,
    enclosureUrl = this.enclosureUrl,
    enclosureType = this.enclosureType,
    enclosureLength = this.enclosureLength,
    duration = this.duration?.toLong(),
    image = this.image.ifEmpty { this.feedImage },
    feedId = this.feedId
)