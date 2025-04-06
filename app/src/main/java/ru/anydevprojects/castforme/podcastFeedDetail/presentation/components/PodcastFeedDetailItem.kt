package ru.anydevprojects.castforme.podcastFeedDetail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.anydevprojects.castforme.R
import ru.anydevprojects.castforme.podcastFeedDetail.presentation.models.PodcastFeedDetailUi
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun PodcastFeedDetailItem(
    podcastFeedDetailUi: PodcastFeedDetailUi,
    onFavoriteBtnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                AsyncImage(
                    model = podcastFeedDetailUi.imageUrl,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentDescription = null
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = podcastFeedDetailUi.name
                    )
                    IconButton(
                        onClick = onFavoriteBtnClick
                    ) {
                        val iconRes = if (podcastFeedDetailUi.isFavorite) {
                            R.drawable.ic_favorite_fill
                        } else {
                            R.drawable.ic_favorite
                        }
                        Icon(
                            painter = painterResource(iconRes),
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                }
            }


        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = podcastFeedDetailUi.description
            )
        }
    }
}

@Preview
@Composable
private fun PodcastFeedDetailItemPreview() {
    AppTheme {
        PodcastFeedDetailItem(
            podcastFeedDetailUi = PodcastFeedDetailUi(),
            onFavoriteBtnClick = {},
        )
    }
}