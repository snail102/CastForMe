package ru.anydevprojects.castforme.home.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.anydevprojects.castforme.home.presentation.components.EpisodeFromFavoritePodcast
import ru.anydevprojects.castforme.home.presentation.components.FavoritePodcastFeedsItem
import ru.anydevprojects.castforme.home.presentation.models.FavoriteItem
import ru.anydevprojects.castforme.home.presentation.models.HomeEvent
import ru.anydevprojects.castforme.home.presentation.models.HomeIntent
import ru.anydevprojects.castforme.home.presentation.models.HomeState
import ru.anydevprojects.castforme.ui.theme.AppTheme
import ru.anydevprojects.castforme.utils.ui.rememberFlowWithLifecycle

@Composable
fun HomeScreen(
    onAllFavoritePodcastFeedItemClick: () -> Unit,
    onFavoritePodcastFeedItemClick: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.onIntent(HomeIntent.SelectedImportFile(uri))
        }


    val event = rememberFlowWithLifecycle(viewModel.event)

    LaunchedEffect(event) {
        event.collect { event ->
            when (event) {
                HomeEvent.OpenFileChoose -> {
                    launcher.launch("text/xml")
                }
            }
        }
    }

    HomeContent(
        state = state,
        onImportBtnClick = {
            viewModel.onIntent(HomeIntent.OnImportBtnClick)
        },
        onAllFavoritePodcastFeedItemClick = onAllFavoritePodcastFeedItemClick,
        onFavoritePodcastFeedItemClick = { onFavoritePodcastFeedItemClick(it.id) },
    )
}

@Composable
private fun HomeContent(
    state: HomeState,
    onImportBtnClick: () -> Unit,
    onAllFavoritePodcastFeedItemClick: () -> Unit,
    onFavoritePodcastFeedItemClick: (FavoriteItem.FavoritePodcastFeedItem) -> Unit,
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues
        ) {
            item {
                Button(
                    onClick = {
                        onImportBtnClick()
                    }
                ) {
                    Text(text = "Import")
                }
            }

            item {
                FavoritePodcastFeedsItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    favoritePodcastState = state.favoritePodcastState,
                    onAllFavoriteItemClick = onAllFavoritePodcastFeedItemClick,
                    onPodcastFeedItemClick = onFavoritePodcastFeedItemClick
                )
            }

            items(
                items = state.episodes,
                key = {
                    it.hashCode()
                }
            ) {
                EpisodeFromFavoritePodcast(
                    modifier = Modifier.fillMaxWidth(),
                    episodeUi = it
                )
            }
        }

    }
}


@Preview
@Composable
private fun HomeContentPreview() {
    AppTheme {
        HomeContent(
            state = HomeState(),
            onImportBtnClick = {},
            onAllFavoritePodcastFeedItemClick = {},
            onFavoritePodcastFeedItemClick = {}
        )
    }
}