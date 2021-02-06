package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
open class Alternate {
    /**
     * Numeric id
     */
    var id: Long = 0

    /**
     * The name you give this story
     */
    var name: String? = null

    /**
     * The slug / path you give this story
     */
    var slug: String? = null

    /**
     * Combined parent folder and current slug
     */
    @SerialName("full_slug")
    var fullSlug: String? = null
}