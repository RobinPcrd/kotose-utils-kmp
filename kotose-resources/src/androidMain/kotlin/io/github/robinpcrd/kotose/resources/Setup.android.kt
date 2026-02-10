/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotose.resources

import android.content.Context
import kotlin.concurrent.Volatile

internal object AndroidSetup {
    @Volatile
    var appContext: Context? = null

    fun reset() {
        appContext = null
    }
}

/**
 * Provide Android [Context] so that [PlatformStrRes.getStringSuspend] can resolve
 * string resources without a Compose scope.
 *
 * Stores `context.applicationContext` to avoid leaking Activities.
 */
fun KotoseUtilsConfig.androidContext(context: Context) {
    AndroidSetup.appContext = context.applicationContext
}
