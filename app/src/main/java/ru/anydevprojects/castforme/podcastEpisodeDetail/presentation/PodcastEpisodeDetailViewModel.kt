package ru.anydevprojects.castforme.podcastEpisodeDetail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.anydevprojects.castforme.core.mvi.BaseViewModel
import ru.anydevprojects.castforme.podcastEpisode.domain.PodcastEpisodeRepository
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.models.PodcastEpisodeDetailState
import javax.inject.Inject

@HiltViewModel
class PodcastEpisodeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val podcastEpisodeRepository: PodcastEpisodeRepository
) : BaseViewModel<PodcastEpisodeDetailState, Nothing, Nothing>(PodcastEpisodeDetailState()) {

    private val args: PodcastEpisodeDetailScreenDestination = savedStateHandle.toRoute()
    private val episodeId: Long = args.episodeId


    init {
        podcastEpisodeDetailObserver()
    }

    private fun podcastEpisodeDetailObserver() {
        podcastEpisodeRepository.getPodcastEpisodeFlow(episodeId = episodeId)
            .onEach { podcastEpisode ->
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

    override fun onIntent(intent: Nothing) {
        TODO("Not yet implemented")
    }
}