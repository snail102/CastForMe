package ru.anydevprojects.castforme.mediaPlayerControl.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.R
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.components.LineSlider
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.models.TimePosition
import ru.anydevprojects.castforme.ui.common.IconControlBtn
import ru.anydevprojects.castforme.ui.theme.AppTheme

@SuppressLint("UnrememberedMutableState")
@Composable
fun MediaPlayerControlScreen(viewModel: MediaPlayerControlViewModel = hiltViewModel()) {

    //val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    PlayControlScreenContent(
        isPlaying = true,
        totalTime = "12",
        onClick = {
            //viewModel.onIntent(PlayControlIntent.OnChangePlayState)
        },
        podcastName = "state.podcastName",
        episodeName = "state.episodeName",
        timePosition = mutableStateOf(TimePosition()),
        coverUrl = "state.imageUrl",
        onChangeCurrentPositionMedia = {
            //viewModel.onIntent(PlayControlIntent.OnChangeCurrentPlayPosition(it))
        },
        onChangeFinishedCurrentPositionMedia = {
            //viewModel.onIntent(PlayControlIntent.OnChangeFinishedCurrentPositionMedia)
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayControlScreenContent(
    podcastName: String,
    episodeName: String,
    totalTime: String,
    timePosition: State<TimePosition>,
    isPlaying: Boolean,
    coverUrl: String,
    onClick: () -> Unit,
    onChangeCurrentPositionMedia: (Float) -> Unit,
    onChangeFinishedCurrentPositionMedia: () -> Unit
) {
    Scaffold { _ ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceDim)
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp, top = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = podcastName,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                model = coverUrl,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = episodeName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            LineSlider(
                modifier = Modifier.fillMaxWidth(),
                value = timePosition.value.trackPosition,
                onValueChange = onChangeCurrentPositionMedia,
                onValueChangeFinished = onChangeFinishedCurrentPositionMedia
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(timePosition.value.currentTime)
                Text(totalTime)
            }

            Spacer(modifier = Modifier.height(64.dp))

            PlayControlIconBtn(
                modifier = Modifier.size(80.dp),
                isPlaying = isPlaying,
                onClick = onClick
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun PlayControlIconBtn(
    isPlaying: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconControlBtn(
        modifier = modifier,
        isActivate = isPlaying,
        activateIconResId = R.drawable.ic_pause,
        deactivateIconResId = R.drawable.ic_play,
        onClick = onClick,
        tint = MaterialTheme.colorScheme.primary
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun PlayControlScreenContentPreview() {
    AppTheme {
        PlayControlScreenContent(
            isPlaying = false,
            totalTime = "",
            onClick = {},
            episodeName = "name",
            timePosition = mutableStateOf(
                TimePosition(
                    currentTime = "",
                    trackPosition = 0F
                )
            ),
            coverUrl = "",
            onChangeCurrentPositionMedia = {},
            onChangeFinishedCurrentPositionMedia = {}, podcastName = "122131"

        )
    }
}
