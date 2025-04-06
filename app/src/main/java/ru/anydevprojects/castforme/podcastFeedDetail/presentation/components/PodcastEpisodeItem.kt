package ru.anydevprojects.castforme.podcastFeedDetail.presentation.components


import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastEpisodePreviewItem
import ru.anydevprojects.castforme.ui.theme.AppTheme
import ru.anydevprojects.castforme.utils.ui.conditional

@Composable
fun PodcastEpisodeItem(
    episode: PodcastEpisodePreviewItem,
    isTopRounded: Boolean,
    isBottomRounded: Boolean,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .conditional(
                condition = isTopRounded,
                ifTrue = {
                    clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                }
            )
            .conditional(
                condition = isBottomRounded,
                ifTrue = {
                    clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))

                }
            ),
        headlineContent = {
            Text(text = episode.name)
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
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
            episode = PodcastEpisodePreviewItem(),
            isTopRounded = true,
            isBottomRounded = true,
        )
    }
}