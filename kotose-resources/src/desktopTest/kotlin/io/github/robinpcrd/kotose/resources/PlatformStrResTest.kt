/*
* Copyright 2026-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotose.resources

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PlatformStrResTest {

    @Test
    fun resolveSimpleKey() = runBlocking {
        val res = PlatformStrRes(key = "greeting")
        assertEquals("Hello", res.getStringSuspend())
    }

    @Test
    fun resolveMissingKeyReturnsNull() = runBlocking {
        val res = PlatformStrRes(key = "nonexistent_key")
        assertNull(res.getStringSuspend())
    }

    @Test
    fun resolveWithStringFormatArg() = runBlocking {
        val res = PlatformStrRes(
            key = "welcome",
            formatArgs = persistentListOf("Alice")
        )
        assertEquals("Welcome, Alice!", res.getStringSuspend())
    }

    @Test
    fun resolveWithIntFormatArg() = runBlocking {
        val res = PlatformStrRes(
            key = "item_count",
            formatArgs = persistentListOf(5)
        )
        assertEquals("5 items found", res.getStringSuspend())
    }

    @Test
    fun resolveWithMultiplePositionalArgs() = runBlocking {
        val res = PlatformStrRes(
            key = "formatted",
            formatArgs = persistentListOf("Bob", 3)
        )
        assertEquals("Hello Bob, you have 3 messages", res.getStringSuspend())
    }

    @Test
    fun resolveEmptyKeyReturnsNull() = runBlocking {
        val res = PlatformStrRes()
        assertNull(res.getStringSuspend())
    }

    @Test
    fun defaultFormatArgsIsEmpty() {
        val res = PlatformStrRes(key = "greeting")
        assertEquals(persistentListOf<Any>(), res.formatArgs)
    }

    @Test
    fun dataClassEquality() {
        val a = PlatformStrRes(key = "greeting", formatArgs = persistentListOf("x"))
        val b = PlatformStrRes(key = "greeting", formatArgs = persistentListOf("x"))
        assertEquals(a, b)
    }
}
