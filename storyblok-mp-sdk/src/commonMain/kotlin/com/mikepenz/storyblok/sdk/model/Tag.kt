package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Each tag is a string value that can be reused accross Stories to create features like word clouds, basic taggings for custom workflows, or similar usecases.
 */
@Serializable
open class Tag {
    /**
     * the actual tag (value)
     */
    var name: String? = null

    /**
     * 	Count of how many times this tag is currenlty in use accross all stories
     */
    @SerialName("taggings_count")
    var taggingsCount: Long? = null
}

@Serializable
internal class TagsWrapper {
    val tags: List<Tag>? = null
}