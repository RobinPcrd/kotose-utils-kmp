/*
* Copyright 2026-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotose.resources

import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformStrResSerializerTest {

    @Test
    fun serializeAndDeserializeWithKeyOnly() {
        val original = PlatformStrRes(key = "greeting_key")
        val json = Json.encodeToString(PlatformStrResSerializer, original)
        val restored = Json.decodeFromString(PlatformStrResSerializer, json)
        assertEquals(original, restored)
    }

    @Test
    fun serializeAndDeserializeWithFormatArgs() {
        val original = PlatformStrRes(
            key = "welcome_message",
            formatArgs = persistentListOf("Alice", 42)
        )
        val json = Json.encodeToString(PlatformStrResSerializer, original)
        val restored = Json.decodeFromString(PlatformStrResSerializer, json)
        assertEquals(original.key, restored.key)
        assertEquals(original.formatArgs, restored.formatArgs)
    }

    @Test
    fun serializeAndDeserializeEmpty() {
        val original = PlatformStrRes()
        val json = Json.encodeToString(PlatformStrResSerializer, original)
        val restored = Json.decodeFromString(PlatformStrResSerializer, json)
        assertEquals(original, restored)
    }
}
