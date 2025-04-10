package ru.anydevprojects.castforme.core.player.domain.models

data class PlayMediaData(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val audioUrl: String,
    val duration: Long
)
