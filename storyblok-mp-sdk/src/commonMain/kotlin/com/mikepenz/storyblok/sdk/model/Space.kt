package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * In the content delivery API a space object is mostly used to receive the latest version timestamp to invalidate the cache.
 *
 * @param id Numeric id
 * @param name Given name
 * @param domain Given domain
 * @param version Cache version
 * @param languageCodes Array of language codes
 */
@Serializable
data class Space(
    val id: Long,
    val name: String,
    val domain: String,
    val version: Long = 0,
    @SerialName("language_codes")
    val languageCodes: List<String> = emptyList()
)

@Serializable
internal class SpaceWrapper {
    val space: Space? = null
}