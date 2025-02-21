plugins {
    id("com.mikepenz.convention.android-library")
    id("com.mikepenz.convention.kotlin-multiplatform")
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.dokka)
    alias(libs.plugins.mavenpublish)
    alias(libs.plugins.kotlinSerialization)
    id("org.jetbrains.kotlin.native.cocoapods")
}

android {
    namespace = "com.mikepenz.storyblok.sdk"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

kotlin {

    cocoapods {
        summary = "Kotlin multiplatform storyblok SDK"
        homepage = "https://github.com/mikepenz/storyblok-mp-SDK"
        authors = "Mike Penz"
        license = "Apache 2.0"
        version = "2.0.0"
    }

    sourceSets {
        commonMain {
            dependencies {
                // Coroutines
                implementation(libs.kotlinx.coroutines.core)

                // Ktor
                implementation(libs.bundles.ktor)
                // implementation("io.ktor:ktor-client-core:${versions.ktor}")
                // implementation("io.ktor:ktor-client-logging:${versions.ktor}")

                // kotlinx Serialize
                implementation(libs.kotlinx.serialization)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.okhttp)
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.ktor.apache)
            }
        }

        nativeMain {
            dependencies {
                implementation(libs.ktor.cio)
            }
        }

        appleMain {
            dependencies {
                implementation(libs.ktor.darwin)
            }
        }
    }
}

baselineProfile {
    filter { include("com.mikepenz.storyblok.*") }
}
