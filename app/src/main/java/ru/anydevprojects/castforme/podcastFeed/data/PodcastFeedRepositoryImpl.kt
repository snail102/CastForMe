package ru.anydevprojects.castforme.podcastFeed.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import ru.anydevprojects.castforme.favoritePodcastFeed.data.FavoritePodcastFeedDao
import ru.anydevprojects.castforme.podcastFeed.data.models.PodcastFeedResponse
import ru.anydevprojects.castforme.podcastFeed.data.models.toDomain
import ru.anydevprojects.castforme.podcastFeed.data.models.toEntity
import ru.anydevprojects.castforme.podcastFeed.domain.PodcastFeedRepository
import ru.anydevprojects.castforme.podcastFeed.domain.models.PodcastFeed
import javax.inject.Inject

class PodcastFeedRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val podcastFeedDao: PodcastFeedDao,
    private val favoritePodcastFeedDao: FavoritePodcastFeedDao
) : PodcastFeedRepository {
    override fun getFavoritePodcastFeedsFlow(): Flow<List<PodcastFeed>> =
        podcastFeedDao.getFavoritePodcasts().map { it.map { it.toDomain(isFavorite = true) } }

    override fun getPodcastFeedByIdFlow(id: Long): Flow<PodcastFeed> =
        podcastFeedDao.getPodcastWithFavorite(
            podcastId = id
        ).filterNotNull().map { it.toDomain() }


    override suspend fun getPodcastFeedByUrl(url: String): Result<PodcastFeed> {
        return runCatching {
            val podcastFeedResponse = httpClient.get("podcasts/byfeedurl") {
                parameter("url", url)
            }.body<PodcastFeedResponse>()

            val isFavorite = favoritePodcastFeedDao
                .getByPodcastId(podcastId = podcastFeedResponse.feed.id) != null

            val remotePodcastFeed = podcastFeedResponse.feed.toDomain(isFavorite = isFavorite)

            podcastFeedDao.insert(remotePodcastFeed.toEntity())

            remotePodcastFeed
        }
    }

    override suspend fun fetchPodcastFeedById(id: Long): Result<Unit> {
        return kotlin.runCatching {

            val podcastFeedResponse = httpClient.get("podcasts/byfeedid") {
                parameter("id", id)
            }.body<PodcastFeedResponse>()

            val isFavorite = favoritePodcastFeedDao
                .getByPodcastId(podcastId = podcastFeedResponse.feed.id) != null

            val remotePodcastFeed = podcastFeedResponse.feed.toDomain(isFavorite = isFavorite)

            podcastFeedDao.insert(remotePodcastFeed.toEntity())
        }
    }

    override suspend fun getPodcastNameByEpisodeIdFromLocal(episodeId: Long): Result<String> {
        return kotlin.runCatching {
            podcastFeedDao.getPodcastNameByEpisodeId(id = episodeId)?.title.orEmpty()
        }
    }

}