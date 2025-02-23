package com.mikepenz.storyblok.ui.stories

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mikepenz.storyblok.sdk.model.Story

@Composable
fun StoriesScreen(
    stories: List<Story>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = contentPadding
    ) {
        items(stories) {
            StoryItem(story = it)
        }
    }
}