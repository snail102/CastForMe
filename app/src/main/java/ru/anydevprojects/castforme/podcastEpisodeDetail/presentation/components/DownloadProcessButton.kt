package ru.anydevprojects.castforme.podcastEpisodeDetail.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.anydevprojects.castforme.R
import ru.anydevprojects.castforme.podcastEpisodeDetail.domain.models.DownloadEpisodeState
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun DownloadProcessButton(
    downloadEpisodeState: DownloadEpisodeState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clickable(
            onClick = onClick
        ),
        contentAlignment = Alignment.Center
    ) {
        when (downloadEpisodeState) {
            DownloadEpisodeState.Downloaded -> {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.ic_save),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            DownloadEpisodeState.NotDownloaded -> {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.ic_download),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            is DownloadEpisodeState.ProcessDownload -> {
                CircularProgressIndicator(modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Preview
@Composable
private fun DownloadProcessButtonPreview() {
    AppTheme {
        DownloadProcessButton(
            onClick = {},
            downloadEpisodeState = DownloadEpisodeState.NotDownloaded,
        )
    }
}
