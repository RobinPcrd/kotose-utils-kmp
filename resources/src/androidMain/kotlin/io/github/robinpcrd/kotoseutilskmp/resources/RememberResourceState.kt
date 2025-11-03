package io.github.robinpcrd.kotoseutilskmp.resources

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.runBlocking

@Composable
inline fun <T> rememberResourceAsState(
    crossinline block: @DisallowComposableCalls suspend Resources.() -> T,
): State<T> {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return remember(resources) {
        mutableStateOf(
            runBlocking { block(resources) }
        )
    }
}

@Composable
inline fun <T> rememberResourceAsState(
    key1: Any?,
    crossinline block: @DisallowComposableCalls suspend Resources.() -> T,
): State<T> {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return remember(key1, resources) {
        mutableStateOf(
            runBlocking { block(resources) }
        )
    }
}

@Composable
inline fun <T> rememberResourceAsState(
    key1: Any?,
    key2: Any?,
    crossinline block: @DisallowComposableCalls suspend Resources.() -> T,
): State<T> {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return remember(key1, key2, resources) {
        mutableStateOf(
            runBlocking { block(resources) }
        )
    }
}

@Composable
inline fun <T> rememberResourceAsState(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    crossinline block: @DisallowComposableCalls suspend Resources.() -> T,
): State<T> {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return remember(key1, key2, key3, resources) {
        mutableStateOf(
            runBlocking { block(resources) }
        )
    }
}

@Composable
inline fun <T> rememberResourceAsState(
    vararg keys: Any?,
    crossinline block: @DisallowComposableCalls suspend Resources.() -> T,
): State<T> {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return remember(*keys, resources) {
        mutableStateOf(
            runBlocking { block(resources) }
        )
    }
}

@Composable
inline fun <T> rememberResource(
    crossinline block: @DisallowComposableCalls suspend Resources.() -> T,
): T {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return remember(resources) {
        runBlocking { block(resources) }
    }
}

@Composable
inline fun <T> rememberResource(
    key1: Any?,
    crossinline block: @DisallowComposableCalls suspend Resources.() -> T,
): T {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return remember(key1, resources) {
        runBlocking { block(resources) }
    }
}

@Composable
inline fun <T> rememberResource(
    key1: Any?,
    key2: Any?,
    crossinline block: @DisallowComposableCalls suspend Resources.() -> T,
): T {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return remember(key1, key2, resources) {
        runBlocking { block(resources) }
    }
}

@Composable
inline fun <T> rememberResource(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    crossinline block: @DisallowComposableCalls suspend Resources.() -> T,
): T {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return remember(key1, key2, key3, resources) {
        runBlocking { block(resources) }
    }
}

@Composable
inline fun <T> rememberResource(
    vararg keys: Any?,
    crossinline block: @DisallowComposableCalls suspend Resources.() -> T,
): T {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return remember(*keys, resources) {
        runBlocking { block(resources) }
    }
}