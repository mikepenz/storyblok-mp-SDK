package com.mikepenz.storyblok

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.koin.compose.KoinContext

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Storyblok Sample", canvasElementId = "storyblokCanvas") {
        KoinContext {
            App()
        }
    }
}
