package ru.anydevprojects.castforme.podcastFeedDetail.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.R
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastEpisodePreviewItem
import ru.anydevprojects.castforme.ui.theme.AppTheme
import ru.anydevprojects.castforme.utils.ui.conditional

@Composable
fun PodcastEpisodeItem(
    episode: PodcastEpisodePreviewItem,
    isTopRounded: Boolean,
    isBottomRounded: Boolean,
    onItemClick: (PodcastEpisodePreviewItem) -> Unit,
    onPlayStateBtnClick: (PodcastEpisodePreviewItem) -> Unit,
    modifier: Modifier = Modifier
) {

    val iconPlayState = if (episode.isPlaying) {
        R.drawable.ic_pause
    } else {
        R.drawable.ic_play
    }

    Row(
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
            )
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .clickable(
                onClick = {
                    onItemClick(episode)
                }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = episode.imageUrl,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            minLines = 3,
            text = episode.name
        )
        IconButton(
            modifier = Modifier.size(36.dp),
            onClick = {
                onPlayStateBtnClick(episode)
            }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(iconPlayState),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun PodcastEpisodeItemPreview() {
    AppTheme {
        PodcastEpisodeItem(
            episode = PodcastEpisodePreviewItem(),
            isTopRounded = true,
            isBottomRounded = true,
            onItemClick = {},
            onPlayStateBtnClick = {},
        )
    }
}