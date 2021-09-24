package com.mikepenz.storyblok.sdk.utils


import kotlinx.datetime.Instant
import java.util.*

fun Instant.toDate(): Date = Date(this.toEpochMilliseconds())