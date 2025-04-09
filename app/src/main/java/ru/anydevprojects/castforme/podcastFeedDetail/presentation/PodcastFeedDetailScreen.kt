package ru.anydevprojects.castforme.podcastFeedDetail.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.anydevprojects.castforme.podcastEpisode.domain.models.PodcastEpisode
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.components.PodcastEpisodeItem
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.components.PodcastFeedDetailItem
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastEpisodePreviewItem
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailIntent
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailState
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun PodcastFeedDetailScreen(
    viewModel: PodcastFeedDetailViewModel = hiltViewModel(),
    onPodcastEpisodeItemClick: (Long) -> Unit,
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    PodcastFeedDetailContent(
        state = state,
        onEpisodePodcastItemClick = {
            onPodcastEpisodeItemClick(it.id)
        },
        onPlayStateBtnClick = {
            viewModel.onIntent(PodcastFeedDetailIntent.OnPlayStateBtnClick(it))
        }
    )
}

@Composable
private fun PodcastFeedDetailContent(
    state: PodcastFeedDetailState,
    onEpisodePodcastItemClick: (PodcastEpisodePreviewItem) -> Unit,
    onPlayStateBtnClick: (PodcastEpisodePreviewItem) -> Unit
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            )
        ) {
            item {
                PodcastFeedDetailItem(
                    podcastFeedDetailUi = state.podcastFeedDetailUi,
                    onFavoriteBtnClick = {

                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            itemsIndexed(
                items = state.episodes,
                key = { _, item ->
                    item.hashCode()
                }
            ) { index, item ->
                PodcastEpisodeItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    isTopRounded = index == 0,
                    isBottomRounded = index == state.episodes.lastIndex,
                    episode = item,
                    onItemClick = onEpisodePodcastItemClick,
                    onPlayStateBtnClick = onPlayStateBtnClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun PodcastFeedDetailContentPreview() {
    AppTheme {
        PodcastFeedDetailContent(
            state = PodcastFeedDetailState(),
            onEpisodePodcastItemClick = {},
            onPlayStateBtnClick = {}
        )
    }
}
