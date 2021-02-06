package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * In the content delivery API a space object is mostly used to receive the latest version timestamp to invalidate the cache.
 */
@Serializable
open class Space {
    /**
     * Numeric id
     */
    var id: Long = 0

    /**
     * Given name
     */
    var name: String? = null

    /**
     * Given domain
     */
    var domain: String? = null

    /**
     * Cache version
     */
    var version: Long? = null

    /**
     * Array of language codes
     */
    @SerialName("language_codes")
    var languageCodes: List<String>? = null
}

@Serializable
internal class SpaceWrapper {
    val space: Space? = null
}