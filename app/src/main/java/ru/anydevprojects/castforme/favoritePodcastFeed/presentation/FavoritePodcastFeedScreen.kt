package ru.anydevprojects.castforme.favoritePodcastFeed.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.components.FavoritePodcastFeedItem
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.models.FavoritePodcastFeedState
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.models.FavoritePodcastFeedUi
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun FavoritePodcastFeedScreen(
    viewModel: FavoritePodcastFeedViewModel = hiltViewModel(),
    onPodcastFeedItemClick: (FavoritePodcastFeedUi) -> Unit
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    FavoritePodcastFeedContent(
        state = state
    )
}

@Composable
private fun FavoritePodcastFeedContent(
    state: FavoritePodcastFeedState
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(
                items = state.podcasts,
                key = {
                    it.hashCode()
                }
            ) {
                FavoritePodcastFeedItem(
                    favoritePodcastFeedUi = it
                )
            }
        }
    }
}


@Preview
@Composable
private fun FavoritePodcastFeedContentPreview() {
    AppTheme {
        FavoritePodcastFeedContent(
            state = FavoritePodcastFeedState()
        )
    }
}