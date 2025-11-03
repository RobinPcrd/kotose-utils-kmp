package io.github.robinpcrd.kotoseutilskmp

import io.github.robinpcrd.kotoseutilskmp.resources.PlatformStrRes
import io.github.robinpcrd.kotoseutilskmp.resources.StrRes
import io.github.robinpcrd.kotoseutilskmp.resources.toStrRes
import kotlinx.collections.immutable.persistentListOf

actual object SamplePlatformStrRes {
    actual val SampleStrResAppName: StrRes = PlatformStrRes(
        stringRes = R.string.app_name
    ).toStrRes()
    actual val SampleStrResWithArgs: StrRes = PlatformStrRes(
        stringRes = R.string.hello_with_args,
        formatArgs = persistentListOf("World", 3)
    ).toStrRes()
}