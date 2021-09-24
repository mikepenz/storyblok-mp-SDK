package com.mikepenz.storyblok.sdk.utils

import com.mikepenz.storyblok.sdk.model.Story
import kotlinx.datetime.Instant
import java.text.DateFormat
import java.util.*


fun Story.readableCreatedAt(dateStyle: Int = DateFormat.SHORT, timeStyle: Int = DateFormat.SHORT): String {
    return this.createdAtInstant.toFormattedDateTime(dateStyle, timeStyle)
}

fun Instant.toDate(): Date = Date(this.toEpochMilliseconds())

fun Instant.toFormattedDateTime(dateStyle: Int = DateFormat.SHORT, timeStyle: Int = DateFormat.SHORT): String {
    return DateFormat.getDateTimeInstance(dateStyle, timeStyle).format(toDate())
}