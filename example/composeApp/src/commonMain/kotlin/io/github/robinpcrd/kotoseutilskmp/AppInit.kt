package io.github.robinpcrd.kotoseutilskmp

import io.github.robinpcrd.kotoseutilskmp.resources.KotoseUtils
import io.github.robinpcrd.kotoseutilskmp.resources.KotoseUtilsConfig

fun initKotoseUtils(
    config: KotoseUtilsConfig.() -> Unit
) {
    KotoseUtils.setup(config)
}