package ru.anydevprojects.castforme.home.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.home.presentation.models.EpisodeUi
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun EpisodeFromFavoritePodcast(
    episodeUi: EpisodeUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = episodeUi.name
                )
            },
            leadingContent = {
                AsyncImage(
                    modifier = Modifier.size(100.dp),
                    model = episodeUi.imageUrl,
                    contentDescription = null
                )
            }
        )
    }
}

@Preview
@Composable
private fun EpisodeFromFavoritePodcastPreview() {
    AppTheme {
        EpisodeFromFavoritePodcast(
            episodeUi = EpisodeUi(
                id = 0L,
                imageUrl = "",
                name = "12345"
            ),
            onClick = {},
        )
    }
}