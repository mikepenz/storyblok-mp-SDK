package com.mikepenz.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            applyDefaultHierarchyTemplate()

            jvm()
            js(IR) {
                nodejs {}
                browser {}
                compilations.all {
                    kotlinOptions {
                        moduleKind = "umd"
                        sourceMap = true
                        sourceMapEmbedSources = null
                    }
                }
            }
            androidTarget {
                publishAllLibraryVariants()
            }
            wasmJs {
                nodejs()
            }

            // tier 1
            // missing ktor linuxX64()
            macosX64()
            macosArm64()
            iosSimulatorArm64()
            iosX64()

            // tier 2
            // missing ktor linuxArm64()
            watchosSimulatorArm64()
            watchosX64()
            watchosArm32()
            watchosArm64()
            tvosSimulatorArm64()
            tvosX64()
            tvosArm64()
            iosArm64()

            // tier 3
            // androidNativeArm32()
            // androidNativeArm64()
            // androidNativeX86()
            // androidNativeX64()
            // missing ktor mingwX64()
            // missing ktor watchosDeviceArm64()

            configureKotlin()
        }
    }
}
