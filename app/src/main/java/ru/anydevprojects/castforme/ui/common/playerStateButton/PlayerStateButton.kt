package ru.anydevprojects.castforme.ui.common.playerStateButton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import ru.anydevprojects.castforme.R
import ru.anydevprojects.castforme.ui.common.IconControlBtn
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun PlayerStateButton(
    playerUiState: PlayerUiState,
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    container: Color = MaterialTheme.colorScheme.primaryContainer,
    shape: Shape = CircleShape,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
    ) {
        if (playerUiState is PlayerUiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize(0.75f), color = tint)
        } else {
            IconControlBtn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = shape)
                    .background(container),
                isActivate = playerUiState is PlayerUiState.Playing,
                activateIconResId = R.drawable.ic_pause,
                deactivateIconResId = R.drawable.ic_play,
                onClick = onClick,
                tint = tint
            )
        }
    }
}


@Preview
@Composable
private fun PlayerStateButtonPreview() {
    AppTheme {
        PlayerStateButton(
            playerUiState = PlayerUiState.Loading,
            onClick = {}
        )
    }
}