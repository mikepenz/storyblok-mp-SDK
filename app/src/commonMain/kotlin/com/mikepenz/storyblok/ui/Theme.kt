package com.mikepenz.storyblok.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

internal val YaleBlue = Color(0xFF274793)
private val GraphiteGrey = Color(0xFF1F1F1F)
private val CloudGrey = Color(0xFFDFE3E8)

private val DarkColorScheme = darkColorScheme(
    primary = YaleBlue,
    onPrimary = Color.White,
    background = GraphiteGrey,
    surface = GraphiteGrey
)

private val LightColorScheme = lightColorScheme(
    primary = YaleBlue,
    onPrimary = Color.White,
    secondary = YaleBlue,
    background = CloudGrey,
    surface = CloudGrey
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
