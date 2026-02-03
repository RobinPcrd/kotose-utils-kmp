/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import kotlin.concurrent.Volatile

internal object Setup {
    @Volatile
    var getStringResource: (String) -> StringResource? = {
        error("KotoseUtils.setup() must be called before using string resources")
    }

    @Volatile
    var getPluralStringResource: (String) -> PluralStringResource? = {
        error("KotoseUtils.setup() must be called before using plural string resources")
    }
    //var getStringArrayResource: (String) -> StringArrayResource? = {
    //    error("KotoseUtils.setup() must be called before using string array resources")
    //}

    @Volatile
    internal var isInitialized = false
        private set

    internal fun markInitialized() {
        isInitialized = true
    }

    internal fun reset() {
        isInitialized = false
        getStringResource = {
            error("KotoseUtils.setup() must be called before using string resources")
        }
        getPluralStringResource = {
            error("KotoseUtils.setup() must be called before using plural string resources")
        }
        //getStringArrayResource = {
        //    error("KotoseUtils.setup() must be called before using string array resources")
        //}
    }
}

class KotoseUtilsConfig internal constructor() {

    /**
     * Configure how to resolve StringResource from a string key.
     *
     * Example:
     * ```
     * stringResourceResolver { key ->
     *     when (key) {
     *         "app_name" -> Res.string.app_name
     *         "welcome" -> Res.string.welcome
     *         else -> error("Unknown string resource: $key")
     *     }
     * }
     * ```
     */
    fun stringResourceResolver(resolver: (String) -> StringResource?) {
        Setup.getStringResource = resolver
    }

    /**
     * Configure how to resolve PluralStringResource from a string key.
     *
     * Example:
     * ```
     * pluralStringResourceResolver { key ->
     *     when (key) {
     *         "items_count" -> Res.plurals.items_count
     *         else -> error("Unknown plural resource: $key")
     *     }
     * }
     * ```
     */
    fun pluralStringResourceResolver(resolver: (String) -> PluralStringResource?) {
        Setup.getPluralStringResource = resolver
    }

    /**
     * Configure how to resolve StringArrayResource from a string key.
     *
     * Example:
     * ```
     * stringArrayResourceResolver { key ->
     *     when (key) {
     *         "countries" -> Res.array.countries
     *         else -> error("Unknown array resource: $key")
     *     }
     * }
     * ```
     */
    //fun stringArrayResourceResolver(resolver: (String) -> StringArrayResource?) {
    //    Setup.getStringArrayResource = resolver
    //}
}

object KotoseUtils {

    /**
     * Initialize KotoseUtils library. Must be called before using any library features.
     * This method is idempotent — subsequent calls are no-ops.
     *
     * Typical usage in your Application class or main function:
     * ```
     * KotoseUtils.setup {
     *     stringResourceResolver { key ->
     *         when (key) {
     *             "app_name" -> Res.string.app_name
     *             "welcome" -> Res.string.welcome
     *             else -> error("Unknown string resource: $key")
     *         }
     *     }
     *
     *     pluralStringResourceResolver { key ->
     *         when (key) {
     *             "items_count" -> Res.plurals.items_count
     *             else -> error("Unknown plural resource: $key")
     *         }
     *     }
     * }
     * ```
     *
     * @param config Configuration block for setting up resource resolvers
     */
    fun setup(config: KotoseUtilsConfig.() -> Unit) {
        if (Setup.isInitialized)
            return
        //check(!Setup.isInitialized) {
        //    "KotoseUtils.setup() has already been called. Setup should only be called once."
        //}

        val configBuilder = KotoseUtilsConfig()
        configBuilder.config()

        Setup.markInitialized()
    }

    /**
     * Check if KotoseUtils has been properly initialized.
     */
    fun isInitialized(): Boolean = Setup.isInitialized

    /**
     * Reset the initialization state. Useful for testing.
     * @suppress Internal use only
     */
    @InternalKotoseUtilsApi
    fun reset() {
        Setup.reset()
    }
}

/**
 * Marks internal API that should not be used by library consumers.
 */
@RequiresOptIn(
    message = "This is an internal KotoseUtils API and should not be used outside the library.",
)
annotation class InternalKotoseUtilsApi
