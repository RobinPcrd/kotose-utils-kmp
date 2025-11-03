/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp

import io.github.robinpcrd.kotoseutilskmp.resources.StrRes

actual object SamplePlatformStrRes {
    actual val SampleStrResAppName: StrRes = StrRes(text = "KotoseUtilsKmp")
    actual val SampleStrResWithArgs: StrRes = StrRes(text = "Not supported on iOS")
}