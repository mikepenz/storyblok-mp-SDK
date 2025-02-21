import com.mikepenz.gradle.utils.readPropertyOrElse
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("com.mikepenz.convention.kotlin-multiplatform")
    id("com.mikepenz.convention.android-application")
    id("com.mikepenz.convention.compose")
    id("com.mikepenz.aboutlibraries.plugin")
}

kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    androidTarget()

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }


    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.storyblokMpSdk)
            implementation(projects.sampleCommon)

            implementation(compose.runtime) { require(true) }
            implementation(compose.foundation) { require(true) }
            implementation(compose.material3) { require(true) }
            implementation(compose.ui) { require(true) }
            implementation(compose.components.resources) { require(true) }

            implementation(libs.bundles.aboutlibs) // aboutlibraries
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)

            implementation(libs.aboutlibraries.ui)

            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.drawerlayout)
            implementation(libs.androidx.constraintlayout)
            implementation(libs.androidx.recyclerview)
            implementation(libs.androidx.cardview)
            implementation(libs.androidx.activity)
            implementation(libs.androidx.lifecycle.runtime)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.fastadapter)
            implementation(libs.materialdrawer.core)
            implementation(libs.materialdrawer.iconics)
            implementation(libs.iconics.core)
            implementation(libs.iconics.mdit)
        }

        val nonAndroidMain by creating {
            dependsOn(commonMain.get())
        }
        val desktopMain by getting {
            dependsOn(nonAndroidMain)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

        nativeMain {
            dependsOn(nonAndroidMain)
        }

        val wasmJsMain by getting {
            dependsOn(nonAndroidMain)
        }
        wasmJsMain.dependencies {
        }
    }
}

android {
    namespace = "com.mikepenz.storyblok.app"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        versionCode = 2000
        versionName = "2.0.0"
        setProperty("archivesBaseName", "Storyblok-v$versionName")
        buildConfigField(
            "String",
            "STORYBLOK_TOKEN",
            "\"" + readPropertyOrElse("storyblok.token") + "\""
        )
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            signingConfig = signingConfigs.findByName("release")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        buildTypes.release.proguard {
            isEnabled = true
            optimize = true
            obfuscate = true
            configurationFiles.from(project.file("compose-desktop.pro"))
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Storyblok Compose Desktop"
            packageVersion = "2.0.0"
            description = "A small sample app to showcase the power of Storyblok's API"
            copyright = "© 2024 Mike Penz. All rights reserved."
        }
    }
}

aboutLibraries {
    registerAndroidTasks = true
    duplicationMode = com.mikepenz.aboutlibraries.plugin.DuplicateMode.MERGE
}