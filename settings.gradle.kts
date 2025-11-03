rootProject.name = "kotose-utils-kmp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.60.6"
}

// Skip the example app when building the libraries
if (gradle.startParameter.taskNames.none { it.startsWith(":example:composeApp") }) {
    startParameter.excludedTaskNames.addAll(
        listOf(
            ":example:composeApp:build",
            ":example:composeApp:assembleDebug",
            ":example:composeApp:assembleDebugUnitTest",
            ":example:composeApp:assembleDebugAndroidTest",
            ":example:composeApp:assemble",
        ),
    )
}

include(
    ":kotose-core",
    ":kotose-resources",
    ":example:composeApp"
)
