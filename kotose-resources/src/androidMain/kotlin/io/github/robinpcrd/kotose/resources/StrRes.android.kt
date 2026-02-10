package io.github.robinpcrd.kotose.resources

import android.content.Context
import android.content.res.Resources
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

fun StrRes.getString(context: Context): String? = getString(context.resources)

fun StrRes.getString(resources: Resources): String? {
    if (platformStrRes != null) {
        val resolvedArgs = platformStrRes.formatArgs.map {
            when (it) {
                is StrRes -> it.getString(resources).orEmpty()
                else -> it
            }
        }
        return when {
            platformStrRes.pluralRes != null && resolvedArgs.isEmpty() -> resources.getQuantityString(
                platformStrRes.pluralRes,
                platformStrRes.quantity ?: 0
            )

            platformStrRes.pluralRes != null -> resources.getQuantityString(
                platformStrRes.pluralRes,
                platformStrRes.quantity ?: 0,
                *resolvedArgs.toTypedArray()
            )

            platformStrRes.stringRes != null && resolvedArgs.isEmpty() -> resources.getString(
                platformStrRes.stringRes
            )

            platformStrRes.stringRes != null -> resources.getString(
                platformStrRes.stringRes,
                *resolvedArgs.toTypedArray()
            )

            else -> null
        }
    }

    val formatArgs = formatArgs.map {
        when (it) {
            is StrRes -> it.getString(resources).orEmpty()
            else -> it
        }
    }
    return when {
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
