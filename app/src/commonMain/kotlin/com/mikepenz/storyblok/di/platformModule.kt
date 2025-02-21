package com.mikepenz.storyblok.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

expect fun platformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        modules(commonModule(), viewModelModule)
        appDeclaration()
    }

fun commonModule() = module {
    includes(platformModule())

    // TODO control based on build mode
    // Logger.setMinSeverity(Severity.Warn)
}


@Composable
expect fun <T> StateFlow<T>.collectAsStateMultiplatform(
    context: CoroutineContext = EmptyCoroutineContext,
): State<T>

@Composable
expect fun <T> Flow<T>.collectAsStateMultiplatformInitial(
    initialValue: T,
    context: CoroutineContext = EmptyCoroutineContext,
): State<T>

