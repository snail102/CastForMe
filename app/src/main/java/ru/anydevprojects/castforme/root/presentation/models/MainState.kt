package ru.anydevprojects.castforme.root.presentation.models

data class MainState(
    val isEnablePlayerControl: Boolean = false,
    val isPlaying: Boolean = false,
    val nameEpisode: String = "",
    val imageUrl: String = ""
)