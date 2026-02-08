/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KotoseUtilsSetupTest {

    @OptIn(InternalKotoseUtilsApi::class)
    @AfterTest
    fun tearDown() {
        KotoseUtils.reset()
    }

    @Test
    fun isNotInitializedByDefault() {
        assertFalse(KotoseUtils.isInitialized())
    }

    @Test
    fun setupMarksInitialized() {
        KotoseUtils.setup {
            stringResourceResolver { null }
        }
        assertTrue(KotoseUtils.isInitialized())
    }

    @Test
    fun setupIsIdempotent() {
        var callCount = 0
        KotoseUtils.setup {
            callCount++
            stringResourceResolver { null }
        }
        KotoseUtils.setup {
            callCount++
            stringResourceResolver { null }
        }
        // Second call should be ignored (early return)
        assertTrue(KotoseUtils.isInitialized())
        assertEquals(1, callCount)
    }

    @OptIn(InternalKotoseUtilsApi::class)
    @Test
    fun resetClearsInitialization() {
        KotoseUtils.setup {
            stringResourceResolver { null }
        }
        assertTrue(KotoseUtils.isInitialized())
        KotoseUtils.reset()
        assertFalse(KotoseUtils.isInitialized())
    }

    @OptIn(InternalKotoseUtilsApi::class)
    @Test
    fun canSetupAgainAfterReset() {
        KotoseUtils.setup {
            stringResourceResolver { null }
        }
        KotoseUtils.reset()
        KotoseUtils.setup {
            stringResourceResolver { null }
        }
        assertTrue(KotoseUtils.isInitialized())
    }

    @OptIn(InternalKotoseUtilsApi::class)
    @Test
    fun resolversErrorAfterReset() {
        KotoseUtils.setup {
            stringResourceResolver { null }
        }
        KotoseUtils.reset()
        assertFailsWith<IllegalStateException> {
            Setup.getStringResource("any_key")
        }
        assertFailsWith<IllegalStateException> {
            Setup.getPluralStringResource("any_key")
        }
    }
}
