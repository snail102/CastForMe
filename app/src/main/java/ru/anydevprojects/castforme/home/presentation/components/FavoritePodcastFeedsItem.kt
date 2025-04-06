package ru.anydevprojects.castforme.home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.CarouselState
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.R
import ru.anydevprojects.castforme.home.presentation.models.FavoriteItem
import ru.anydevprojects.castforme.home.presentation.models.FavoriteItem.FavoritePodcastFeedItem
import ru.anydevprojects.castforme.home.presentation.models.FavoritePodcastState
import ru.anydevprojects.castforme.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePodcastFeedsItem(
    favoritePodcastState: FavoritePodcastState,
    onAllFavoriteItemClick: () -> Unit,
    onPodcastFeedItemClick: (FavoritePodcastFeedItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberCarouselState { favoritePodcastState.favoritePodcastFeeds.size }


    HorizontalUncontainedCarousel(
        modifier = modifier.wrapContentSize(),
        state = state,
        itemWidth = 88.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) { index ->
        val favoriteItem = favoritePodcastState.favoritePodcastFeeds[index]
        when (favoriteItem) {
            FavoriteItem.AllFavoritePodcastFeeds -> {
                AllFavoriteItem(
                    modifier = Modifier
                        .size(100.dp)
                        .maskClip(MaterialTheme.shapes.extraLarge)
                        .maskBorder(
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            shape = MaterialTheme.shapes.extraLarge
                        ),
                    onClick = onAllFavoriteItemClick
                )
            }

            is FavoritePodcastFeedItem -> {
                FavoritePodcastItem(
                    modifier = Modifier
                        .size(100.dp)
                        .maskClip(MaterialTheme.shapes.extraLarge)
                        .maskBorder(
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            shape = MaterialTheme.shapes.extraLarge
                        ),
                    favoritePodcastFeedItem = favoriteItem,
                    onClick = {
                        onPodcastFeedItemClick(favoriteItem)
                    }
                )
            }
        }

    }

}

@Composable
private fun FavoritePodcastItem(
    favoritePodcastFeedItem: FavoritePodcastFeedItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier.clickable(
            onClick = onClick
        ),
        model = favoritePodcastFeedItem.imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

}

@Composable
private fun AllFavoriteItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .clickable(
                onClick = onClick
            )
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            painter = painterResource(R.drawable.ic_all),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
    }
}


@Preview
@Composable
private fun FavoritePodcastItemPreview() {
    AppTheme {
        FavoritePodcastItem(
            favoritePodcastFeedItem = FavoritePodcastFeedItem(
                id = 0L,
                imageUrl = ""
            ),
            onClick = {},
        )
    }
}


@Preview
@Composable
private fun FavoritePodcastFeedsItemPreview() {
    AppTheme {
        FavoritePodcastFeedsItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            favoritePodcastState = FavoritePodcastState(
                favoritePodcastFeeds = listOf(
                    FavoritePodcastFeedItem(
                        id = 1,
                        imageUrl = "12"
                    ),
                    FavoritePodcastFeedItem(
                        id = 2,
                        imageUrl = "33"
                    )
                )
            ),
            onAllFavoriteItemClick = {},
            onPodcastFeedItemClick = {}
        )
    }
}