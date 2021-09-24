package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Each tag is a string value that can be reused across Stories to create features like word clouds, basic taggings for custom workflows, or similar usecases.
 *
 * @param name The actual tag (value)
 * @param taggingsCount Count of how many times this tag is currently in use across all stories
 */
@Serializable
data class Tag(
    val name: String,
    @SerialName("taggings_count")
    val taggingsCount: Long = 0
)

@Serializable
internal class TagsWrapper {
    val tags: List<Tag> = emptyList()
}