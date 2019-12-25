package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * This is an object representing your content entry. One Story object can be of a specific type, so called content types and is able to contain components.
 * You define the fields and nestability of your content types to achieve your content structure. To learn how to build a basic blog you can checkout our
 * content building tutorial.
 */
@Serializable
open class Story {
    /**
     * Numeric id
     */
    var id: Long = 0
    /**
     * Generated uuid string
     */
    var uuid: String? = null
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

    /**
     * Creation date (Format: YYYY-mm-dd HH:MM)
     */
    @SerialName("created_at")
    var createdAt: String? = null

    /**
     * Latest publishing date (Format: YYYY-mm-dd HH:MM)
     */
    @SerialName("published_at")
    var publishedAt: String? = null

    /**
     * First publishing date (Format: YYYY-mm-dd HH:MM)
     */
    @SerialName("first_published_at")
    var firstPublishedAt: String? = null

    /**
     * Your defined custom content body object
     */
    var content: JsonObject? = null

    @SerialName("sort_by_date")
    var sortByDate: Boolean? = null

    /**
     * Position in folder
     */
    var position: Int? = null

    @SerialName("tag_list")
    var tagList: List<String>? = null

    /**
     * Is startpage of current folder (true/false)
     */
    @SerialName("is_startpage")
    var isStartPage: Boolean? = null

    /**
     * Parent folder id
     */
    @SerialName("parent_id")
    var parentId: Int? = null

    /**
     * Alternates group id (uuid string)
     */
    @SerialName("group_id")
    var groupId: String? = null

    /**
     * Array of alternate objects
     */
    @SerialName("alternates")
    var alternates: List<String>? = null

    /**
     * Id of your content stage (default: null)
     */
    @SerialName("release_id")
    var releaseId: String? = null

    /**
     * Defined language (default: "default")
     */
    var lang: String? = null
}

@Serializable
internal class StoryWrapper {
    val story: Story? = null
}

@Serializable
internal class StoriesWrapper {
    val stories: List<Story>? = null
}