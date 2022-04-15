package com.mikepenz.storyblok.sdk.http

import io.ktor.client.*

internal expect val httpClient: HttpClient

internal fun provideClient(): HttpClient {
    return httpClient
}