package io.github.robinpcrd.kotoseutilskmp.resources

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalSerializationApi::class)
object StringResourceSerializer : KSerializer<StringResource?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "org.jetbrains.compose.resources.StringResource",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: StringResource?) {
        val key = value?.key
        if (key != null)
            encoder.encodeString(key)
        else
            encoder.encodeNull()
    }

    override fun deserialize(decoder: Decoder): StringResource? {
        return Setup.getStringResource(decoder.decodeString())
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

@OptIn(ExperimentalSerializationApi::class)
object PluralStringResourceSerializer : KSerializer<PluralStringResource?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "org.jetbrains.compose.resources.PluralStringResource",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: PluralStringResource?) {
        val key = value?.key
        if (key != null)
            encoder.encodeString(key)
        else
            encoder.encodeNull()
    }

    override fun deserialize(decoder: Decoder): PluralStringResource? {
        return Setup.getPluralStringResource(decoder.decodeString())
    }
}
