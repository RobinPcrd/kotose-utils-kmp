/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformStrResSerializerTest {

    @Test
    fun serializeAndDeserializeWithStringResOnly() {
        val original = PlatformStrRes(stringRes = 123)
        val json = Json.encodeToString(PlatformStrResSerializer, original)
        val restored = Json.decodeFromString(PlatformStrResSerializer, json)
        assertEquals(original, restored)
    }

    @Test
    fun serializeAndDeserializeWithPluralRes() {
        val original = PlatformStrRes(pluralRes = 456, quantity = 5)
        val json = Json.encodeToString(PlatformStrResSerializer, original)
        val restored = Json.decodeFromString(PlatformStrResSerializer, json)
        assertEquals(original, restored)
    }

    @Test
    fun serializeAndDeserializeWithFormatArgs() {
        val original = PlatformStrRes(
            stringRes = 789,
            formatArgs = persistentListOf("Alice", 42)
        )
        val json = Json.encodeToString(PlatformStrResSerializer, original)
        val restored = Json.decodeFromString(PlatformStrResSerializer, json)
        assertEquals(original.stringRes, restored.stringRes)
        assertEquals(original.formatArgs, restored.formatArgs)
    }

    @Test
    fun serializeAndDeserializeEmpty() {
        val original = PlatformStrRes()
        val json = Json.encodeToString(PlatformStrResSerializer, original)
        val restored = Json.decodeFromString(PlatformStrResSerializer, json)
        assertEquals(original, restored)
    }

    @Test
    fun serializeAndDeserializeFullyPopulated() {
        val original = PlatformStrRes(
            stringRes = 100,
            pluralRes = 200,
            quantity = 3,
            formatArgs = persistentListOf("test", 99)
        )
        val json = Json.encodeToString(PlatformStrResSerializer, original)
        val restored = Json.decodeFromString(PlatformStrResSerializer, json)
        assertEquals(original.stringRes, restored.stringRes)
        assertEquals(original.pluralRes, restored.pluralRes)
        assertEquals(original.quantity, restored.quantity)
        assertEquals(original.formatArgs, restored.formatArgs)
    }
}
