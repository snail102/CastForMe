package ru.anydevprojects.castforme.ui.common.playerStateButton

import ru.anydevprojects.castforme.core.player.domain.models.PlayState
import ru.anydevprojects.castforme.ui.common.playerStateButton.PlayerUiState.Loading
import ru.anydevprojects.castforme.ui.common.playerStateButton.PlayerUiState.Pause
import ru.anydevprojects.castforme.ui.common.playerStateButton.PlayerUiState.Playing

sealed interface PlayerUiState {
    data object Loading : PlayerUiState
    data object Playing : PlayerUiState
    data object Pause : PlayerUiState
}

fun PlayState.toUi(): PlayerUiState {
    return when (this) {
        PlayState.Init -> Pause
        PlayState.Loading -> Loading
        PlayState.Pause -> Pause
        PlayState.Playing -> Playing
    }
}