/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotose.resources

import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class StrResTest {

    @Test
    fun textConstructorSetsText() {
        val res = StrRes(text = "hello")
        assertEquals("hello", res.text)
        assertNull(res.platformStrRes)
        assertNull(res.composeString)
        assertNull(res.composePluralString)
        assertNull(res.quantity)
        assertEquals(persistentListOf(), res.formatArgs)
    }

    @Test
    fun platformStrResConstructor() {
        val platform = PlatformStrRes()
        val res = StrRes(platformStrRes = platform)
        assertEquals(platform, res.platformStrRes)
        assertNull(res.text)
        assertNull(res.composeString)
    }

    @Test
    fun emptyIsEmptyString() {
        assertEquals("", StrRes.Empty.text)
    }

    @Test
    fun strResOfCreatesTextStrRes() {
        val res = strResOf("test")
        assertEquals("test", res.text)
    }

    @Test
    fun toStrResExtension() {
        val res = "value".toStrRes()
        assertEquals("value", res.text)
    }

    @Test
    fun platformStrResToStrRes() {
        val platform = PlatformStrRes()
        val res = platform.toStrRes()
        assertEquals(platform, res.platformStrRes)
    }

    @Test
    fun equalityByText() {
        val a = StrRes(text = "same")
        val b = StrRes(text = "same")
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun inequalityByText() {
        val a = StrRes(text = "one")
        val b = StrRes(text = "two")
        assertNotEquals(a, b)
    }

    @Test
    fun equalityReferenceIdentity() {
        val a = StrRes(text = "x")
        assertEquals(a, a)
    }

    @Test
    fun inequalityWithNull() {
        val a = StrRes(text = "x")
        assertNotEquals<Any?>(a, null)
    }

    @Test
    fun serializeAndDeserializeTextStrRes() {
        val original = StrRes(text = "serializable")
        val json = Json.encodeToString(StrRes.serializer(), original)
        val restored = Json.decodeFromString(StrRes.serializer(), json)
        assertEquals(original, restored)
    }

    @Test
    fun serializeAndDeserializeEmptyStrRes() {
        val original = StrRes.Empty
        val json = Json.encodeToString(StrRes.serializer(), original)
        val restored = Json.decodeFromString(StrRes.serializer(), json)
        assertEquals(original, restored)
    }

    @Test
    fun serializeAndDeserializeWithFormatArgs() {
        // StrRes with text and format args (text + string arg)
        val original = StrRes(
            text = "Hello %s",
            formatArgs = persistentListOf("World")
        )
        val json = Json.encodeToString(StrRes.serializer(), original)
        val restored = Json.decodeFromString(StrRes.serializer(), json)
        assertEquals(original.text, restored.text)
        assertEquals(original.formatArgs, restored.formatArgs)
    }

    @Test
    fun serializeAndDeserializeWithPlatformStrRes() {
        val platform = PlatformStrRes()
        val original = StrRes(platformStrRes = platform)
        val json = Json.encodeToString(StrRes.serializer(), original)
        val restored = Json.decodeFromString(StrRes.serializer(), json)
        assertEquals(original, restored)
    }

    @Test
    fun serializeAndDeserializeWithNestedStrResInFormatArgs() {
        val nested = StrRes(text = "inner")
        val original = StrRes(
            text = "Hello %s",
            formatArgs = persistentListOf(nested)
        )
        val json = Json.encodeToString(StrRes.serializer(), original)
        val restored = Json.decodeFromString(StrRes.serializer(), json)
        assertEquals(original.text, restored.text)
        assertEquals(original.formatArgs, restored.formatArgs)
    }

    @Test
    fun inequalityByFormatArgs() {
        val a = StrRes(
            text = "Hello %s",
            formatArgs = persistentListOf("Alice")
        )
        val b = StrRes(
            text = "Hello %s",
            formatArgs = persistentListOf("Bob")
        )
        assertNotEquals(a, b)
    }

    @Test
    fun inequalityByQuantity() {
        val a = StrRes(
            composePluralString = null,
            quantity = 1,
            text = "item"
        )
        val b = StrRes(
            composePluralString = null,
            quantity = 5,
            text = "item"
        )
        assertNotEquals(a, b)
    }
}
