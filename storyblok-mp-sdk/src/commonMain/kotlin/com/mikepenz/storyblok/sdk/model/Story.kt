package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * This is an object representing your content entry. One Story object can be of a specific type, so called content types and is able to contain components.
 * You define the fields and nestability of your content types to achieve your content structure. To learn how to build a basic blog you can checkout our
 * content building tutorial.
 *
 * @param id Numeric id
 * @param uuid Generated uuid string
 * @param name The name you give this story
 * @param slug The slug / path you give this story
 * @param fullSlug Combined parent folder and current slug
 * @param defaultFullSlug Contains the full slug of the default language if the app Translatable Slugs is installed
 * @param createdAt Creation date
 * @param publishedAt Latest publishing date
 * @param firstPublishedAt First publishing date
 * @param content Your defined custom content body object
 * @param sortByDate
 * @param position Position in folder
 * @param tagList
 * @param isStartPage Is startpage of current folder (true/false)
 * @param parentId Parent folder id
 * @param groupId Alternates group id (uuid string)
 * @param alternates Array of alternate objects
 * @param translatedSlugs Array of alternate objects
 * @param releaseId Id of your content stage (default: null)
 * @param lang Defined language (default: "default")
 */
@Serializable
data class Story(
    val id: Long,
    val uuid: String,
    val name: String,
    val slug: String,
    @SerialName("full_slug")
    val fullSlug: String,
    @SerialName("default_full_slug")
    val defaultFullSlug: String? = null,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("published_at")
    val publishedAt: String? = null,
    @SerialName("first_published_at")
    val firstPublishedAt: String? = null,
    val content: JsonObject? = null,
    @SerialName("sort_by_date")
    val sortByDate: Boolean? = null,
    val position: Int? = null,
    @SerialName("tag_list")
    val tagList: List<String>? = null,
    @SerialName("is_startpage")
    val isStartPage: Boolean = false,
    @SerialName("parent_id")
    val parentId: Int? = null,
    @SerialName("group_id")
    val groupId: String? = null,
    @SerialName("alternates")
    val alternates: List<Alternate>? = null,
    @SerialName("translated_slugs")
    val translatedSlugs: List<JsonObject>? = null,
    @SerialName("release_id")
    val releaseId: String? = null,
    val lang: String = "default"
)

@Serializable
internal class StoryWrapper {
    val story: Story? = null
}

@Serializable
internal class StoriesWrapper {
    val stories: List<Story> = emptyList()
}