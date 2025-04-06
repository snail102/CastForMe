package ru.anydevprojects.castforme.podcastFeedDetail.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.anydevprojects.castforme.podcastEpisode.domain.models.PodcastEpisode
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.components.PodcastFeedDetailItem
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailState
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun PodcastFeedDetailScreen(
    viewModel: PodcastFeedDetailViewModel = hiltViewModel(),
    onPodcastEpisodeItemClick: (PodcastEpisode) -> Unit
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    PodcastFeedDetailContent(
        state = state
    )
}

@Composable
private fun PodcastFeedDetailContent(
    state: PodcastFeedDetailState
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                PodcastFeedDetailItem(
                    podcastFeedDetailUi = state.podcastFeedDetailUi
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
            state = PodcastFeedDetailState()
        )
    }
}
