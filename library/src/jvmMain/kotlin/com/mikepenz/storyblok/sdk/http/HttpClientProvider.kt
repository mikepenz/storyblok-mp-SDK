package com.mikepenz.storyblok.sdk.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache

internal actual val httpClient: HttpClient = HttpClient(Apache)