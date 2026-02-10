/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

@file:Suppress("unused")

package io.github.robinpcrd.kotose.resources

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource

@Stable
data class PainterRes(
    @DrawableRes
    val res: Int? = null,
    val vector: ImageVector? = null,
    val bitmap: ImageBitmap? = null,
    val painter: Painter? = null
) {
    @Composable
    @JvmName("getPainterComposable")
    fun getPainter(): Painter? = when {
        res != null -> painterResource(id = res)
        vector != null -> rememberVectorPainter(image = vector)
        bitmap != null -> remember(bitmap) { BitmapPainter(bitmap) }
        painter != null -> painter
        else -> null
    }
}