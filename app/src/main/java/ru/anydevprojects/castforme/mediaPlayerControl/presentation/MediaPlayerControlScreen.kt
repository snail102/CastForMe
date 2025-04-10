package ru.anydevprojects.castforme.mediaPlayerControl.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.R
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.components.LineSlider
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.models.MediaPlayerControlIntent
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.models.TimePosition
import ru.anydevprojects.castforme.ui.common.IconControlBtn
import ru.anydevprojects.castforme.ui.theme.AppTheme

@SuppressLint("UnrememberedMutableState")
@Composable
fun MediaPlayerControlScreen(viewModel: MediaPlayerControlViewModel = hiltViewModel()) {

    val state = viewModel.stateFlow.collectAsState()

    val timePosition = viewModel.timePosition.collectAsState()


    PlayControlScreenContent(
        isPlaying = state.value.isPlaying,
        totalTime = state.value.totalDuration,
        onPlayStateChangeClick = {
            viewModel.onIntent(MediaPlayerControlIntent.OnChangePlayState)
        },
        podcastName = state.value.podcastName,
        episodeName = state.value.episodeName,
        timePosition = timePosition,
        coverUrl = state.value.imageUrl,
        onChangeCurrentPositionMedia = {
            viewModel.onIntent(MediaPlayerControlIntent.OnChangeCurrentPlayPosition(it))
        },
        onChangeFinishedCurrentPositionMedia = {
            viewModel.onIntent(MediaPlayerControlIntent.OnChangeFinishedCurrentPositionMedia)
        },
        onForwardBtnClick = {
            viewModel.onIntent(MediaPlayerControlIntent.OnForwardBtnClick)

        },
        onReplayBtnClick = {
            viewModel.onIntent(MediaPlayerControlIntent.OnReplayBtnClick)

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
    onPlayStateChangeClick: () -> Unit,
    onForwardBtnClick: () -> Unit,
    onReplayBtnClick: () -> Unit,
    onChangeCurrentPositionMedia: (Float) -> Unit,
    onChangeFinishedCurrentPositionMedia: () -> Unit
) {
    Scaffold { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .statusBarsPadding()
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = podcastName,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = coverUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .heightIn(max = 200.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
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

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(timePosition.value.currentTime)
                Text(totalTime)
            }

            Spacer(modifier = Modifier.height(64.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ReplayIconBtn(
                    onClick = onReplayBtnClick,
                    modifier = Modifier.size(48.dp)
                )
                PlayControlIconBtn(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    isPlaying = isPlaying,
                    onClick = onPlayStateChangeClick
                )

                ForwardIconBtn(
                    onClick = onForwardBtnClick,
                    modifier = Modifier.size(48.dp)
                )

            }



            Spacer(modifier = Modifier.height(32.dp))
        }

    }
}

@Composable
private fun ForwardIconBtn(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(0.75f),
            painter = painterResource(R.drawable.ic_forward_30),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun ReplayIconBtn(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(0.75f),
            painter = painterResource(R.drawable.ic_replay_30),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
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
        tint = MaterialTheme.colorScheme.onPrimary
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
            onPlayStateChangeClick = {},
            episodeName = "name",
            timePosition = mutableStateOf(
                TimePosition(
                    currentTime = "",
                    trackPosition = 0F
                )
            ),
            coverUrl = "",
            onChangeCurrentPositionMedia = {},
            onChangeFinishedCurrentPositionMedia = {}, podcastName = "122131",
            onForwardBtnClick = {},
            onReplayBtnClick = {}
        )
    }
}
