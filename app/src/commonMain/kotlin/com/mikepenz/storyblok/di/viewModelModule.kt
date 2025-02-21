package com.mikepenz.storyblok.di

import com.mikepenz.storyblok.viewmodel.AppViewModel
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

expect fun Module.viewModel()

fun Scope.createViewModule() = AppViewModel()

val viewModelModule = module {
    viewModel()
}