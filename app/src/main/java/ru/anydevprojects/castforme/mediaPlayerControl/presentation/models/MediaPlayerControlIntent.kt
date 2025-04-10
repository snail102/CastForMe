package ru.anydevprojects.castforme.mediaPlayerControl.presentation.models

sealed interface MediaPlayerControlIntent {
    data object OnChangePlayState : MediaPlayerControlIntent
    data class OnChangeCurrentPlayPosition(
        val tackPosition: Float
    ) : MediaPlayerControlIntent

    data object OnChangeFinishedCurrentPositionMedia : MediaPlayerControlIntent

    data object OnForwardBtnClick : MediaPlayerControlIntent
    data object OnReplayBtnClick : MediaPlayerControlIntent


}