package com.mikepenz.storyblok.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mikepenz.storyblok.ui.icons.Moon
import com.mikepenz.storyblok.ui.icons.Sun
import com.mikepenz.storyblok.ui.icons.SunMoon
import com.mikepenz.storyblok.ui.model.UiTheme
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.Tooltip
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.defaultTitleBarStyle
import org.jetbrains.jewel.window.newFullscreenControls

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DecoratedWindowScope.TitleBarView(manualTheme: UiTheme, toggleTheme: (UiTheme) -> Unit) {
    TitleBar(Modifier.newFullscreenControls(), gradientStartColor = Color(0xFF1B4897)) {
        Text(title, color = JewelTheme.defaultTitleBarStyle.colors.content)

        Row(Modifier.align(Alignment.End)) {
            Tooltip({
                when (manualTheme) {
                    UiTheme.Light -> Text("Switch to light theme with light header")
                    UiTheme.Dark -> Text("Switch to light theme")
                    UiTheme.System -> Text("Switch to system theme")
                }
            }) {
                IconButton({
                    toggleTheme.invoke(
                        when (manualTheme) {
                            UiTheme.Light -> UiTheme.Dark
                            UiTheme.Dark -> UiTheme.System
                            UiTheme.System -> UiTheme.Light
                        }
                    )
                }, Modifier.size(40.dp).padding(5.dp)) {
                    when (manualTheme) {
                        UiTheme.Light -> Icon(
                            Sun,
                            "Theme",
                            tint = JewelTheme.defaultTitleBarStyle.colors.content
                        )

                        UiTheme.Dark -> Icon(
                            Moon,
                            "Theme",
                            tint = JewelTheme.defaultTitleBarStyle.colors.content
                        )

                        UiTheme.System -> Icon(
                            SunMoon,
                            "Theme",
                            tint = JewelTheme.defaultTitleBarStyle.colors.content
                        )
                    }
                }
            }
        }
    }
}