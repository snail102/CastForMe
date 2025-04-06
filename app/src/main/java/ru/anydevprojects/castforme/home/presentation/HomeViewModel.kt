package ru.anydevprojects.castforme.home.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.anydevprojects.castforme.core.mvi.BaseViewModel
import ru.anydevprojects.castforme.favoritePodcastFeed.domain.useCases.ImportToFavoriteUseCase
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.models.toFavoritePodcastFeedUi
import ru.anydevprojects.castforme.home.presentation.models.FavoriteItem
import ru.anydevprojects.castforme.home.presentation.models.HomeEvent
import ru.anydevprojects.castforme.home.presentation.models.HomeIntent
import ru.anydevprojects.castforme.home.presentation.models.HomeState
import ru.anydevprojects.castforme.home.presentation.models.toFavoriteItem
import ru.anydevprojects.castforme.home.presentation.models.toUi
import ru.anydevprojects.castforme.podcastEpisode.domain.PodcastEpisodeRepository
import ru.anydevprojects.castforme.podcastFeed.domain.PodcastFeedRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val importToFavoriteUseCase: ImportToFavoriteUseCase,
    private val podcastEpisodeRepository: PodcastEpisodeRepository,
    private val podcastFeedRepository: PodcastFeedRepository
) : BaseViewModel<HomeState, HomeIntent, HomeEvent>(
    HomeState()
) {

    init {
        podcastFeedFavoriteObserver()
        episodeFromFavoritePodcastFeedObserver()
    }

    private fun episodeFromFavoritePodcastFeedObserver() {
        podcastEpisodeRepository.getAllEpisodesFavorites().onEach { podcastEpisodes ->
            updateState {
                copy(
                    episodes = podcastEpisodes.map { it.toUi() }
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun podcastFeedFavoriteObserver() {
        podcastFeedRepository.getFavoritePodcastFeedsFlow().onEach { podcastFeeds ->
            Log.d("favoritePodcasts", podcastFeeds.toString())
            updateState {
                copy(
                    favoritePodcastState = lastContentState.favoritePodcastState.copy(
                        isLoading = false,
                        favoritePodcastFeeds = buildList {
                            add(FavoriteItem.AllFavoritePodcastFeeds)
                            addAll(podcastFeeds.map { it.toFavoriteItem() })
                        }
                    )
                )
            }
        }.launchIn(viewModelScope)
    }


    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.OnImportBtnClick -> openFileChoose()
            is HomeIntent.SelectedImportFile -> startImportDataFromFile(intent.uri)
        }
    }

    private fun openFileChoose() {
        emitEvent(
            HomeEvent.OpenFileChoose
        )
    }

    private fun startImportDataFromFile(uri: Uri?) {
        viewModelScope.launch {
            if (uri == null) {
                return@launch
            }
            importToFavoriteUseCase(uri.toString())
        }
    }

}