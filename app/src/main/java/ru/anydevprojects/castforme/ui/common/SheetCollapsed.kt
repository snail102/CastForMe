package ru.anydevprojects.castforme.ui.common

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.pow

@Composable
fun SheetCollapsed(
    isCollapsed: Boolean,
    currentFraction: Float,
    onSheetClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(alpha = getAlpha(currentFraction)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

private fun getAlpha(currentFraction: Float): Float {
    return (1f - currentFraction).pow(2.5f)
    val result = when {
        currentFraction <= 0.3f -> 1f
        currentFraction >= 0.7f -> 0f
        else -> 1f - (currentFraction - 0.3f) / 0.4f
    }
    Log.d("getAlpha", result.toString())
    return result
}
