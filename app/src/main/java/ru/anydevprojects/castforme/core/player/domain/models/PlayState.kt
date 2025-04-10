package ru.anydevprojects.castforme.core.player.domain.models

sealed interface PlayState {

    data object Init : PlayState

    data object Loading : PlayState

    data object Playing : PlayState

    data object Pause : PlayState
}
