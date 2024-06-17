package com.mikepenz.storyblok.sdk.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.JsClient

internal actual val httpClient: HttpClient = HttpClient(JsClient())