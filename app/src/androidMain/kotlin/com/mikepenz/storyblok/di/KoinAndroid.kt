package com.mikepenz.storyblok.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

actual fun platformModule() = module {
    single<CoroutineScope> { CoroutineScope(Dispatchers.Default) }
}

actual fun Module.viewModel() {
    viewModel { createViewModule() }
}

@Composable
actual fun <T> StateFlow<T>.collectAsStateMultiplatform(
    context: CoroutineContext,
): State<T> = collectAsStateWithLifecycle(context = context)

@Composable
actual fun <T> Flow<T>.collectAsStateMultiplatformInitial(
    initialValue: T,
    context: CoroutineContext,
): State<T> = collectAsStateWithLifecycle(initialValue = initialValue, context = context)
