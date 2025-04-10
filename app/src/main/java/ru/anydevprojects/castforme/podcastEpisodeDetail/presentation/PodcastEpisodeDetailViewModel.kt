package ru.anydevprojects.castforme.podcastEpisodeDetail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.anydevprojects.castforme.core.mvi.BaseViewModel
import ru.anydevprojects.castforme.core.player.domain.Player
import ru.anydevprojects.castforme.core.player.domain.models.MediaData
import ru.anydevprojects.castforme.core.player.domain.models.PlayMediaData
import ru.anydevprojects.castforme.core.player.domain.models.PlayState
import ru.anydevprojects.castforme.podcastEpisode.domain.PodcastEpisodeRepository
import ru.anydevprojects.castforme.podcastEpisode.domain.models.PodcastEpisode
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.models.PodcastEpisodeDetailIntent
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.models.PodcastEpisodeDetailState
import ru.anydevprojects.castforme.ui.common.playerStateButton.PlayerUiState
import ru.anydevprojects.castforme.ui.common.playerStateButton.toUi
import javax.inject.Inject

@HiltViewModel
class PodcastEpisodeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val podcastEpisodeRepository: PodcastEpisodeRepository,
    private val player: Player
) : BaseViewModel<PodcastEpisodeDetailState, PodcastEpisodeDetailIntent, Nothing>(
    PodcastEpisodeDetailState()
) {

    private val args: PodcastEpisodeDetailScreenDestination = savedStateHandle.toRoute()
    private val episodeId: Long = args.episodeId

    private var podcastEpisode: PodcastEpisode? = null

    init {
        playStateObserve()
        podcastEpisodeDetailObserve()
    }

    private fun playStateObserve() {
        viewModelScope.launch {
            player.playState.combine(
                player.currentMedia
            ) { playState, media ->
                if (media is MediaData.Content) {
                    updateState {
                        copy(
                            playerUiState = if (media.id == episodeId) playState.toUi() else PlayerUiState.Pause
                        )
                    }
                }

            }.collect()
        }
    }

    private fun podcastEpisodeDetailObserve() {
        podcastEpisodeRepository.getPodcastEpisodeFlow(episodeId = episodeId)
            .onEach { podcastEpisode ->
                this.podcastEpisode = podcastEpisode
                if (podcastEpisode == null) {
                    return@onEach
                }
                updateState {
                    copy(
                        isLoading = false,
                        title = podcastEpisode.title,
                        description = podcastEpisode.description,
                        imageUrl = podcastEpisode.image
                    )
                }

            }.launchIn(viewModelScope)
    }

    override fun onIntent(intent: PodcastEpisodeDetailIntent) {
        when (intent) {
            PodcastEpisodeDetailIntent.OnPlayStateChange -> changePlayState()
        }
    }

    private fun changePlayState() {
        podcastEpisode?.let { episode ->
            player.play(
                playMediaData = PlayMediaData(
                    id = episode.id,
                    title = episode.title,
                    imageUrl = episode.image,
                    audioUrl = episode.enclosureUrl,
                    duration = episode.duration ?: 0L
                )
            )
        }
    }
}