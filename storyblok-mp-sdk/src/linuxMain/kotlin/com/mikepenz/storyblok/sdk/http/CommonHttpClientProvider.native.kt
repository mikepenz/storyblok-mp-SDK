package com.mikepenz.storyblok.sdk.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

internal actual val httpClient: HttpClient = HttpClient(CIO)