/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotose.resources

// Matches Java-style (%s, %1$s, %2$d) and iOS-style (%@, %1$@, %lld, %1$lld) format specifiers.
private val FORMAT_SPECIFIER = Regex("""%((\d+)\$)?(l{0,2}[df]|[sf@])""")

internal actual fun formatString(format: String, args: List<Any>): String {
    // NSString.stringWithFormat only supports a fixed number of variadic args from Kotlin,
    // so we resolve specifiers ourselves.
    var sequentialIndex = 0
    return FORMAT_SPECIFIER.replace(format) { match ->
        val positionalIndex = match.groupValues[2] // e.g. "1" from "%1$s"
        val argIndex = if (positionalIndex.isNotEmpty()) {
            positionalIndex.toInt() - 1 // 1-based → 0-based
        } else {
            sequentialIndex++
        }
        args.getOrNull(argIndex)?.toString() ?: match.value
    }
}
