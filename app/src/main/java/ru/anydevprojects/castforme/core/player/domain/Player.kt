package ru.anydevprojects.castforme.core.player.domain

import kotlinx.coroutines.flow.StateFlow
import ru.anydevprojects.castforme.core.player.domain.models.MediaData
import ru.anydevprojects.castforme.core.player.domain.models.PlayMediaData
import ru.anydevprojects.castforme.core.player.domain.models.PlayState

interface Player {

    val currentMedia: StateFlow<MediaData>

    val progress: StateFlow<Float>

    val currentDuration: StateFlow<Long>

    val currentMediaContent: MediaData.Content?

    val playState: StateFlow<PlayState>

    suspend fun moveTo(timeMs: Long)

    suspend fun seekBy(offsetSeconds: Long)

    fun play(playMediaData: PlayMediaData)

    fun pause()

    fun changePlayStatus()

    fun reset()
}
