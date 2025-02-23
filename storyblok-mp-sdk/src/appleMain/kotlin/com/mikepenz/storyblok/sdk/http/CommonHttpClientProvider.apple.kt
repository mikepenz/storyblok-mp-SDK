package com.mikepenz.storyblok.sdk.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

internal actual val httpClient: HttpClient = HttpClient(Darwin)