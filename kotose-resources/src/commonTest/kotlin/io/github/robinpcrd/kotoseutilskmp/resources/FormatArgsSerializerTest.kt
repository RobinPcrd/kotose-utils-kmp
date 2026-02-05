/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class FormatArgsSerializerTest {

    private val json = Json

    @Test
    fun serializeAndDeserializeStringArg() {
        val args = persistentListOf<Any>("hello")
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(args, decoded)
    }

    @Test
    fun serializeAndDeserializeIntArg() {
        val args = persistentListOf<Any>(42)
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(args, decoded)
    }

    @Test
    fun serializeAndDeserializeMixedArgs() {
        val args = persistentListOf<Any>("world", 7)
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(args, decoded)
    }

    @Test
    fun serializeAndDeserializeNestedStrResArg() {
        val nested = StrRes(text = "nested")
        val args = persistentListOf<Any>("before", nested, 99)
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(3, decoded.size)
        assertEquals("before", decoded[0])
        assertEquals(nested, decoded[1])
        assertEquals(99, decoded[2])
    }

    @Test
    fun serializeAndDeserializeEmptyList() {
        val args = persistentListOf<Any>()
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(args, decoded)
    }

    @Test
    fun serializeAndDeserializePlatformStrResArg() {
        val platform = PlatformStrRes()
        val args = persistentListOf<Any>(platform)
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(1, decoded.size)
    }

    @Test
    fun serializeAndDeserializeLongArg() {
        val args = persistentListOf<Any>(9223372036854775807L)
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(args, decoded)
    }

    @Test
    fun serializeAndDeserializeFloatArg() {
        val args = persistentListOf<Any>(3.14f)
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(args, decoded)
    }

    @Test
    fun serializeAndDeserializeDoubleArg() {
        val args = persistentListOf<Any>(2.718281828459045)
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(args, decoded)
    }

    @Test
    fun serializeAndDeserializeBooleanArg() {
        val args = persistentListOf<Any>(true, false)
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(args, decoded)
    }

    @Test
    fun serializeAndDeserializeAllPrimitiveTypes() {
        val args = persistentListOf<Any>(
            "text",
            42,
            123456789012345L,
            1.5f,
            3.14159265358979,
            true
        )
        val encoded = json.encodeToString(FormatArgsSerializer, args)
        val decoded = json.decodeFromString(FormatArgsSerializer, encoded)
        assertEquals(args, decoded)
    }
}
