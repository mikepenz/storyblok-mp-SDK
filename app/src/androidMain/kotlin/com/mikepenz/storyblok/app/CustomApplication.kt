package com.mikepenz.storyblok.app

import android.app.Application
import com.mikepenz.storyblok.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@CustomApplication)
        }
    }
}