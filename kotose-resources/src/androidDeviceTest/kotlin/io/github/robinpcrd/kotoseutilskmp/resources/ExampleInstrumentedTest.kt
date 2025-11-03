/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.google.common.truth.Truth.assertThat
import io.github.robinpcrd.kotose_resources.generated.resources.Res
import io.github.robinpcrd.kotose_resources.generated.resources.allStringResources
import io.github.robinpcrd.kotose_resources.generated.resources.test_string_0
import io.github.robinpcrd.kotoseutilskmpandroid.test.R
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val strRes0From = StrRes(
        PlatformStrRes(R.string.test_string_0_from),
        formatArgs = persistentListOf("John Doe")
    )

    @Before
    fun setup() {
        KotoseUtils.setup {
            this.stringResourceResolver { key ->
                Res.allStringResources[key]
            }
        }

        composeTestRule.setContent {
            Column {
                Text("Test")
                Box(Modifier.testTag("test-box-0")) {
                    val abc by rememberResourceAsState {
                        getString(R.string.test_string_0)
                    }
                    Text(abc)
                }
                Box(Modifier.testTag("test-box-1")) {
                    val abcd = rememberStrRes(strRes0From).orEmpty()
                    Text(abcd)
                }
            }

        }
    }

    @Test
    fun test_text() {
        //composeTestRule.waitForIdle()
        val activity = composeTestRule.activity
        val string0 =
            activity.getString(R.string.test_string_0)
        composeTestRule.onNodeWithText(string0).isDisplayed()

        val string1 = strRes0From.getString(activity)
        assertThat(string1).isNotNull()
        composeTestRule.onNodeWithText(string1!!).isDisplayed()
    }

    @Test
    fun useAppContext() {
        val appContext = composeTestRule.activity.applicationContext
        val strResHello = StrRes(
            text = "Hello, %s! I have %d apples",
            formatArgs = persistentListOf("World", 3)
        )
        assertThat(strResHello.getString(appContext)).isEqualTo("Hello, World! I have 3 apples")
        val strResName = StrRes(
            text = "Hello, my name is %s",
            formatArgs = persistentListOf(
                StrRes(
                    text = "John %s",
                    formatArgs = persistentListOf("Doe")
                )
            )
        )
        assertThat(strResName.getString(appContext)).isEqualTo("Hello, my name is John Doe")
    }

    @Test
    fun testSerialization() {
        val composeStrRes = StrRes(
            composeString = Res.string.test_string_0
        )
        val json = Json.encodeToString(composeStrRes)
        println(json)

        val composeStrRes2 = Json.decodeFromString(StrRes.serializer(), json)
        println(composeStrRes2.getString(composeTestRule.activity))

        val platformStrRes = PlatformStrRes(
            stringRes = R.string.test_string_0
        )
        val json2 = Json.encodeToString(platformStrRes)
        println(json2)

        val platformStrRes2 = Json.decodeFromString(PlatformStrRes.serializer(), json2)
        println(platformStrRes2)
    }
}