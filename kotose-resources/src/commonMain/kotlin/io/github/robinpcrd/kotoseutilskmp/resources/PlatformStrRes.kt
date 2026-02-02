/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Wraps platform-native string resource identifiers.
 *
 * On **Android**, this holds `@StringRes`/`@PluralsRes` resource IDs and resolves them
 * via the Android resource system.
 *
 * On **iOS**, `getString()` returns `null` because there are no Android resources available.
 * For cross-platform string resources, use [StrRes] with `composeString` (Compose Multiplatform
 * resources) or `text` (plain string) instead.
 *
 * Serialization round-trips gracefully on all platforms — the iOS serializer encodes/decodes
 * an empty string without crashing.
 */
@Immutable
@Serializable
expect class PlatformStrRes {
    @Composable
    fun getString(): String?
}

expect object PlatformStrResSerializer : KSerializer<PlatformStrRes> {
    override val descriptor: SerialDescriptor
    override fun serialize(
        encoder: Encoder,
        value: PlatformStrRes
    )

    override fun deserialize(decoder: Decoder): PlatformStrRes
}

fun PlatformStrRes.toStrRes(): StrRes = StrRes(this)