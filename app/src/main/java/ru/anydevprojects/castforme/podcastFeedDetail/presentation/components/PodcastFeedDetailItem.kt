package ru.anydevprojects.castforme.podcastFeedDetail.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailUi
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun PodcastFeedDetailItem(
    podcastFeedDetailUi: PodcastFeedDetailUi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column {
            Text(text = podcastFeedDetailUi.name)
            AsyncImage(
                model = podcastFeedDetailUi.imageUrl,
                modifier = Modifier.size(128.dp),
                contentDescription = null
            )
            Text(text = podcastFeedDetailUi.description)
        }
    }
}

@Preview
@Composable
private fun PodcastFeedDetailItemPreview() {
    AppTheme {
        PodcastFeedDetailItem(
            podcastFeedDetailUi = PodcastFeedDetailUi(),
        )
    }
}