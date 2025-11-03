package io.github.robinpcrd.kotoseutilskmp

import android.app.Application
import kotose_utils_kmp.example.composeapp.generated.resources.Res
import kotose_utils_kmp.example.composeapp.generated.resources.allStringResources

class KotoseUtilsExampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKotoseUtils {
            stringResourceResolver { key ->
                Res.allStringResources[key]
            }
        }
    }
}