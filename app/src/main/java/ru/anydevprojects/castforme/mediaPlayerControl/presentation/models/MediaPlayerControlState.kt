package ru.anydevprojects.castforme.mediaPlayerControl.presentation.models

data class MediaPlayerControlState(
    val podcastName: String = "",
    val episodeName: String = "",
    val imageUrl: String = "",
    val totalDuration: String = "",
    val isPlaying: Boolean = false,
)