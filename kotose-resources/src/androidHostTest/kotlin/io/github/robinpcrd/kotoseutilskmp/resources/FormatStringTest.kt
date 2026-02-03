/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import kotlin.test.Test
import kotlin.test.assertEquals

class FormatStringTest {

    // region Simple specifiers: %s, %d, %f

    @Test
    fun simpleString() {
        assertEquals("hello world", formatString("hello %s", listOf("world")))
    }

    @Test
    fun simpleInt() {
        assertEquals("count: 42", formatString("count: %d", listOf(42)))
    }

    @Test
    fun simpleFloat() {
        assertEquals("pi: 3.14", formatString("pi: %.2f", listOf(3.14)))
    }

    // endregion

    // region Positional specifiers: %N$s, %N$d, %N$f

    @Test
    fun positionalString() {
        assertEquals("hello World", formatString("hello %1\$s", listOf("World")))
    }

    @Test
    fun positionalInt() {
        assertEquals("count: 5", formatString("count: %1\$d", listOf(5)))
    }

    @Test
    fun positionalFloat() {
        assertEquals("pi: 3.14", formatString("pi: %1\$.2f", listOf(3.14)))
    }

    // endregion

    // region Multiple args with sequential specifiers

    @Test
    fun multipleSequentialArgs() {
        assertEquals(
            "name=Alice age=30",
            formatString("name=%s age=%d", listOf("Alice", 30))
        )
    }

    @Test
    fun adjacentSequentialPlaceholders() {
        assertEquals("AB", formatString("%s%s", listOf("A", "B")))
    }

    @Test
    fun mixedSimpleSpecifierTypes() {
        assertEquals(
            "str=hello int=42 float=3.14",
            formatString("str=%s int=%d float=%.2f", listOf("hello", 42, 3.14))
        )
    }

    // endregion

    // region Multiple args with positional specifiers

    @Test
    fun multiplePositionalArgs() {
        assertEquals(
            "Hello World, you have 3 new messages",
            formatString("Hello %1\$s, you have %2\$d new messages", listOf("World", 3))
        )
    }

    @Test
    fun positionalReversedOrder() {
        assertEquals("second first", formatString("%2\$s %1\$s", listOf("first", "second")))
    }

    @Test
    fun positionalArgReused() {
        assertEquals("A and A", formatString("%1\$s and %1\$s", listOf("A")))
    }

    @Test
    fun positionalMixedSpecifierTypes() {
        assertEquals(
            "str=hello int=42 float=3.14",
            formatString("str=%1\$s int=%2\$d float=%3\$.2f", listOf("hello", 42, 3.14))
        )
    }

    // endregion

    // region Edge cases: no args, no placeholders, empty string

    @Test
    fun noArgs() {
        assertEquals("plain text", formatString("plain text", emptyList()))
    }

    @Test
    fun emptyFormatString() {
        assertEquals("", formatString("", emptyList()))
    }

    @Test
    fun specifierAtStart() {
        assertEquals("hello world", formatString("%s world", listOf("hello")))
    }

    @Test
    fun specifierAtEnd() {
        assertEquals("hello world", formatString("hello %s", listOf("world")))
    }

    @Test
    fun onlySpecifier() {
        assertEquals("42", formatString("%d", listOf(42)))
    }

    @Test
    fun percentLiteral() {
        assertEquals("100%", formatString("100%%", emptyList()))
    }

    @Test
    fun textAroundSpecifiers() {
        assertEquals(
            "<<hello>> and <<42>>",
            formatString("<<%s>> and <<%d>>", listOf("hello", 42))
        )
    }

    // endregion
}
