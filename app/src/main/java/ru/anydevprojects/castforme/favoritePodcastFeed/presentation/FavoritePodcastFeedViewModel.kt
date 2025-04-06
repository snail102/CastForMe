package ru.anydevprojects.castforme.favoritePodcastFeed.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.anydevprojects.castforme.core.mvi.BaseViewModel
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.models.FavoritePodcastFeedIntent
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.models.FavoritePodcastFeedState
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.models.toFavoritePodcastFeedUi
import ru.anydevprojects.castforme.podcastFeed.domain.PodcastFeedRepository
import javax.inject.Inject

@HiltViewModel
class FavoritePodcastFeedViewModel @Inject constructor(
    private val podcastFeedRepository: PodcastFeedRepository
) : BaseViewModel<FavoritePodcastFeedState, FavoritePodcastFeedIntent, Nothing>(
    FavoritePodcastFeedState()
) {
    init {
        favoritePodcastsObserver()
    }

    private fun favoritePodcastsObserver() {
        podcastFeedRepository.getFavoritePodcastFeedsFlow().onEach { podcastFeeds ->
            updateState {
                copy(
                    podcasts = podcastFeeds.map { it.toFavoritePodcastFeedUi() }
                )
            }
        }.launchIn(viewModelScope)
    }

    override fun onIntent(intent: FavoritePodcastFeedIntent) {
        when (intent) {
            else -> {}
        }
    }
}