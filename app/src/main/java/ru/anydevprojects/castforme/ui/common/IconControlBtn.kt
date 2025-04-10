package ru.anydevprojects.castforme.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.anydevprojects.castforme.R
import ru.anydevprojects.castforme.ui.theme.AppTheme

@Composable
fun IconControlBtn(
    isActivate: Boolean,
    @DrawableRes activateIconResId: Int,
    @DrawableRes deactivateIconResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = Color.White
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(0.75f),
            painter = painterResource(if (isActivate) activateIconResId else deactivateIconResId),
            contentDescription = null,
            tint = tint
        )
    }
}

@Preview
@Composable
private fun IconControlBtnPreview() {
    AppTheme {
        IconControlBtn(
            modifier = Modifier.size(32.dp),
            isActivate = true,
            activateIconResId = R.drawable.ic_play,
            deactivateIconResId = R.drawable.ic_pause,
            onClick = {}
        )
    }
}