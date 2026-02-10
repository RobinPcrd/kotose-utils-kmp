/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotose.resources

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
 * On **iOS**, this holds a localization key and resolves it via `NSBundle.localizedStringForKey`.
 * Format args are applied after resolution, supporting both Java-style (`%1$s`) and
 * iOS-style (`%1$@`, `%lld`) specifiers.
 */
@Immutable
@Serializable
expect class PlatformStrRes {
    @Composable
    fun getString(): String?

    /**
     * Non-composable suspend resolution.
     *
     * On **iOS**, resolves via `NSBundle.localizedStringForKey`.
     * On **Android**, resolves via the context provided through
     * [KotoseUtilsConfig.androidContext] during setup.
     * Returns `null` if no context was configured.
     */
    suspend fun getStringSuspend(): String?
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