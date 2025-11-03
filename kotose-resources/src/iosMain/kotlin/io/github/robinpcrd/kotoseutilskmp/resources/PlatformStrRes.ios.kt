/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Immutable
@Serializable
actual class PlatformStrRes {
    @Composable
    actual fun getString(): String? {
        // TODO
        return null
    }
}

actual object PlatformStrResSerializer : KSerializer<PlatformStrRes> {
    actual override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "io.github.robinpcrd.kotoseutilskmp.core.PlatformStrRes",
        PrimitiveKind.STRING
    )

    actual override fun serialize(
        encoder: Encoder,
        value: PlatformStrRes
    ) {
        throw NotImplementedError()
    }

    actual override fun deserialize(decoder: Decoder): PlatformStrRes {
        throw NotImplementedError()
    }
}