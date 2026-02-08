/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(InternalResourceApi::class)
class StringResourceSerializerTest {

    private val json = Json

    @OptIn(InternalKotoseUtilsApi::class)
    @BeforeTest
    fun setUp() {
        KotoseUtils.reset()
    }

    // region StringResourceSerializer

    @Test
    fun serializeStringResource() {
        val resource = StringResource(
            id = "test_id",
            key = "test_key",
            items = emptySet()
        )
        val encoded = json.encodeToString(StringResourceSerializer, resource)
        assertEquals("\"test_key\"", encoded)
    }

    @Test
    fun deserializeStringResource() {
        val expectedResource = StringResource(
            id = "test_id",
            key = "test_key",
            items = emptySet()
        )
        KotoseUtils.setup {
            stringResourceResolver { key ->
                if (key == "test_key") expectedResource else null
            }
        }

        val decoded = json.decodeFromString(StringResourceSerializer, "\"test_key\"")
        assertEquals(expectedResource, decoded)
    }

    @Test
    fun deserializeStringResourceNotFound() {
        KotoseUtils.setup {
            stringResourceResolver { null }
        }

        assertFailsWith<IllegalStateException> {
            json.decodeFromString(StringResourceSerializer, "\"unknown_key\"")
        }
    }

    @Test
    fun roundTripStringResource() {
        val resource = StringResource(
            id = "round_trip_id",
            key = "round_trip_key",
            items = emptySet()
        )
        KotoseUtils.setup {
            stringResourceResolver { key ->
                if (key == "round_trip_key") resource else null
            }
        }

        val encoded = json.encodeToString(StringResourceSerializer, resource)
        val decoded = json.decodeFromString(StringResourceSerializer, encoded)
        assertEquals(resource, decoded)
    }

    // endregion

    // region PluralStringResourceSerializer

    @Test
    fun serializePluralStringResource() {
        val resource = PluralStringResource(
            id = "plural_id",
            key = "plural_key",
            items = emptySet()
        )
        val encoded = json.encodeToString(PluralStringResourceSerializer, resource)
        assertEquals("\"plural_key\"", encoded)
    }

    @Test
    fun deserializePluralStringResource() {
        val expectedResource = PluralStringResource(
            id = "plural_id",
            key = "plural_key",
            items = emptySet()
        )
        KotoseUtils.setup {
            pluralStringResourceResolver { key ->
                if (key == "plural_key") expectedResource else null
            }
        }

        val decoded = json.decodeFromString(PluralStringResourceSerializer, "\"plural_key\"")
        assertEquals(expectedResource, decoded)
    }

    @Test
    fun deserializePluralStringResourceNotFound() {
        KotoseUtils.setup {
            pluralStringResourceResolver { null }
        }

        assertFailsWith<IllegalStateException> {
            json.decodeFromString(PluralStringResourceSerializer, "\"unknown_key\"")
        }
    }

    @Test
    fun roundTripPluralStringResource() {
        val resource = PluralStringResource(
            id = "plural_round_trip_id",
            key = "plural_round_trip_key",
            items = emptySet()
        )
        KotoseUtils.setup {
            pluralStringResourceResolver { key ->
                if (key == "plural_round_trip_key") resource else null
            }
        }

        val encoded = json.encodeToString(PluralStringResourceSerializer, resource)
        val decoded = json.decodeFromString(PluralStringResourceSerializer, encoded)
        assertEquals(resource, decoded)
    }

    // endregion

    // region Not-initialized errors

    @Test
    fun deserializeStringResourceWithoutSetupThrows() {
        // Setup not called - resolvers should throw meaningful error
        assertFailsWith<IllegalStateException> {
            json.decodeFromString(StringResourceSerializer, "\"any_key\"")
        }
    }

    @Test
    fun deserializePluralStringResourceWithoutSetupThrows() {
        assertFailsWith<IllegalStateException> {
            json.decodeFromString(PluralStringResourceSerializer, "\"any_key\"")
        }
    }

    // endregion
}
