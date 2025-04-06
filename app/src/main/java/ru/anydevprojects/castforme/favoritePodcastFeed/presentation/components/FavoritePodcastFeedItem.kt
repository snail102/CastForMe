package ru.anydevprojects.castforme.favoritePodcastFeed.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.favoritePodcastFeed.presentation.models.FavoritePodcastFeedUi
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun FavoritePodcastFeedItem(
    favoritePodcastFeedUi: FavoritePodcastFeedUi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        ListItem(
            headlineContent = {
                Text(text = favoritePodcastFeedUi.title)

            },
            leadingContent = {
                AsyncImage(
                    model = favoritePodcastFeedUi.imageUrl,
                    modifier = Modifier.size(100.dp),
                    contentDescription = null
                )
            }
        )
    }
}

@Preview
@Composable
private fun FavoritePodcastFeedItemPreview() {
    AppTheme {
        FavoritePodcastFeedItem(
            favoritePodcastFeedUi = FavoritePodcastFeedUi(
                id = 1,
                title = "12123",
                imageUrl = ""
            )
        )
    }
}