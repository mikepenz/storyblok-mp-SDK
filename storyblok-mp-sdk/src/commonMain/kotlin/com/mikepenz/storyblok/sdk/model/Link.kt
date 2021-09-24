package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A data source is contains the information (slug) to receive a collection of datasource entries. You can use this endpoint to receive all datasources and
 * then call the datasource entries endpoint using the slug of the datasource.
 *
 * @param id Numeric id
 * @param uuid Generated uuid string
 * @param name Given name of the content entry
 * @param slug The full_slug of the content entry
 * @param isFolder Is this content entry a folder (true/false)
 * @param parentId Parent folder numeric id
 * @param published Is this story published (true/false)
 * @param position Numeric position value of the content entry
 * @param isStartpage Is this story a startpage (true/false)
 */
@Serializable
data class Link(
    val id: Long,
    val uuid: String,
    val name: String,
    val slug: String,
    @SerialName("is_folder")
    val isFolder: Boolean = false,
    @SerialName("parent_id")
    val parentId: Long? = null,
    val published: Boolean = false,
    val position: Long? = null,
    @SerialName("is_startpage")
    val isStartpage: Boolean = false
)

@Serializable
internal class LinksWrapper {
    val links: Map<String, Link> = emptyMap()
}