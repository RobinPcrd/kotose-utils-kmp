/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.robinpcrd.kotoseutilskmp.resources.StrRes
import kotlinx.collections.immutable.persistentListOf
import kotose_utils_kmp.example.composeapp.generated.resources.Res
import kotose_utils_kmp.example.composeapp.generated.resources.compose_multiplatform
import kotose_utils_kmp.example.composeapp.generated.resources.hello
import kotose_utils_kmp.example.composeapp.generated.resources.hello_format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val SampleStrRes = StrRes(
    composeString = Res.string.hello,
)

private val SampleStrRes2 = StrRes(
    text = "Hello, this is a text",
)

private val SampleStrRes3 = StrRes(
    composeString = Res.string.hello_format,
    formatArgs = persistentListOf("World", 3)
)

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painterResource(Res.drawable.compose_multiplatform),
                        null,
                        modifier = Modifier.size(64.dp)
                    )
                    Text("Compose: $greeting")
                    Text(SampleStrRes.getStringOrEmpty())
                    Text(SampleStrRes2.getStringOrEmpty())
                    Text(SampleStrRes3.getStringOrEmpty())
                    Text(SamplePlatformStrRes.SampleStrResWithArgs.getStringOrEmpty())
                }
            }
        }
    }
}