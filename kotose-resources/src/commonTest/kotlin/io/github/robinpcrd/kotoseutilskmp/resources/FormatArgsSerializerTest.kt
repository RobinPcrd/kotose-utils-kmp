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
}
