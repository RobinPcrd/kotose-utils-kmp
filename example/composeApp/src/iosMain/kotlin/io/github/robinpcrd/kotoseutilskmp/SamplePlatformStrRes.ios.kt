/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp

import io.github.robinpcrd.kotoseutilskmp.resources.PlatformStrRes
import io.github.robinpcrd.kotoseutilskmp.resources.StrRes
import io.github.robinpcrd.kotoseutilskmp.resources.toStrRes
import kotlinx.collections.immutable.persistentListOf

actual object SamplePlatformStrRes {
    actual val SampleStrResAppName: StrRes = PlatformStrRes(
        key = "app_name"
    ).toStrRes()
    actual val SampleStrResWithArgs: StrRes = PlatformStrRes(
        key = "hello_with_args",
        formatArgs = persistentListOf("World", 3)
    ).toStrRes()
}
