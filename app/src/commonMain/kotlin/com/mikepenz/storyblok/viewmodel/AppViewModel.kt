package com.mikepenz.storyblok.viewmodel

import com.mikepenz.storyblok.BuildKonfig.STORYBLOK_TOKEN
import com.mikepenz.storyblok.sdk.Storyblok
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModel : ViewModel(), KoinComponent {
    private val client = Storyblok(STORYBLOK_TOKEN)

    val stories = flow {
        emit(client.fetchStories().stories)
    }.stateIn(viewModelScope.coroutineScope, SharingStarted.Lazily, emptyList())
}