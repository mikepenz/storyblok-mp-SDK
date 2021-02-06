package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * A data source is contains the information (slug) to receive a collection of datasource entries. You can use this endpoint to receive all datasources and
 * then call the datasource entries endpoint using the slug of the datasource.
 */
@Serializable
open class Datasource {
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
     * Array of dimension objects
     */
    var dimensions: List<JsonObject>? = null
}

@Serializable
internal class DatasourceWrapper {
    val datasources: List<Datasource>? = null
}