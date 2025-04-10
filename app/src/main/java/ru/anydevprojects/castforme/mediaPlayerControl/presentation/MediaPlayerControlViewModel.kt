package ru.anydevprojects.castforme.mediaPlayerControl.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.anydevprojects.castforme.core.mvi.BaseViewModel
import ru.anydevprojects.castforme.core.player.domain.Player
import ru.anydevprojects.castforme.core.player.domain.models.MediaData
import ru.anydevprojects.castforme.core.player.domain.models.PlayState
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.models.DurationTimeFormat
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.models.MediaPlayerControlIntent
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.models.MediaPlayerControlState
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.models.TimePosition
import ru.anydevprojects.castforme.podcastFeed.domain.PodcastFeedRepository
import javax.inject.Inject

@HiltViewModel
class MediaPlayerControlViewModel @Inject constructor(
    private val player: Player,
    private val podcastFeedRepository: PodcastFeedRepository
) :
    BaseViewModel<MediaPlayerControlState, MediaPlayerControlIntent, Nothing>(
        MediaPlayerControlState()
    ) {


    private val _timePosition: MutableStateFlow<TimePosition> = MutableStateFlow(TimePosition())
    val timePosition = _timePosition.asStateFlow()

    private var durationTimeFormat: DurationTimeFormat = DurationTimeFormat.HOUR

    private var isProcessingChangeProgress: Boolean = false
    private var timeBeforeStartMoveTrack: Long = 0

    init {
        currentMediaDataObserve()
        progressPlayObserve()
        currentPodcastFeedNameObserve()
    }

    private fun currentMediaDataObserve() {
        viewModelScope.launch {
            player.currentMedia.combine(
                player.playState
            ) { mediaData: MediaData, playState: PlayState ->
                Log.d("playState", playState.toString())
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
                    durationTimeFormat = maxDurationTimeFormat(content.totalDurationMs)
                    updateState {
                        copy(
                            episodeName = content.title,
                            imageUrl = content.imageUrl,
                            totalDuration = formatMillisToDynamicTime(
                                millis = content.totalDurationMs,
                                durationTimeFormat = durationTimeFormat
                            ),
                            isPlaying = isPlaying
                        )
                    }
                }
            }.collect()
        }
    }

    private fun progressPlayObserve() {
        viewModelScope.launch {
            player.progress.combine(
                player.currentDuration
            ) { progress, currentDuration ->
                Log.d("progressPlaying", progress.toString())
                _timePosition.update {
                    it.copy(
                        currentTimeMs = currentDuration,
                        currentTime = formatMillisToDynamicTime(
                            currentDuration,
                            durationTimeFormat
                        ),
                        trackPosition = if (!isProcessingChangeProgress) {
                            progress
                        } else {
                            it.trackPosition
                        }
                    )
                }
            }.collect()
        }
    }

    private fun currentPodcastFeedNameObserve() {
        viewModelScope.launch {
            player.currentMedia.filterIsInstance<MediaData.Content>()
                .map { it.id }
                .distinctUntilChanged()
                .collect { id ->
                    val podcastName = podcastFeedRepository.getPodcastNameByEpisodeIdFromLocal(
                        episodeId = id
                    ).getOrElse { "" }
                    updateState {
                        copy(
                            podcastName = podcastName
                        )
                    }
                }
        }
    }

    override fun onIntent(intent: MediaPlayerControlIntent) {
        when (intent) {
            is MediaPlayerControlIntent.OnChangeCurrentPlayPosition -> movePlay(intent.tackPosition)
            MediaPlayerControlIntent.OnChangeFinishedCurrentPositionMedia -> onFinishChangeTrackPosition()
            MediaPlayerControlIntent.OnChangePlayState -> changePlayState()
            MediaPlayerControlIntent.OnForwardBtnClick -> forward()
            MediaPlayerControlIntent.OnReplayBtnClick -> replay()
        }
    }

    private fun movePlay(positionTrack: Float) {
        isProcessingChangeProgress = true
        val totalDuration = player.currentMediaContent?.totalDurationMs ?: 0
        timeBeforeStartMoveTrack = (totalDuration * positionTrack).toLong()

        _timePosition.update {
            it.copy(
                trackPosition = positionTrack
            )
        }
    }

    private fun onFinishChangeTrackPosition() {
        viewModelScope.launch {
            player.moveTo(timeBeforeStartMoveTrack)
            isProcessingChangeProgress = false
        }
    }

    private fun changePlayState() {
        player.changePlayStatus()
    }

    private fun forward() {
        viewModelScope.launch {
            player.seekBy(30)
        }

    }

    private fun replay() {
        viewModelScope.launch {
            player.seekBy(-30)
        }
    }

    private fun maxDurationTimeFormat(millis: Long): DurationTimeFormat {
        val hours = millis / 1000 / 3600
        val minutes = (millis / 1000 % 3600) / 60
        val seconds = millis / 1000 % 60

        return when {
            hours > 0 -> DurationTimeFormat.HOUR
            minutes > 0 -> DurationTimeFormat.MINUTE
            else -> DurationTimeFormat.SECOND
        }
    }

    private fun formatMillisToDynamicTime(
        millis: Long,
        durationTimeFormat: DurationTimeFormat = DurationTimeFormat.HOUR
    ): String {
        val hours = millis / 1000 / 3600
        val minutes = (millis / 1000 % 3600) / 60
        val seconds = millis / 1000 % 60

        val formattedDuration = String.format("%d:%02d:%02d", hours, minutes, seconds)

        return when (durationTimeFormat) {
            DurationTimeFormat.HOUR -> formattedDuration
            DurationTimeFormat.MINUTE -> formattedDuration.substringAfter(":")
            DurationTimeFormat.SECOND -> formattedDuration.substringAfterLast(":")
        }
    }
}