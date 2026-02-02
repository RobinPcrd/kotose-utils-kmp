plugins {
    kotlin("multiplatform")
    id("com.android.kotlin.multiplatform.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "kotose_utils_kmp.example.composeapp.generated.resources"
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xexpect-actual-classes",
        )
    }

    android {
        namespace = "io.github.robinpcrd.kotoseutilskmp.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        @Suppress("UnstableApiUsage")
        androidResources {
            enable = true
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            binaryOptions["bundleId"] = "io.github.robinpcrd.kotoseutils"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.jb.compose.ui.tooling.preview)
        }
        commonMain.dependencies {
            api(projects.kotoseResources)
            implementation(libs.kotlinx.collections.immutable)

            implementation(libs.jb.compose.runtime)
            implementation(libs.jb.compose.foundation)
            implementation(libs.jb.compose.material3)
            implementation(libs.jb.compose.ui)
            api(libs.jb.compose.resources)
            implementation(libs.jb.compose.ui.tooling.preview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
