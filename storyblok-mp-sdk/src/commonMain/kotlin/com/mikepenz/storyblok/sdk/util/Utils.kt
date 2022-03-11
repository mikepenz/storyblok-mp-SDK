package com.mikepenz.storyblok.sdk.util

import io.ktor.client.request.*

/**
 * Sets a single URL query parameter of [key] with a specific [value] Array if the value is not null. Can not be used to set
 * form parameters in the body.
 */
fun HttpRequestBuilder.parameter(key: String, value: Array<String>?): Unit =
    value?.let { url.parameters.append(key, it.joinToString(",")) } ?: Unit
