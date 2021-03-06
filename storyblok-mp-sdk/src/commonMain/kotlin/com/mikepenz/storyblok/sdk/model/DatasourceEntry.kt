package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A data source is simply a collection of key-value pairs. One specific datasource-entry is a set of two linked attributes: a key,
 * which is a unique identifier for the item and the value.
 *
 * Key-value pairs can be used for a single-choice, multiple-choice options and as well directly through our API to use them for multi-language labels,
 * categories, or any use-case you might need key-value pairs.
 */
@Serializable
open class DatasourceEntry {
    /**
     * Numeric id
     */
    var id: Long = 0

    /**
     * Given name
     */
    var name: String? = null

    /**
     * Given slug
     */
    var slug: String? = null

    /**
     * Given value in the requested dimension
     */
    @SerialName("dimension_value")
    var dimensionValue: String? = null
}

@Serializable
internal class DatasourceEntryWrapper {
    @SerialName("datasource_entries")
    val datasourceEntries: List<DatasourceEntry>? = null
}