plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    id(libs.plugins.androidLibrary.get().pluginId) apply false
    id(libs.plugins.kotlinMultiplatform.get().pluginId) apply false
    id(libs.plugins.androidApplication.get().pluginId) apply false
    //alias(libs.plugins.androidApplication) apply false
    //alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    //alias(libs.plugins.kotlinMultiplatform) apply false
    //alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    //alias(libs.plugins.androidLint) apply false
    alias(libs.plugins.kotlinSerialization) apply false
}