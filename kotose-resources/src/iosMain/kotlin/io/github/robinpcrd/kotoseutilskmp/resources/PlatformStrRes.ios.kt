/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonArray
import platform.Foundation.NSBundle

@Immutable
@Serializable
actual data class PlatformStrRes(
    val key: String = "",
    @Serializable(FormatArgsSerializer::class)
    val formatArgs: ImmutableList<Any> = persistentListOf(),
) {
    @Composable
    actual fun getString(): String? {
        val resolved = NSBundle.mainBundle.localizedStringForKey(key, value = key, table = null)
        if (resolved == key) return null // key not found in bundle
        if (formatArgs.isEmpty()) return resolved
        val args = formatArgs.map {
            when (it) {
                is StrRes -> it.getString() ?: ""
                else -> it
            }
        }
        return formatString(resolved, args)
    }
}

actual object PlatformStrResSerializer : KSerializer<PlatformStrRes> {
    actual override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("io.github.robinpcrd.kotoseutilskmp.resources.PlatformStrRes") {
            element<String>("key")
            element("formatArgs", JsonArray.serializer().descriptor)
        }

    actual override fun serialize(
        encoder: Encoder,
        value: PlatformStrRes
    ) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.key)
            encodeSerializableElement(
                descriptor,
                1,
                FormatArgsSerializer,
                value.formatArgs.toImmutableList()
            )
        }
    }

    actual override fun deserialize(decoder: Decoder): PlatformStrRes {
        var key = ""
        var formatArgs: List<Any> = emptyList()

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> key = decodeStringElement(descriptor, 0)
                    1 -> formatArgs = decodeSerializableElement(descriptor, 1, FormatArgsSerializer)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
        }

        return PlatformStrRes(
            key = key,
            formatArgs = formatArgs.toImmutableList()
        )
    }
}
