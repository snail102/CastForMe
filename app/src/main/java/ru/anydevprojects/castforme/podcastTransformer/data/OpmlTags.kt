package ru.anydevprojects.castforme.podcastTransformer.data

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import ru.anydevprojects.castforme.podcastTransformer.domain.models.PodcastFeedTransformed

@Serializable
@XmlSerialName("opml")
data class Opml(
    @XmlElement(value = false) val version: String?,
    @XmlElement(value = false) val head: Head?,
    val body: Body
)

@Serializable
@XmlSerialName("head")
data class Head(@XmlElement val title: String, @XmlElement val dateCreated: String?)

@Serializable
@XmlSerialName("body")
data class Body(@XmlSerialName("outline") val outlines: List<Outline>)

@Serializable
@XmlSerialName("outline")
data class Outline(
    @XmlElement(value = false) val title: String?,
    @XmlElement(value = false) val text: String?,
    @XmlElement(value = false) val type: String?,
    @XmlElement(value = false) val xmlUrl: String?,
    @XmlElement(value = false) val htmlUrl: String?,
    @XmlSerialName("outline") val outlines: List<Outline>?
)



 fun Outline.convertToPodcastFeedTransformed(): PodcastFeedTransformed? {
    return if (this.title != null && this.xmlUrl != null) {
        PodcastFeedTransformed(
            title = this.title,
            url = this.xmlUrl
        )
    } else {
        null
    }
}

fun PodcastFeedTransformed.convertToOutline(): Outline {
    return Outline(
        title = this.title,
        text = this.title,
        type = "rss",
        xmlUrl = this.url,
        htmlUrl = "",
        outlines = null
    )
}
