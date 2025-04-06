package ru.anydevprojects.castforme.podcastTransformer.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.serializer
import nl.adaptivity.xmlutil.serialization.XML
import ru.anydevprojects.castforme.podcastTransformer.domain.PodcastTransformer
import ru.anydevprojects.castforme.podcastTransformer.domain.models.PodcastFeedTransformed
import javax.inject.Inject

class OpmlTransformer @Inject constructor() : PodcastTransformer {

    private val xml = XML {
        autoPolymorphic = true
        indentString = "  "
        defaultPolicy {
            pedantic = false
            ignoreUnknownChildren()
        }
    }

    override suspend fun decode(content: String): Result<List<PodcastFeedTransformed>> {
        return withContext(Dispatchers.Default) {
            kotlin.runCatching {
                val opml = xml.decodeFromString(serializer<Opml>(), content)
                val podcastOpmlList = mutableListOf<PodcastFeedTransformed>()

                fun flatten(outline: Outline) {
                    if (outline.outlines.isNullOrEmpty() && !outline.xmlUrl.isNullOrBlank()) {
                        outline.convertToPodcastFeedTransformed()?.let {
                            podcastOpmlList.add(it)
                        }
                    }

                    outline.outlines?.forEach { nestedOutline -> flatten(nestedOutline) }
                }

                opml.body.outlines.forEach { outline -> flatten(outline) }
                podcastOpmlList
            }
        }
    }

    override suspend fun encode(podcastFeeds: List<PodcastFeedTransformed>): Result<String> {
        return withContext(Dispatchers.Default) {
            kotlin.runCatching {
                val opml = Opml(
                    version = "2.0",
                    head = Head("Simple Podcast App Subscriptions", dateCreated = null),
                    body = Body(outlines = podcastFeeds.map { it.convertToOutline() })
                )

                val xmlString = xml.encodeToString(serializer<Opml>(), opml)

                StringBuilder(xmlString)
                    .insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n")
                    .appendLine()
                    .toString()
            }
        }
    }
}