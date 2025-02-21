plugins {
    id("com.mikepenz.convention.android-library")
    id("com.mikepenz.convention.kotlin-multiplatform")
}

android {
    namespace = "com.mikepenz.storyblok.common"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.storyblokMpSdk)
                implementation(libs.kotlinx.datetime)

                // logging
                implementation(libs.kermit)
            }
        }
    }
}