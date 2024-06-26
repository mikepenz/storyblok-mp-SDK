package com.mikepenz.common.repository

import co.touchlab.kermit.Logger
import com.mikepenz.storyblok.sdk.Storyblok
import com.mikepenz.storyblok.sdk.model.Story


class StoryblokRepository(token: String) {
    private val storyblok = Storyblok(token)

    suspend fun fetchStories(): List<Story> {
        Logger.d("Repo")
        return storyblok.fetchStories().stories
    }
}

