package ru.anydevprojects.castforme.root.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.FavoritePodcastFeedScreen
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.FavoritePodcastFeedScreenDestination
import ru.anydevprojects.castforme.home.presentation.HomeScreen
import ru.anydevprojects.castforme.home.presentation.HomeScreenDestination
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.PodcastEpisodeDetailScreen
import ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.PodcastEpisodeDetailScreenDestination
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.PodcastFeedDetailScreen
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.PodcastFeedDetailScreenDestination

@Composable
fun Root() {
    val navController = rememberNavController()
    NavHost(
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
                            episodeId = it.id
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