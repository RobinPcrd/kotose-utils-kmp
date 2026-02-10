/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotose.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getPluralString
import org.jetbrains.compose.resources.getString

/**
 * Unified string resource wrapper supporting platform-native resources ([PlatformStrRes]),
 * Compose Multiplatform resources ([StringResource], [PluralStringResource]), and plain text.
 *
 * Not a data class - [copy] would allow creating invalid states (e.g. both
 * [platformStrRes] and [composeString] set).
 *
 * **Format args:** [formatArgs] apply to [composeString], [composePluralString], and [text]
 * paths. [PlatformStrRes] uses its own format args; this [formatArgs] is ignored for that path.
 *
 * **Serialization:** JSON only ([FormatArgsSerializer] requires [JsonEncoder]/[JsonDecoder]).
 */
@Immutable
@Serializable
class StrRes internal constructor(
    val platformStrRes: PlatformStrRes? = null,
    @Serializable(StringResourceSerializer::class)
    val composeString: StringResource? = null,
    //@Serializable(StringArrayResourceSerializer::class)
    //val composeStringArray: StringArrayResource? = null,
    @Serializable(PluralStringResourceSerializer::class)
    val composePluralString: PluralStringResource? = null,
    val quantity: Int? = null,
    val text: String? = null,
    @Serializable(FormatArgsSerializer::class)
    val formatArgs: ImmutableList<Any> = persistentListOf(),
) {
    constructor(
        platformStrRes: PlatformStrRes,
    ) : this(
        platformStrRes = platformStrRes,
        composeString = null,
        composePluralString = null,
        quantity = null,
        text = null,
        formatArgs = persistentListOf(),
    )

    constructor(
        composeString: StringResource,
        formatArgs: ImmutableList<@Serializable @Contextual Any> = persistentListOf(),
    ) : this(
        platformStrRes = null,
        composeString = composeString,
        composePluralString = null,
        quantity = null,
        text = null,
        formatArgs = formatArgs,
    )

    constructor(
        composePluralString: PluralStringResource,
        quantity: Int,
        formatArgs: ImmutableList<@Serializable @Contextual Any> = persistentListOf(),
    ) : this(
        platformStrRes = null,
        composeString = null,
        composePluralString = composePluralString,
        quantity = quantity,
        text = null,
        formatArgs = formatArgs,
    )

    constructor(
        text: String,
    ) : this(
        platformStrRes = null,
        composeString = null,
        composePluralString = null,
        quantity = null,
        text = text,
        formatArgs = persistentListOf(),
    )

    @Composable
    fun getString(): String? {
        val formatArgs = formatArgs.map {
            when (it) {
                is StrRes -> it.getString() ?: ""
                else -> it
            }
        }

        return when {
            platformStrRes != null -> platformStrRes.getString()
            composeString != null -> org.jetbrains.compose.resources.stringResource(
                composeString,
                *formatArgs.toTypedArray()
            )
            //composeStringArray != null -> org.jetbrains.compose.resources.stringArrayResource(composeStringArray)
            composePluralString != null && quantity != null -> org.jetbrains.compose.resources.pluralStringResource(
                composePluralString,
                quantity,
                *formatArgs.toTypedArray()
            )

            text != null && formatArgs.isNotEmpty() -> formatString(text, formatArgs)
            text != null -> text
            else -> null
        }
    }

    @Composable
    fun getStringOrEmpty(): String = getString().orEmpty()

    /**
     * Non-composable suspend resolution.
     *
     * Resolves [composeString], [composePluralString], [text], and [platformStrRes]
     * on all platforms. On Android, [platformStrRes] resolution requires
     * [KotoseUtilsConfig.androidContext] to be configured during setup.
     */
    suspend fun getStringSuspend(): String? {
        val resolvedArgs = formatArgs.map {
            when (it) {
                is StrRes -> it.getStringSuspend() ?: ""
                else -> it
            }
        }

        return when {
            platformStrRes != null -> platformStrRes.getStringSuspend()
            composeString != null -> getString(composeString, *resolvedArgs.toTypedArray())
            composePluralString != null && quantity != null -> getPluralString(
                composePluralString,
                quantity,
                *resolvedArgs.toTypedArray()
            )

            text != null && resolvedArgs.isNotEmpty() -> formatString(text, resolvedArgs)
            text != null -> text
            else -> null
        }
    }

    suspend fun getStringSuspendOrEmpty(): String = getStringSuspend().orEmpty()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as StrRes

        if (quantity != other.quantity) return false
        if (platformStrRes != other.platformStrRes) return false
        if (composeString != other.composeString) return false
        if (composePluralString != other.composePluralString) return false
        if (text != other.text) return false
        if (formatArgs != other.formatArgs) return false

        return true
    }

    override fun hashCode(): Int {
        var result = quantity ?: 0
        result = 31 * result + (platformStrRes?.hashCode() ?: 0)
        result = 31 * result + (composeString?.hashCode() ?: 0)
        result = 31 * result + (composePluralString?.hashCode() ?: 0)
        result = 31 * result + (text?.hashCode() ?: 0)
        result = 31 * result + formatArgs.hashCode()
        return result
    }

    companion object {
        val Empty = StrRes(text = "")
    }
}

@Composable
fun StrRes?.getStringOrEmpty(): String = this?.getString().orEmpty()

fun strResOf(string: String) = StrRes(text = string)

fun String.toStrRes(): StrRes = StrRes(text = this)

fun StringResource.toStrRes() = StrRes(composeString = this)

fun PluralStringResource.toStrRes(quantity: Int) =
    StrRes(composePluralString = this, quantity = quantity)
