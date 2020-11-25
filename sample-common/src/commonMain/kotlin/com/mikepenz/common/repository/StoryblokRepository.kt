package com.mikepenz.common.repository

import co.touchlab.kermit.Logger
import com.mikepenz.storyblok.sdk.Storyblok
import com.mikepenz.storyblok.sdk.model.Story


class StoryblokRepository(token: String) {
    private val logger: Logger = getLogger()

    private val storyblok = Storyblok(token)

    suspend fun fetchStories(): List<Story> {
        logger.d("fetchStories", "Repo")
        return storyblok.fetchStories()
    }
}

