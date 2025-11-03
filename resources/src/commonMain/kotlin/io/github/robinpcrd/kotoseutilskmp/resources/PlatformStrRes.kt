package io.github.robinpcrd.kotoseutilskmp.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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