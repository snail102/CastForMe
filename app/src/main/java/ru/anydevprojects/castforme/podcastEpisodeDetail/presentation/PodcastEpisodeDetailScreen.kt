package ru.anydevprojects.castforme.podcastEpisodeDetail.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import ru.anydevprojects.castforme.R
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.components.DownloadProcessButton
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.models.PodcastEpisodeDetailIntent.OnPlayStateChange
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.models.PodcastEpisodeDetailState
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailIntent
import ru.anydevprojects.castforme.ui.common.IconControlBtn
import ru.anydevprojects.castforme.ui.common.playerStateButton.PlayerUiState
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun PodcastEpisodeDetailScreen(
    viewModel: PodcastEpisodeDetailViewModel = hiltViewModel()
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    PodcastEpisodeDetailContent(
        state = state,
        onPlayStateBtnClick = {
            viewModel.onIntent(OnPlayStateChange)
        }
    )
}

@Composable
private fun PodcastEpisodeDetailContent(
    state: PodcastEpisodeDetailState,
    onPlayStateBtnClick: () -> Unit
) {

    val richTextState = rememberRichTextState()

    LaunchedEffect(state.description) {
        richTextState.setHtml(state.description)
    }


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    AsyncImage(
                        model = state.imageUrl,
                        modifier = Modifier
                            .size(128.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        modifier = Modifier,
                        text = state.title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DownloadProcessButton(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        downloadEpisodeState = state.downloadEpisodeState,
                        onClick = {}
                    )

                    VerticalDivider(modifier = Modifier.fillMaxHeight())

                    //TODO вынести в отдельную функцию
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable(
                                onClick = onPlayStateBtnClick
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (state.playerUiState is PlayerUiState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            val iconPlayState = if (state.playerUiState is PlayerUiState.Playing) {
                                R.drawable.ic_pause
                            } else {
                                R.drawable.ic_play
                            }
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(iconPlayState),
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                RichText(
                    state = richTextState,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PodcastEpisodeDetailContentPreview() {
    AppTheme {
        PodcastEpisodeDetailContent(
            state = PodcastEpisodeDetailState(
                isLoading = false,
                title = "hsabdhaudabsdjasbdiasbdasojd",
                description = "sadknadnkasd",
                imageUrl = ""
            ),
            onPlayStateBtnClick = {}
        )
    }
}

