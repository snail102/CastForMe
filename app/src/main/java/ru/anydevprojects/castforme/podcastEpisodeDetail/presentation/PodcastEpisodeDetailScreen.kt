package ru.anydevprojects.castforme.podcastEpisodeDetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.models.PodcastEpisodeDetailState
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun PodcastEpisodeDetailScreen(
    viewModel: PodcastEpisodeDetailViewModel = hiltViewModel()
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    PodcastEpisodeDetailContent(
        state = state
    )
}

@Composable
private fun PodcastEpisodeDetailContent(
    state: PodcastEpisodeDetailState
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = state.imageUrl,
                modifier = Modifier.size(128.dp),
                contentDescription = null
            )
            Text(
                text = state.title
            )
            Text(
                text = state.description
            )
        }
    }
}

@Preview
@Composable
private fun PodcastEpisodeDetailContentPreview() {
    AppTheme {
        PodcastEpisodeDetailContent(
            state = PodcastEpisodeDetailState()
        )
    }
}

