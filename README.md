# Kotose Utils KMP

Kotlin Multiplatform utilities for Compose Multiplatform (Android + iOS).

## Modules

| Description                                                   | Module                                         |
|---------------------------------------------------------------|------------------------------------------------|
| Serializable resource management (`StrRes`, `PlatformStrRes`) | [kotose-resources](kotose-resources/README.md) | 
| Android context extensions                                    | [kotose-core](kotose-core/README.md)           |

## Installation

```kotlin
// Resources (StrRes, PlatformStrRes, serialization)
implementation("io.github.robinpcrd:kotose-resources:<VERSION>")

// Android context extensions
implementation("io.github.robinpcrd:kotose-core:<VERSION>")
```

## Platforms

- **Android** SDK 24+
- **iOS** arm64, x64, simulatorArm64

## Quick Start

```kotlin
// Setup (once, at app init)
KotoseUtils.setup {
    androidContext(context) // Android only - enables getStringSuspend()
    stringResourceResolver { key -> Res.allStringResources[key] }
    pluralStringResourceResolver { key -> Res.allPluralStringResources[key] }
}

// Create resources
val text = "Hello".toStrRes()
val compose = Res.string.app_name.toStrRes()
val android = R.string.greeting.toStrRes()  // Android only

// Resolve in Composable
@Composable
fun Greeting(strRes: StrRes) {
    Text(strRes.getStringOrEmpty())
}
```

See module READMEs for detailed usage.

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for build, test, and contribution guidelines.

## License

```
Copyright 2025-present Robin PICARD and contributors.
Licensed under the Apache License, Version 2.0.
```

