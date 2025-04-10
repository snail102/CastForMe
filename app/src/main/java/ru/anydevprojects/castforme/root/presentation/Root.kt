package ru.anydevprojects.castforme.root.presentation

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.FavoritePodcastFeedScreen
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.FavoritePodcastFeedScreenDestination
import ru.anydevprojects.castforme.home.presentation.HomeScreen
import ru.anydevprojects.castforme.home.presentation.HomeScreenDestination
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.MediaPlayerControlScreen
import ru.anydevprojects.castforme.mediaPlayerControl.presentation.components.MiniPlayer
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.PodcastEpisodeDetailScreen
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.PodcastEpisodeDetailScreenDestination
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.PodcastFeedDetailScreen
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.PodcastFeedDetailScreenDestination
import ru.anydevprojects.castforme.root.presentation.models.MainIntent
import ru.anydevprojects.castforme.ui.common.SheetCollapsed
import ru.anydevprojects.castforme.ui.common.bottomsheet.currentFraction
import kotlin.compareTo
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Root(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val bottomSheetScaffoldState = androidx.compose.material.rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    val trackPosition = viewModel.trackPosition.collectAsState()


    val navigationBars = WindowInsets.navigationBars.asPaddingValues()

    var radiusBottomSheet by remember {
        mutableStateOf(16.dp)
    }

    LaunchedEffect(bottomSheetScaffoldState.bottomSheetState.progress) {
        snapshotFlow { bottomSheetScaffoldState.bottomSheetState }.collect {
            Log.d("bottomSheet", "${bottomSheetScaffoldState.currentFraction}")
            kotlin.runCatching {
                if (bottomSheetScaffoldState.currentFraction > 0.99) {
                    radiusBottomSheet = 0.dp
                } else {
                    radiusBottomSheet = 16.dp
                }
            }
        }
    }


    androidx.compose.material.BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(
            topStart = radiusBottomSheet,
            topEnd = radiusBottomSheet
        ),
        sheetPeekHeight = if (state.isEnablePlayerControl) {
            100.dp
        } else {
            0.dp
        },
        sheetBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
        sheetContent = {
            if (state.isEnablePlayerControl) {
                PlayerSheetContent(
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    navigationBars = navigationBars,
                    isPlaying = state.isPlaying,
                    imageUrl = state.imageUrl,
                    episodeName = state.nameEpisode,
                    trackPosition = trackPosition,
                    onPlayStatusBtnClick = {
                        viewModel.onIntent(MainIntent.OnChangePlayState)
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = navController,
            startDestination = HomeScreenDestination
        ) {
            composable<HomeScreenDestination> {
                HomeScreen(
                    onAllFavoritePodcastFeedItemClick = {
                        navController.navigate(FavoritePodcastFeedScreenDestination)
                    },
                    onFavoritePodcastFeedItemClick = {
                        navController.navigate(
                            PodcastFeedDetailScreenDestination(
                                podcastFeedId = it
                            )
                        )
                    },
                    onPodcastEpisodeItemClick = {
                        navController.navigate(
                            PodcastEpisodeDetailScreenDestination(episodeId = it)
                        )
                    }
                )
            }
            composable<FavoritePodcastFeedScreenDestination> {
                FavoritePodcastFeedScreen(
                    onPodcastFeedItemClick = {
                        navController.navigate(
                            PodcastFeedDetailScreenDestination(
                                podcastFeedId = it.id
                            )
                        )
                    }
                )
            }
            composable<PodcastFeedDetailScreenDestination> {
                PodcastFeedDetailScreen(
                    onPodcastEpisodeItemClick = {
                        navController.navigate(
                            PodcastEpisodeDetailScreenDestination(
                                episodeId = it
                            )
                        )
                    }
                )
            }

            composable<PodcastEpisodeDetailScreenDestination> {
                PodcastEpisodeDetailScreen()
            }
        }
    }
}

@Composable
private fun PlayerSheetContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    navigationBars: PaddingValues,
    isPlaying: Boolean,
    imageUrl: String,
    episodeName: String,
    trackPosition: State<Float>,
    onPlayStatusBtnClick: () -> Unit
) {

    val currentFraction = remember(bottomSheetScaffoldState.currentFraction) {
        derivedStateOf { bottomSheetScaffoldState.currentFraction == 1f }
    }

    SheetContent(
        heightFraction = 1f
    ) {
        SheetExpanded(
            currentFraction = bottomSheetScaffoldState.currentFraction
        ) {
            MediaPlayerControlScreen()
        }
        SheetCollapsed(
            isCollapsed = bottomSheetScaffoldState.bottomSheetState.isCollapsed,
            currentFraction = bottomSheetScaffoldState.currentFraction,
            onSheetClick = {}
        ) {
            if (!currentFraction.value) {
                MiniPlayer(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp
                            )
                        ),
                    paddingValues = navigationBars,
                    isPlaying = isPlaying,
                    onClick = onPlayStatusBtnClick,
                    nameEpisode = episodeName,
                    timePosition = trackPosition,
                    coverUrl = imageUrl
                )
            }
        }
    }
}