package ru.anydevprojects.castforme.mediaPlayerControl.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.R
import ru.anydevprojects.castforme.ui.common.IconControlBtn
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun MiniPlayer(
    nameEpisode: String,
    timePosition: Float,
    isPlaying: Boolean,
    coverUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {

    val animatedProgress = animateFloatAsState(
        targetValue = timePosition,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy
        ),
        label = ""
    ).value

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surfaceDim)
            .padding(paddingValues)
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            progress = { animatedProgress },
            color = MaterialTheme.colorScheme.inverseSurface,
            trackColor = MaterialTheme.colorScheme.outlineVariant
        )

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = coverUrl,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = nameEpisode,
                style = MaterialTheme.typography.labelMedium.copy(),
                color = MaterialTheme.colorScheme.inverseSurface,
                maxLines = 2,
                minLines = 2
            )

            Spacer(modifier = Modifier.width(8.dp))

            PlayControlIconBtn(
                isPlaying = isPlaying,
                tint = MaterialTheme.colorScheme.primary,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun PlayControlIconBtn(
    isPlaying: Boolean,
    tint: Color,
    onClick: () -> Unit
    ) {
    IconControlBtn(
        modifier = Modifier.size(32.dp),
        isActivate = isPlaying,
        activateIconResId = R.drawable.ic_pause,
        deactivateIconResId = R.drawable.ic_play,
        onClick = onClick,
        tint = tint
    )
}


@Preview
@Composable
private fun MiniPlayerPreview() {
    AppTheme {
        MiniPlayer(
            nameEpisode = "1234",
            timePosition = 0.4f,
            isPlaying = true,
            coverUrl = "",
            onClick = {

            }
        )
    }
}