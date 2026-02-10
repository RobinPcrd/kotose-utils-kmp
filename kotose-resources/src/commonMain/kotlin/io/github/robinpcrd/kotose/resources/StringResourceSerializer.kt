/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotose.resources

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource

object StringResourceSerializer : KSerializer<StringResource> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "org.jetbrains.compose.resources.StringResource",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: StringResource) {
        encoder.encodeString(value.key)
    }

    override fun deserialize(decoder: Decoder): StringResource {
        val key = decoder.decodeString()
        return Setup.getStringResource(key)
            ?: error("No StringResource found for key: $key")
    }
}

/*
@OptIn(ExperimentalSerializationApi::class)
object StringArrayResourceSerializer : KSerializer<StringArrayResource?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "org.jetbrains.compose.resources.StringArrayResource",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: StringArrayResource?) {
        val key = value?.key
        if (key != null)
            encoder.encodeString(key)
        else
            encoder.encodeNull()
    }

    override fun deserialize(decoder: Decoder): StringArrayResource? {
        return Setup.getStringArrayResource(decoder.decodeString())
    }
}
*/

object PluralStringResourceSerializer : KSerializer<PluralStringResource> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "org.jetbrains.compose.resources.PluralStringResource",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: PluralStringResource) {
        encoder.encodeString(value.key)
    }

    override fun deserialize(decoder: Decoder): PluralStringResource {
        val key = decoder.decodeString()
        return Setup.getPluralStringResource(key)
            ?: error("No PluralStringResource found for key: $key")
    }
}
