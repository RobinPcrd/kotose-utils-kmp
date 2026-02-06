/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import android.content.Context
import android.content.res.Resources
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonArray
@Immutable
@Serializable
actual data class PlatformStrRes(
    @StringRes
    val stringRes: Int? = null,
    @PluralsRes
    val pluralRes: Int? = null,
    val quantity: Int? = null,
    @Serializable(FormatArgsSerializer::class)
    val formatArgs: ImmutableList<Any> = persistentListOf(),
) {
    @Composable
    actual fun getString(): String? {
        val resolvedArgs = formatArgs.map { if (it is StrRes) it.getString() ?: "" else it }
        return when {
            pluralRes != null && resolvedArgs.isEmpty() -> pluralStringResource(id = pluralRes, count = quantity ?: 0)
            pluralRes != null -> pluralStringResource(
                id = pluralRes,
                count = quantity ?: 0,
                *resolvedArgs.toTypedArray()
            )

            stringRes != null && resolvedArgs.isEmpty() -> stringResource(id = stringRes)
            stringRes != null -> stringResource(id = stringRes, *resolvedArgs.toTypedArray())
            else -> null
        }
    }

    /**
     * Resolves this resource outside a Compose scope using the context
     * provided via [KotoseUtilsConfig.androidContext].
     *
     * Returns `null` if no context was configured or if neither [stringRes]
     * nor [pluralRes] is set.
     */
    actual suspend fun getStringSuspend(): String? {
        val resources = AndroidSetup.appContext?.resources ?: return null
        val resolvedArgs = formatArgs.map { if (it is StrRes) it.getStringSuspend() ?: "" else it }
        return when {
            pluralRes != null && resolvedArgs.isEmpty() ->
                resources.getQuantityString(pluralRes, quantity ?: 0)

            pluralRes != null ->
                resources.getQuantityString(pluralRes, quantity ?: 0, *resolvedArgs.toTypedArray())

            stringRes != null && resolvedArgs.isEmpty() ->
                resources.getString(stringRes)

            stringRes != null ->
                resources.getString(stringRes, *resolvedArgs.toTypedArray())

            else -> null
        }
    }
}

fun StrRes.getString(context: Context): String? = getString(context.resources)

fun StrRes.getString(resources: Resources): String? {
    val formatArgs = formatArgs.map {
        when (it) {
            is StrRes -> it.getString(resources).orEmpty()
            else -> it
        }
    }
    return when {
        platformStrRes != null && platformStrRes.pluralRes != null && formatArgs.isEmpty() -> resources.getQuantityString(
            platformStrRes.pluralRes,
            platformStrRes.quantity ?: 0
        )

        platformStrRes != null && platformStrRes.pluralRes != null -> resources.getQuantityString(
            platformStrRes.pluralRes,
            platformStrRes.quantity ?: 0,
            *formatArgs.toTypedArray()
        )

        platformStrRes != null && platformStrRes.stringRes != null && formatArgs.isEmpty() -> resources.getString(
            platformStrRes.stringRes
        )

        platformStrRes != null && platformStrRes.stringRes != null -> resources.getString(
            platformStrRes.stringRes,
            *formatArgs.toTypedArray()
        )

        formatArgs.isEmpty() -> text
        text != null -> String.format(text, *formatArgs.toTypedArray())
        else -> null
    }
}

fun @receiver:StringRes Int.toStrRes(
    formatArgs: ImmutableList<Any> = persistentListOf()
) = StrRes(PlatformStrRes(stringRes = this, formatArgs = formatArgs))

fun @receiver:StringRes Int.toPlatformStrRes(
    formatArgs: ImmutableList<Any> = persistentListOf()
) = PlatformStrRes(stringRes = this, formatArgs = formatArgs)

fun @receiver:PluralsRes Int.toStrRes(
    quantity: Int,
    formatArgs: ImmutableList<Any> = persistentListOf()
) = StrRes(PlatformStrRes(pluralRes = this, quantity = quantity, formatArgs = formatArgs))

fun @receiver:PluralsRes Int.toPlatformStrRes(
    quantity: Int,
    formatArgs: ImmutableList<Any> = persistentListOf()
) = PlatformStrRes(pluralRes = this, quantity = quantity, formatArgs = formatArgs)

@Composable
fun rememberStrRes(strRes: StrRes): String? = rememberResource(strRes) {
    strRes.getString(this)
}

@Composable
fun rememberStrResAsState(strRes: StrRes): State<String?> = rememberResourceAsState(strRes) {
    strRes.getString(this)
}

actual object PlatformStrResSerializer : KSerializer<PlatformStrRes> {
    actual override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("PlatformStringRes") {
            element<Int?>("stringRes", isOptional = true)
            element<Int?>("pluralRes", isOptional = true)
            element<Int?>("quantity", isOptional = true)
            element("formatArgs", JsonArray.serializer().descriptor)
        }

    actual override fun serialize(encoder: Encoder, value: PlatformStrRes) {
        encoder.encodeStructure(descriptor) {
            if (value.stringRes != null) {
                encodeSerializableElement(descriptor, 0, Int.serializer(), value.stringRes)
            }
            if (value.pluralRes != null) {
                encodeSerializableElement(descriptor, 1, Int.serializer(), value.pluralRes)
            }
            if (value.quantity != null) {
                encodeSerializableElement(descriptor, 2, Int.serializer(), value.quantity)
            }
            encodeSerializableElement(
                descriptor,
                3,
                FormatArgsSerializer,
                value.formatArgs.toImmutableList()
            )
        }
    }

    actual override fun deserialize(decoder: Decoder): PlatformStrRes {
        var stringRes: Int? = null
        var pluralRes: Int? = null
        var quantity: Int? = null
        var formatArgs: List<Any> = emptyList()

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> stringRes = decodeSerializableElement(descriptor, 0, Int.serializer())
                    1 -> pluralRes = decodeSerializableElement(descriptor, 1, Int.serializer())
                    2 -> quantity = decodeSerializableElement(descriptor, 2, Int.serializer())
                    3 -> formatArgs = decodeSerializableElement(descriptor, 3, FormatArgsSerializer)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
        }

        return PlatformStrRes(
            stringRes = stringRes,
            pluralRes = pluralRes,
            quantity = quantity,
            formatArgs = formatArgs.toImmutableList()
        )
    }
}
