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
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import ru.anydevprojects.castforme.ui.common.SheetCollapsed
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Root() {
    val navController = rememberNavController()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded
        )
    )


    val navigationBars = WindowInsets.navigationBars.asPaddingValues()

    var radiusBottomSheet by remember {
        mutableStateOf(16.dp)
    }

    CustomBottomSheetScaffold(
//        scaffoldState = bottomSheetScaffoldState,
//        sheetDragHandle = null,
//        sheetShape = RoundedCornerShape(
//            topStart = radiusBottomSheet,
//            topEnd = radiusBottomSheet
//        ),
//        sheetPeekHeight = if (true) {
//            100.dp
//        } else {
//            0.dp
//        },
        sheetContent = {
            if (true) {
                SheetContent(
                    heightFraction = 1f
                ) {
                    SheetExpanded(
                        currentFraction = 0.5f
                    ) {
                        MediaPlayerControlScreen()
                    }
                    SheetCollapsed(
                        isCollapsed = bottomSheetScaffoldState.bottomSheetState.isVisible,
                        currentFraction = 0.5f,
                        onSheetClick = {}
                    ) {

                        MiniPlayer(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp
                                    )
                                )
                                .height(100.dp),
                            paddingValues = navigationBars,
                            isPlaying = true,
                            onClick = {
                                //viewModel.onChangePlayState()
                            },
                            nameEpisode = "playerControlState.title",
                            timePosition = 0.5f,
                            coverUrl = "playerControlState.imageUrl"
                        )
                    }
                }
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
fun CustomBottomSheetScaffold(
    modifier: Modifier = Modifier,
    sheetHeight: Dp = 300.dp,
    peekHeight: Dp = 64.dp,
    sheetBackground: Color = Color.LightGray,
    onProgressChanged: (Float) -> Unit = {},
    topBar: @Composable (() -> Unit)? = null,
    sheetContent: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    val density = LocalDensity.current
    val peekHeightPx = with(density) { peekHeight.toPx() }

    val coroutineScope = rememberCoroutineScope()

    val sheetHeightPx = with(LocalDensity.current) { sheetHeight.toPx() }

    val offsetY = remember { Animatable(sheetHeightPx) }
    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(progress) {
        println(progress)
    }

    // Gesture + snapping logic
    val dragGesture = Modifier.pointerInput(Unit) {
        detectVerticalDragGestures(
            onVerticalDrag = { change, dragAmount ->
                val newOffset = (offsetY.value + dragAmount).coerceIn(0f, sheetHeightPx)
                coroutineScope.launch { offsetY.snapTo(newOffset) }

                // Расчёт прогресса
                val totalDistance = sheetHeightPx - peekHeightPx
                val currentDistance = sheetHeightPx - newOffset
                progress = (currentDistance / totalDistance).coerceIn(0f, 1f)
            },
            onDragEnd = {
                coroutineScope.launch {
                    val shouldExpand = offsetY.value < (sheetHeightPx + peekHeightPx) / 2
                    val target = if (shouldExpand) 0f else sheetHeightPx
                    offsetY.animateTo(target, tween(300))
                    progress = if (shouldExpand) 1f else 0f
                }
            }
        )
    }

    Box(modifier.fillMaxSize()) {
        Column(modifier.fillMaxSize()) {
            topBar?.invoke()
            content(PaddingValues())
        }

        // Sheet content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(sheetHeight)
                .offset { IntOffset(x = 0, y = offsetY.value.roundToInt()) }
                .background(sheetBackground, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .then(dragGesture)
        ) {
            sheetContent()
        }
    }
}