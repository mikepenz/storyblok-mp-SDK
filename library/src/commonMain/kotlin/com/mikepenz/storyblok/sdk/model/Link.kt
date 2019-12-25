package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A data source is contains the information (slug) to receive a collection of datasource entries. You can use this endpoint to receive all datasources and
 * then call the datasource entries endpoint using the slug of the datasource.
 */
@Serializable
open class Link {
    /**
     * Numeric id
     */
    var id: Long = 0
    /**
     * Generated uuid string
     */
    var uuid: String? = null
    /**
     * Given name of the content entry
     */
    var name: String? = null

    /**
     * The full_slug of the content entry
     */
    var slug: String? = null

    /**
     * 	Is this content entry a folder (true/false)
     */
    @SerialName("is_folder")
    var isFolder: Boolean? = null

    /**
     * 	Parent folder numeric id
     */
    @SerialName("parent_id")
    var parentId: Long? = null

    /**
     * 	Is this story published (true/false)
     */
    var published: Boolean? = null

    /**
     * Numeric position value of the content entry
     */
    var position: Long? = null

    /**
     * Is this story a startpage (true/false)
     */
    @SerialName("is_startpage")
    var isStartpage: Boolean? = null
}

@Serializable
internal class LinksWrapper {
    val links: Map<String, Link>? = null
}