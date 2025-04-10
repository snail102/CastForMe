package ru.anydevprojects.castforme.root.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.anydevprojects.castforme.core.mvi.BaseViewModel
import ru.anydevprojects.castforme.core.player.domain.Player
import ru.anydevprojects.castforme.core.player.domain.models.MediaData
import ru.anydevprojects.castforme.core.player.domain.models.PlayState
import ru.anydevprojects.castforme.root.presentation.models.MainIntent
import ru.anydevprojects.castforme.root.presentation.models.MainState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val player: Player
) : BaseViewModel<MainState, MainIntent, Nothing>(
    MainState()
) {

    val trackPosition = player.progress.stateIn(
        scope = viewModelScope,
        initialValue = 0f,
        started = SharingStarted.Lazily
    )

    init {
        currentMediaFromPlayerObserve()
    }

    private fun currentMediaFromPlayerObserve() {
        viewModelScope.launch {
            player.currentMedia.combine(
                player.playState
            ) { mediaData: MediaData, playState: PlayState ->
                Log.d("playerOnMainViewModel", "mediaData: $mediaData playState: $playState")
                val content = when (mediaData) {
                    is MediaData.Content -> mediaData
                    MediaData.Init -> null
                    MediaData.Loading -> null
                }

                if (content != null) {
                    val isPlaying = when (playState) {
                        PlayState.Init -> false
                        PlayState.Loading -> false
                        PlayState.Pause -> false
                        PlayState.Playing -> true
                    }
                    updateState {
                        copy(
                            isEnablePlayerControl = true,
                            imageUrl = content.imageUrl,
                            nameEpisode = content.title,
                            isPlaying = isPlaying
                        )
                    }
                } else {
                    updateState {
                        copy(
                            isEnablePlayerControl = false
                        )
                    }
                }
            }.collect()
        }
    }


    override fun onIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.OnChangePlayState -> changeCurrentStatePlayer()
        }
    }

    private fun changeCurrentStatePlayer() {
        player.changePlayStatus()
    }

}