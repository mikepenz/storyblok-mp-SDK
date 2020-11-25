package com.mikepenz.storyblok.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikepenz.storyblok.app.BuildConfig
import com.mikepenz.storyblok.sdk.Storyblok
import com.mikepenz.storyblok.sdk.model.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SampleViewModel : ViewModel() {
    private val client = Storyblok(BuildConfig.STORYBLOK_TOKEN)

    val stories = MutableLiveData<List<Story>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            stories.postValue(client.fetchStories())
        }
    }
}