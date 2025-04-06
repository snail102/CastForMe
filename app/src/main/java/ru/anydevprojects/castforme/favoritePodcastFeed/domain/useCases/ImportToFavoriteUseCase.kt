package ru.anydevprojects.castforme.favoritePodcastFeed.domain.useCases

import android.util.Log
import ru.anydevprojects.castforme.favoritePodcastFeed.domain.FavoritePodcastFeedRepository
import ru.anydevprojects.castforme.fileReader.domain.FileReader
import ru.anydevprojects.castforme.podcastFeed.domain.PodcastFeedRepository
import ru.anydevprojects.castforme.podcastTransformer.domain.PodcastTransformer
import javax.inject.Inject

class ImportToFavoriteUseCase @Inject constructor(
    private val fileReader: FileReader,
    private val podcastTransformer: PodcastTransformer,
    private val favoritePodcastFeedRepository: FavoritePodcastFeedRepository,
    private val podcastFeedRepository: PodcastFeedRepository //TODO Заменить на usecase
) {

    suspend operator fun invoke(filePath: String): Result<Unit> =
        runCatching {
            val contentFromFile = fileReader.getContentFromFile(filePath).getOrThrow()
            val podcastFeedTransformedList = podcastTransformer.decode(
                content =
                    contentFromFile
            ).getOrThrow()


            podcastFeedTransformedList.forEach { podcastTransformer ->
                podcastFeedRepository.getPodcastFeedByUrl(podcastTransformer.url)
                    .onSuccess { podcastFeed ->
                        favoritePodcastFeedRepository.addToFavorite(
                            podcastFeedId = podcastFeed.id
                        )
                    }.onFailure {
                        Log.d("test", it.message.toString())
                    }
            }
        }

}