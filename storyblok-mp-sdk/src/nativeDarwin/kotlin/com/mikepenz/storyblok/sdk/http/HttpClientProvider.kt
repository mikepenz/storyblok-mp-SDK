package com.mikepenz.storyblok.sdk.http

import io.ktor.client.*
import io.ktor.client.engine.darwin.*

internal actual val httpClient: HttpClient = HttpClient(Darwin)