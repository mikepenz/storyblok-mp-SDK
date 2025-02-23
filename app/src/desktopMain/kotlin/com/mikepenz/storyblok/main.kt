package com.mikepenz.storyblok

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.SystemTheme
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mikepenz.storyblok.di.initKoin
import com.mikepenz.storyblok.ui.TitleBarView
import com.mikepenz.storyblok.ui.model.UiTheme
import com.mikepenz.storyblok.utils.asComposeSystemTheme
import com.mikepenz.storyblok.utils.isDark
import com.mikepenz.storyblok.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.intui.standalone.theme.darkThemeDefinition
import org.jetbrains.jewel.intui.standalone.theme.lightThemeDefinition
import org.jetbrains.jewel.intui.window.decoratedWindow
import org.jetbrains.jewel.intui.window.styling.dark
import org.jetbrains.jewel.intui.window.styling.light
import org.jetbrains.jewel.ui.ComponentStyling
import org.jetbrains.jewel.window.DecoratedWindow
import org.jetbrains.jewel.window.styling.TitleBarStyle
import org.jetbrains.skiko.currentSystemTheme
import org.koin.compose.koinInject
import org.koin.dsl.module
import java.awt.Dimension

private val koin = initKoin {
    modules(
        module {

        }
    )
}.koin

fun main() = application {
    val viewModel: AppViewModel = koinInject()
    val windowState = rememberWindowState()
    var theme by remember { mutableStateOf(UiTheme.System) }

    val actualTheme by produceState(initialValue = theme, key1 = theme) {
        launch(Dispatchers.IO) {
            while (true) {
                value = if (theme == UiTheme.System) {
                    if (currentSystemTheme.asComposeSystemTheme() == SystemTheme.Dark) UiTheme.Dark else UiTheme.Light
                } else theme
                delay(1_000)
            }
        }
    }

    val themeDefinition = if (actualTheme.isDark()) JewelTheme.darkThemeDefinition() else JewelTheme.lightThemeDefinition()
    val titleBarStyle = if (actualTheme.isDark()) TitleBarStyle.dark() else TitleBarStyle.light()

    IntUiTheme(
        theme = themeDefinition,
        styling = ComponentStyling.decoratedWindow(titleBarStyle = titleBarStyle),
    ) {
        DecoratedWindow(
            onCloseRequest = { exitApplication() },
            title = "Storyblok Sample",
            state = windowState,
            onKeyEvent = {
                if (it.key == Key.Escape && it.type == KeyEventType.KeyDown) {
                    // TODO
                    true
                } else {
                    false
                }
            }
        ) {
            with(LocalDensity.current) {
                with(DpSize(400.dp, 250.dp).toSize()) {
                    window.minimumSize = Dimension(width.toInt(), height.toInt())
                }
            }

            TitleBarView(theme) { theme = it }
            App(viewModel, actualTheme.isDark())
        }
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App(koin.get())
}