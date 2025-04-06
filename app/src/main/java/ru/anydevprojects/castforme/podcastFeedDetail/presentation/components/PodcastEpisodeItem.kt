package ru.anydevprojects.castforme.podcastFeedDetail.presentation.components


import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastEpisodePreviewItem
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun PodcastEpisodeItem(
    episode: PodcastEpisodePreviewItem,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(text = episode.name)
        },
        leadingContent = {
            AsyncImage(
                modifier = Modifier.size(64.dp),
                model = episode.imageUrl,
                contentDescription = null
            )
        }
    )
}

@Preview
@Composable
private fun PodcastEpisodeItemPreview() {
    AppTheme {
        PodcastEpisodeItem(
            episode = PodcastEpisodePreviewItem()
        )
    }
}