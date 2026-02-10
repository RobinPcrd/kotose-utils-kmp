/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotose.resources

import kotlin.test.Test
import kotlin.test.assertEquals

class FormatStringTest {

    // region Simple specifiers: %s, %d, %f, %@, %ld, %lld, %lf, %llf

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
        assertEquals("pi: 3.14", formatString("pi: %f", listOf(3.14)))
    }

    @Test
    fun simpleObjcObject() {
        assertEquals("hello World", formatString("hello %@", listOf("World")))
    }

    @Test
    fun simpleLongInt() {
        assertEquals("val: 99", formatString("val: %ld", listOf(99)))
    }

    @Test
    fun simpleLongLongInt() {
        assertEquals("count: 42", formatString("count: %lld", listOf(42)))
    }

    @Test
    fun simpleLongFloat() {
        assertEquals("val: 1.5", formatString("val: %lf", listOf(1.5)))
    }

    @Test
    fun simpleLongLongFloat() {
        assertEquals("val: 2.7", formatString("val: %llf", listOf(2.7)))
    }

    // endregion

    // region Positional specifiers: %N$s, %N$d, %N$f, %N$@, %N$ld, %N$lld, %N$lf, %N$llf

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
        assertEquals("pi: 3.14", formatString("pi: %1\$f", listOf(3.14)))
    }

    @Test
    fun positionalObjcObject() {
        assertEquals("hello World", formatString("hello %1\$@", listOf("World")))
    }

    @Test
    fun positionalLongInt() {
        assertEquals("val: 99", formatString("val: %1\$ld", listOf(99)))
    }

    @Test
    fun positionalLongLongInt() {
        assertEquals("count: 42", formatString("count: %1\$lld", listOf(42)))
    }

    @Test
    fun positionalLongFloat() {
        assertEquals("val: 1.5", formatString("val: %1\$lf", listOf(1.5)))
    }

    @Test
    fun positionalLongLongFloat() {
        assertEquals("val: 2.7", formatString("val: %1\$llf", listOf(2.7)))
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
            formatString("str=%s int=%d float=%f", listOf("hello", 42, 3.14))
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
            formatString("str=%1\$s int=%2\$d float=%3\$f", listOf("hello", 42, 3.14))
        )
    }

    // endregion

    // region iOS real-world patterns

    @Test
    fun iosRealWorldObjectAndLongLong() {
        assertEquals(
            "Hello World, you have 3 new messages",
            formatString("Hello %1\$@, you have %2\$lld new messages", listOf("World", 3))
        )
    }

    @Test
    fun iosRealWorldMixedObjcSpecifiers() {
        assertEquals(
            "User Alice scored 95 with avg 87.5",
            formatString("User %1\$@ scored %2\$lld with avg %3\$lf", listOf("Alice", 95, 87.5))
        )
    }

    // endregion

    // region Edge cases: no args, no placeholders, bounds, empty string

    @Test
    fun noArgs() {
        assertEquals("plain text", formatString("plain text", emptyList()))
    }

    @Test
    fun noPlaceholders() {
        assertEquals("no placeholders", formatString("no placeholders", listOf("unused")))
    }

    @Test
    fun emptyFormatString() {
        assertEquals("", formatString("", emptyList()))
    }

    @Test
    fun emptyFormatStringWithArgs() {
        assertEquals("", formatString("", listOf("unused")))
    }

    @Test
    fun morePlaceholdersThanArgs() {
        assertEquals("first then %s", formatString("%s then %s", listOf("first")))
    }

    @Test
    fun positionalOutOfBoundsFallback() {
        assertEquals("ok %3\$s", formatString("ok %3\$s", listOf("a", "b")))
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
    fun percentLiteralNotMatched() {
        // %% is not a format specifier - should pass through unchanged
        assertEquals("100%%", formatString("100%%", emptyList()))
    }

    @Test
    fun unknownSpecifierNotMatched() {
        // %c, %x, %o etc. are not in our regex - should pass through unchanged
        assertEquals("char: %c hex: %x", formatString("char: %c hex: %x", listOf("a", 255)))
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
