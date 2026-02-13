/*
* Copyright 2026-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotose.resources

internal actual fun formatString(format: String, args: List<Any>): String =
    String.format(format, *args.toTypedArray())
