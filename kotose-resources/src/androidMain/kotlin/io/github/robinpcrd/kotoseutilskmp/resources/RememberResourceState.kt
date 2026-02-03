/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalResources

@Composable
inline fun <T> rememberResourceAsState(
    crossinline block: @DisallowComposableCalls Resources.() -> T,
): State<T> {
    LocalConfiguration.current
    val resources = LocalResources.current //LocalContext.current.resources
    return remember(resources) {
        mutableStateOf(
            block(resources)
        )
    }
}

@Composable
inline fun <T> rememberResourceAsState(
    key1: Any?,
    crossinline block: @DisallowComposableCalls Resources.() -> T,
): State<T> {
    LocalConfiguration.current
    val resources = LocalResources.current //LocalContext.current.resources
    return remember(key1, resources) {
        mutableStateOf(
            block(resources)
        )
    }
}

@Composable
inline fun <T> rememberResourceAsState(
    key1: Any?,
    key2: Any?,
    crossinline block: @DisallowComposableCalls Resources.() -> T,
): State<T> {
    LocalConfiguration.current
    val resources = LocalResources.current //LocalContext.current.resources
    return remember(key1, key2, resources) {
        mutableStateOf(
            block(resources)
        )
    }
}

@Composable
inline fun <T> rememberResourceAsState(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    crossinline block: @DisallowComposableCalls Resources.() -> T,
): State<T> {
    LocalConfiguration.current
    val resources = LocalResources.current //LocalContext.current.resources
    return remember(key1, key2, key3, resources) {
        mutableStateOf(
            block(resources)
        )
    }
}

@Composable
inline fun <T> rememberResourceAsState(
    vararg keys: Any?,
    crossinline block: @DisallowComposableCalls Resources.() -> T,
): State<T> {
    LocalConfiguration.current
    val resources = LocalResources.current //LocalContext.current.resources
    return remember(*keys, resources) {
        mutableStateOf(
            block(resources)
        )
    }
}

@Composable
inline fun <T> rememberResource(
    crossinline block: @DisallowComposableCalls Resources.() -> T,
): T {
    LocalConfiguration.current
    val resources = LocalResources.current //LocalContext.current.resources
    return remember(resources) {
        block(resources)
    }
}

@Composable
inline fun <T> rememberResource(
    key1: Any?,
    crossinline block: @DisallowComposableCalls Resources.() -> T,
): T {
    LocalConfiguration.current
    val resources = LocalResources.current //LocalContext.current.resources
    return remember(key1, resources) {
        block(resources)
    }
}

@Composable
inline fun <T> rememberResource(
    key1: Any?,
    key2: Any?,
    crossinline block: @DisallowComposableCalls Resources.() -> T,
): T {
    LocalConfiguration.current
    val resources = LocalResources.current //LocalContext.current.resources
    return remember(key1, key2, resources) {
        block(resources)
    }
}

@Composable
inline fun <T> rememberResource(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    crossinline block: @DisallowComposableCalls Resources.() -> T,
): T {
    LocalConfiguration.current
    val resources = LocalResources.current //LocalContext.current.resources
    return remember(key1, key2, key3, resources) {
        block(resources)
    }
}

@Composable
inline fun <T> rememberResource(
    vararg keys: Any?,
    crossinline block: @DisallowComposableCalls Resources.() -> T,
): T {
    LocalConfiguration.current
    val resources = LocalResources.current //LocalContext.current.resources
    return remember(*keys, resources) {
        block(resources)
    }
}
