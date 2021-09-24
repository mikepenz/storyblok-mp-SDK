package com.mikepenz.storyblok.sdk.utils

import kotlinx.datetime.Instant
import java.text.DateFormat
import java.util.*

fun Instant.toDate(): Date = Date(this.toEpochMilliseconds())

fun Instant.toFormattedDateTime(dateStyle: Int = DateFormat.SHORT, timeStyle: Int = DateFormat.SHORT): String {
    return DateFormat.getDateTimeInstance(dateStyle, timeStyle).format(toDate())
}

fun Instant.toFormattedDateTime(dateStyle: Int = DateFormat.SHORT, timeStyle: Int = DateFormat.SHORT, locale: Locale): String {
    return DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale).format(toDate())
}