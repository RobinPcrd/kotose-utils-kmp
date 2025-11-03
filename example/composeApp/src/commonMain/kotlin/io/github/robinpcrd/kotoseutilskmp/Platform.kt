package io.github.robinpcrd.kotoseutilskmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform