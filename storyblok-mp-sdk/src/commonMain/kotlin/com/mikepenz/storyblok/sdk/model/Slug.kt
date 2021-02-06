package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
open class Slug {
    /**
     * the slug name (value)
     */
    var name: String? = null

    /**
     * the path for this slug (value)
     */
    var path: String? = null

    /**
     * the lang of the slug (value)
     */
    var lang: String? = null

    /**
     * 	Is this content entry a folder (true/false)
     */
    @SerialName("is_folder")
    var isFolder: Boolean? = null

    /**
     * Parent folder id
     */
    @SerialName("parent_id")
    var parentId: Int? = null
}