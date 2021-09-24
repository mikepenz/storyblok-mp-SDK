package com.mikepenz.storyblok.sdk.utils

import com.mikepenz.storyblok.sdk.model.Story
import kotlinx.datetime.Instant

/**
 * Creation date as instant
 */
val Story.createdAtInstant: Instant
    get() = Instant.parse(createdAt)

/**
 * Latest publishing date as instant
 */
val Story.publishedAtInstant: Instant?
    get() = publishedAt?.let { Instant.parse(it) }

/**
 * First publishing date as instant
 */
val Story.firstPublishedAtInstant: Instant?
    get() = firstPublishedAt?.let { Instant.parse(it) }