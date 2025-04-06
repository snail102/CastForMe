package ru.anydevprojects.castforme.favoritePodcastFeed.data

import ru.anydevprojects.castforme.favoritePodcastFeed.data.models.FavoritePodcastFeedEntity
import ru.anydevprojects.castforme.favoritePodcastFeed.domain.FavoritePodcastFeedRepository
import javax.inject.Inject

class FavoritePodcastFeedRepositoryImpl @Inject constructor(
    private val favoritePodcastFeedDao: FavoritePodcastFeedDao,
) : FavoritePodcastFeedRepository {

    override suspend fun addToFavorite(podcastFeedId: Long): Result<Unit> {
        return runCatching {
            favoritePodcastFeedDao.insert(FavoritePodcastFeedEntity(podcastId = podcastFeedId))
        }
    }

}