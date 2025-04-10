package ru.anydevprojects.castforme.podcastFeedDetail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.anydevprojects.castforme.core.mvi.BaseViewModel
import ru.anydevprojects.castforme.core.player.domain.Player
import ru.anydevprojects.castforme.core.player.domain.models.MediaData
import ru.anydevprojects.castforme.core.player.domain.models.PlayMediaData
import ru.anydevprojects.castforme.core.player.domain.models.PlayState
import ru.anydevprojects.castforme.podcastEpisode.domain.PodcastEpisodeRepository
import ru.anydevprojects.castforme.podcastFeed.domain.PodcastFeedRepository
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastEpisodePreviewItem
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailIntent
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailState
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailUi
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.toPreviewItem
import javax.inject.Inject

@HiltViewModel
class PodcastFeedDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val podcastFeedRepository: PodcastFeedRepository,
    private val podcastEpisodeRepository: PodcastEpisodeRepository,
    private val player: Player
) : BaseViewModel<PodcastFeedDetailState, PodcastFeedDetailIntent, Nothing>(
    PodcastFeedDetailState()
) {

    private val args: PodcastFeedDetailScreenDestination = savedStateHandle.toRoute()

    private val podcastFeedId: Long = args.podcastFeedId

    init {
        fetchPodcastFeed()
        fetchEpisodes()
        podcastFeedObserve()
        episodesObserve()
        currentMediaObserve()
        playingStateObserve()
    }


    private fun currentMediaObserve() {
        player.currentMedia.onEach { mediaData ->
            when (mediaData) {
                is MediaData.Content -> {
                    updateState {
                        copy(
                            episodes = lastContentState.episodes.map {
                                it.copy(
                                    isPlaying = it.id == mediaData.id && player.playState.value is PlayState.Playing
                                )
                            }
                        )
                    }

                }

                MediaData.Init -> {}
                MediaData.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }


    private fun playingStateObserve() {
        player.playState.onEach { playState ->
            when (playState) {
                PlayState.Init -> {}
                PlayState.Loading -> {}
                PlayState.Pause -> {
                    updateState {
                        copy(
                            episodes = lastContentState.episodes.map {
                                it.copy(
                                    isPlaying = false
                                )
                            }
                        )

                    }
                }

                PlayState.Playing -> {
                    updateState {
                        copy(
                            episodes = lastContentState.episodes.map {
                                it.copy(
                                    isPlaying = it.id == player.currentMediaContent?.id
                                )
                            }

                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
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

    private fun podcastFeedObserve() {
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

    private fun episodesObserve() {
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
            is PodcastFeedDetailIntent.OnPlayStateBtnClick -> changePlayState(intent.episode)
        }
    }

    private fun changePlayState(item: PodcastEpisodePreviewItem) {
        viewModelScope.launch {
            player.play(
                PlayMediaData(
                    id = item.id,
                    title = item.name,
                    imageUrl = item.imageUrl,
                    audioUrl = item.audioUrl,
                    duration = item.duration
                )
            )
        }
    }
}