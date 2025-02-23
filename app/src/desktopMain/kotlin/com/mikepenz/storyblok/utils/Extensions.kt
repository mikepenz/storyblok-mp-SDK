package com.mikepenz.storyblok.utils

import androidx.compose.ui.SystemTheme
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import com.mikepenz.storyblok.ui.model.UiTheme
import com.mikepenz.storyblok.ui.model.UiTheme.Dark
import com.mikepenz.storyblok.ui.model.UiTheme.System
import org.jetbrains.skiko.currentSystemTheme

/**
 * Converts Skiko system theme type to Compose [SystemTheme].
 */
internal fun org.jetbrains.skiko.SystemTheme.asComposeSystemTheme(): SystemTheme {
    return when (this) {
        org.jetbrains.skiko.SystemTheme.DARK -> SystemTheme.Dark
        org.jetbrains.skiko.SystemTheme.LIGHT -> SystemTheme.Light
        org.jetbrains.skiko.SystemTheme.UNKNOWN -> SystemTheme.Unknown
    }
}


fun UiTheme.isDark() = (if (this == System) fromSystemTheme(currentSystemTheme) else this) == Dark

fun UiTheme.fromSystemTheme(systemTheme: org.jetbrains.skiko.SystemTheme) =
    if (systemTheme == org.jetbrains.skiko.SystemTheme.LIGHT) Light else Dark