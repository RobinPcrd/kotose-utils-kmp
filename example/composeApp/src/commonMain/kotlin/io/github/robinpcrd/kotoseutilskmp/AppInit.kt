/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp

import io.github.robinpcrd.kotoseutilskmp.resources.KotoseUtils
import io.github.robinpcrd.kotoseutilskmp.resources.KotoseUtilsConfig

fun initKotoseUtils(
    config: KotoseUtilsConfig.() -> Unit
) {
    KotoseUtils.setup(config)
}