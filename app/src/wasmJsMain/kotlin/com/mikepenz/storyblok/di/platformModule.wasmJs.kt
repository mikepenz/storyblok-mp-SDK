package com.mikepenz.storyblok.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.module.Module
import kotlin.coroutines.CoroutineContext

actual fun platformModule(): Module {
    TODO("Not yet implemented")
}

actual fun Module.viewModel() {
    single { createViewModule() }
}

@Composable
actual fun <T> StateFlow<T>.collectAsStateMultiplatform(
    context: CoroutineContext,
): State<T> = collectAsState(context)

@Composable
actual fun <T> Flow<T>.collectAsStateMultiplatformInitial(
    initialValue: T,
    context: CoroutineContext
): State<T> = collectAsState(initialValue, context)
