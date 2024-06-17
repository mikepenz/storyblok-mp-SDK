import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

plugins {
    id("com.mikepenz.android.application")
    id("org.jetbrains.kotlin.multiplatform")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.aboutlibraries)
}

if (appSigningFile != null) {
    apply(from = appSigningFile)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    androidTarget {
    }

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
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }

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
        versionCode = 100
        versionName = "1.0.0"
        setProperty("archivesBaseName", "Storyblok-v$versionName")
        buildConfigField(
            "String",
            "STORYBLOK_TOKEN",
            "\"" + getLocalProperty("storyblok.token") + "\""
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
            packageName = "StoryBlok Compose Desktop"
            packageVersion = "1.0.0"
            description = "A small sample app to showcase the power of Storyblok's API"
            copyright = "© 2024 Mike Penz. All rights reserved."
        }
    }
}

compose.experimental {
    web.application {}
}

aboutLibraries {
    registerAndroidTasks = true
    duplicationMode = com.mikepenz.aboutlibraries.plugin.DuplicateMode.MERGE
}

private val appSigningFile: String?
    get() {
        val k = "signing.file"
        return Properties().also { prop ->
            rootProject.file("local.properties").takeIf { it.exists() }?.let {
                prop.load(it.inputStream())
            }
        }.getProperty(k, null) ?: if (project.hasProperty(k)) project.property(k)
            ?.toString() else null
    }


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        if (project.findProperty("composeCompilerReports") == "true") {
            freeCompilerArgs += listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.layout.buildDirectory.asFile.get().absolutePath}/compose_compiler"
            )
        }
        if (project.findProperty("composeCompilerMetrics") == "true") {
            freeCompilerArgs += listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.layout.buildDirectory.asFile.get().absolutePath}/compose_compiler"
            )
        }
    }
}

fun Project.getLocalProperty(key: String, file: String = "local.properties"): Any {
    val properties = Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else error("File from not found")

    return properties.getProperty(key) ?: ""
}