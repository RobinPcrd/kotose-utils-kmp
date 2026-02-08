/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp

import android.app.Application
import io.github.robinpcrd.kotoseutilskmp.resources.androidContext
import kotose_utils_kmp.example.composeapp.generated.resources.Res
import kotose_utils_kmp.example.composeapp.generated.resources.allPluralStringResources
import kotose_utils_kmp.example.composeapp.generated.resources.allStringResources

class KotoseUtilsExampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKotoseUtils {
            androidContext(this@KotoseUtilsExampleApp)
            stringResourceResolver { key ->
                Res.allStringResources[key]
            }
            pluralStringResourceResolver { key ->
                Res.allPluralStringResources[key]
            }
        }
    }
}
