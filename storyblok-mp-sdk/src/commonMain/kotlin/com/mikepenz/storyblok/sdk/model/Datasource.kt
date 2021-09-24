package com.mikepenz.storyblok.sdk.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * A data source is contains the information (slug) to receive a collection of datasource entries. You can use this endpoint to receive all datasources and
 * then call the datasource entries endpoint using the slug of the datasource.
 *
 * @param id Numeric id
 * @param name Given name
 * @param slug Given slug
 * @param dimensions Array of dimension objects
 */
@Serializable
data class Datasource(
    val id: Long,
    val name: String,
    val slug: String,
    val dimensions: List<JsonObject>? = null
)

@Serializable
internal class DatasourceWrapper {
    val datasources: List<Datasource> = emptyList()
}