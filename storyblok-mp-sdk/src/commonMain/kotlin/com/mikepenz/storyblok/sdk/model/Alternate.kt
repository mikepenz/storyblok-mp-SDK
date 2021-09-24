package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param id Numeric id
 * @param name The name you give this story
 * @param slug The slug / path you give this story
 * @param fullSlug Combined parent folder and current slug
 */
@Serializable
data class Alternate(
    val id: Long,
    val name: String,
    val slug: String,
    @SerialName("full_slug")
    val fullSlug: String? = null
)