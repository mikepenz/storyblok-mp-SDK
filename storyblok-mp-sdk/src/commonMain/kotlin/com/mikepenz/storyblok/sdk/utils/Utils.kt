package com.mikepenz.storyblok.sdk.utils

import io.ktor.http.ParametersBuilder


/**
 * Utils to append a parameter to the url in a save way
 */
internal fun ParametersBuilder.appendNonNull(name: String, value: Any?) {
    when {
        value is String -> this.append(name, value)
        value is Boolean -> this.append(name, if (value) "1" else "0")
        value is IntArray -> this.append(name, value.joinToString(","))
        value is Array<*> -> this.append(name, value.joinToString(","))
        value != null -> this.append(name, value.toString())
    }
}