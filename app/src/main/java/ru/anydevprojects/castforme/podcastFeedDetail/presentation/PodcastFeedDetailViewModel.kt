package ru.anydevprojects.castforme.podcastFeedDetail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.anydevprojects.castforme.core.mvi.BaseViewModel
import ru.anydevprojects.castforme.home.presentation.models.toUi
import ru.anydevprojects.castforme.podcastEpisode.domain.PodcastEpisodeRepository
import ru.anydevprojects.castforme.podcastFeed.domain.PodcastFeedRepository
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailIntent
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailState
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailUi
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.toPreviewItem
import javax.inject.Inject

@HiltViewModel
class PodcastFeedDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val podcastFeedRepository: PodcastFeedRepository,
    private val podcastEpisodeRepository: PodcastEpisodeRepository
) : BaseViewModel<PodcastFeedDetailState, PodcastFeedDetailIntent, Nothing>(
    PodcastFeedDetailState()
) {

    private val args: PodcastFeedDetailScreenDestination = savedStateHandle.toRoute()

    private val podcastFeedId: Long = args.podcastFeedId

    init {
        fetchPodcastFeed()
        fetchEpisodes()
        podcastFeedObserver()
        episodesObserver()
    }

    private fun fetchPodcastFeed() {
        viewModelScope.launch {
            podcastFeedRepository.fetchPodcastFeedById(id = podcastFeedId)
        }
    }

    private fun fetchEpisodes() {
        viewModelScope.launch {
            podcastEpisodeRepository.fetchEpisodesByPodcastId(
                podcastId = podcastFeedId
            )
        }
    }

    private fun podcastFeedObserver() {
        podcastFeedRepository.getPodcastFeedByIdFlow(
            id = podcastFeedId
        ).onEach { podcastFeed ->
            updateState {
                copy(
                    isLoading = false,
                    podcastFeedDetailUi = PodcastFeedDetailUi(
                        name = podcastFeed.title,
                        imageUrl = podcastFeed.image,
                        description = podcastFeed.description
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun episodesObserver() {
        podcastEpisodeRepository.getPodcastEpisodesFlow(podcastFeedId).onEach {
            updateState {
                copy(
                    episodes = it.map { it.toPreviewItem(false) }
                )
            }
        }.launchIn(viewModelScope)
    }


    override fun onIntent(intent: PodcastFeedDetailIntent) {
        when (intent) {

            else -> {}
        }
    }
}