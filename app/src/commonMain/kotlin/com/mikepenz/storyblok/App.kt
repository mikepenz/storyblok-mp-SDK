package com.mikepenz.storyblok

import Storyblok
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.markdown.Github
import com.mikepenz.markdown.OpenSourceInitiative
import com.mikepenz.storyblok.di.collectAsStateMultiplatform
import com.mikepenz.storyblok.ui.AppTheme
import com.mikepenz.storyblok.ui.stories.StoriesScreen
import com.mikepenz.storyblok.viewmodel.AppViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import storyblok_mp_sdk_root.app.generated.resources.Res

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun App(
    viewModel: AppViewModel = koinInject<AppViewModel>(),
    useDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    val uriHandler = LocalUriHandler.current
    var aboutLibs by remember { mutableStateOf(false) }
    val stories by viewModel.stories.collectAsStateMultiplatform()
    KoinContext {
        AppTheme(darkTheme = useDarkTheme) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("Storyblok Sdk Sample")
                        },
                        actions = {
                            IconButton(onClick = {
                                uriHandler.openUri("https://storyblok.com/")
                            }) {
                                Icon(
                                    imageVector = Storyblok,
                                    contentDescription = "Storyblok"
                                )
                            }
                            IconButton(onClick = { aboutLibs = !aboutLibs }) {
                                Icon(
                                    imageVector = OpenSourceInitiative,
                                    contentDescription = "Open Source"
                                )
                            }
                            IconButton(onClick = {
                                uriHandler.openUri("https://github.com/mikepenz/storyblok-mp-SDK")
                            }) {
                                Icon(
                                    imageVector = Github,
                                    contentDescription = "GitHub"
                                )
                            }
                        }
                    )
                }
            ) { contentPadding ->
                if (!aboutLibs) {
                    StoriesScreen(stories, contentPadding = contentPadding)
                } else {
                    var libs by remember { mutableStateOf<Libs?>(null) }
                    LaunchedEffect("aboutlibraries.json") {
                        libs = Libs.Builder()
                            .withJson(Res.readBytes("files/aboutlibraries.json").decodeToString())
                            .build()
                    }
                    LibrariesContainer(
                        libraries = libs,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = contentPadding
                    )
                }
            }
        }
    }
}

