package com.mikepenz.storyblok.ui.stories

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mikepenz.storyblok.sdk.model.Story

@Composable
fun StoryItem(story: Story) {
    ListItem(
        headlineContent = { Text(story.name) },
        supportingContent = { Text(story.createdAt) },
    )
}