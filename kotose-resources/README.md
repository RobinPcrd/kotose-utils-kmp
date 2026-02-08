# kotose-resources

Serializable resource management for Compose Multiplatform (Android + iOS).

## Installation

```kotlin
implementation("io.github.robinpcrd:kotose-resources:<VERSION>")
```

## Setup

Initialize before using resource features:

```kotlin
KotoseUtils.setup {
    androidContext(context) // Android only - enables getStringSuspend() for PlatformStrRes
    stringResourceResolver { key ->
        Res.allStringResources[key]
    }
    pluralStringResourceResolver { key ->
        Res.allPluralStringResources[key]
    }
}
```

**What each option does:**

- `androidContext(context)` - Android only. Required for resolving `PlatformStrRes` outside of
  composables (via `getStringSuspend()`).
- `stringResourceResolver` / `pluralStringResourceResolver` - Needed only if you *
  *serialize/deserialize `StrRes`** objects that wrap Compose resources (`Res.string.*`,
  `Res.plurals.*`). When a `StrRes` is serialized to JSON, only the resource **key** (e.g.
  `"app_name"`) is stored. On deserialization, the resolver maps that key back to a
  `StringResource`/`PluralStringResource` object. If you don't serialize `StrRes`, you can skip
  these.

**iOS example** - if you only use `PlatformStrRes` (which resolves via `NSBundle` directly), the
config block can be empty:

```swift
// Swift
AppInitKt.doInitKotoseUtils { config in
}
```

If you do serialize `StrRes` with Compose resources on iOS, provide the resolvers:

```swift
AppInitKt.doInitKotoseUtils { config in
    config.stringResourceResolver { key in
        return Res.shared.allStringResources[key]
    }
    config.pluralStringResourceResolver { key in
        return Res.shared.allPluralStringResources[key]
    }
}
```

## Usage

### Creating StrRes

```kotlin
// Plain text
val text = StrRes(text = "Hello!")
val text2 = strResOf("Hello")
val text3 = "Hello".toStrRes()

// Compose Multiplatform resources
val compose = StrRes(composeString = Res.string.app_name)
val compose2 = Res.string.app_name.toStrRes()

// Compose plural resources
val plural = StrRes(composePluralString = Res.plurals.items_count, quantity = 5)
val plural2 = Res.plurals.items_count.toStrRes(quantity = 5)

// With format arguments
val formatted = StrRes(
    text = "Hello, %s! You have %d messages.",
    formatArgs = persistentListOf("Alice", 5)
)

// Nested StrRes as format argument
val nested = StrRes(
    text = "Welcome, %s!",
    formatArgs = persistentListOf(StrRes(composeString = Res.string.username))
)

// Empty
val empty = StrRes.Empty
```

### Android-only: PlatformStrRes

```kotlin
// String resource
val str = R.string.app_name.toStrRes()
val str2 = R.string.greeting.toStrRes(formatArgs = persistentListOf("Alice"))

// Plural resource
val plural = R.plurals.items.toStrRes(quantity = 3)
val plural2 = R.plurals.messages.toStrRes(
    quantity = 5,
    formatArgs = persistentListOf(5)
)

// Direct PlatformStrRes
val platform = R.string.hello.toPlatformStrRes()
val platformPlural = R.plurals.items.toPlatformStrRes(quantity = 2)
```

### iOS-only: PlatformStrRes

```kotlin
// Localization key (from Localizable.strings)
val ios = PlatformStrRes(key = "greeting_key")
val iosFormatted = PlatformStrRes(
    key = "welcome_message",
    formatArgs = persistentListOf("Alice")
)
```

Resolves via `NSBundle.localizedStringForKey`. Returns `null` if key not found.

Supports both Java-style (`%1$s`, `%d`, `%.2f`) and iOS-style (`%@`, `%lld`, `%lf`) format
specifiers.

### Android-only: PainterRes

Wraps different painter types into a single composable-friendly wrapper:

```kotlin
// From drawable resource
val icon = PainterRes(res = R.drawable.ic_star)

// From ImageVector
val vector = PainterRes(vector = Icons.Default.Star)

// From ImageBitmap
val bitmap = PainterRes(bitmap = myBitmap)

// From Painter
val custom = PainterRes(painter = myPainter)

// Resolve in composable
@Composable
fun Icon(painterRes: PainterRes) {
    painterRes.getPainter()?.let { Image(painter = it, contentDescription = null) }
}
```

### Resolving in Composables

```kotlin
@Composable
fun Greeting(strRes: StrRes) {
    val text = strRes.getString() ?: ""
    Text(text)
}

// Or with empty default
@Composable
fun Greeting(strRes: StrRes) {
    Text(strRes.getStringOrEmpty())
}
```

### Android: rememberStrRes / rememberResource

Cache-friendly composable helpers that recompose on configuration changes (locale, etc.):

```kotlin
// Resolve StrRes with resource-aware caching
@Composable
fun Greeting(strRes: StrRes) {
    val text = rememberStrRes(strRes) // String?
    // or as State
    val textState = rememberStrResAsState(strRes) // State<String?>
}

// Generic Android Resources access
@Composable
fun Label() {
    val name = rememberResource { getString(R.string.app_name) }
    val nameState = rememberResourceAsState { getString(R.string.app_name) }
}
```

### Non-composable resolution (suspend)

Works cross-platform for compose resources, text, and `PlatformStrRes`.
On Android, `PlatformStrRes` resolution requires `androidContext()` in setup.

```kotlin
suspend fun loadGreeting(strRes: StrRes): String {
    return strRes.getStringSuspendOrEmpty()
}
```

### Android: Non-composable resolution with Context

Alternatively, resolve Android `PlatformStrRes` directly with a Context:

```kotlin
// With Context
val text = strRes.getString(context)

// With Resources
val text = strRes.getString(resources)
```

### Serialization

`StrRes` is fully serializable (JSON only):

```kotlin
val json = Json.encodeToString(StrRes.serializer(), strRes)
val restored = Json.decodeFromString(StrRes.serializer(), json)
```

Supported format argument types: `String`, `Int`, `Long`, `Float`, `Double`, `Boolean`, `StrRes`,
`PlatformStrRes`.
`FormatArgsSerializer` requires `JsonEncoder`/`JsonDecoder` - other formats are not supported.

## Platform Notes

| Platform | PlatformStrRes behavior                                               |
|----------|-----------------------------------------------------------------------|
| Android  | Wraps `@StringRes`/`@PluralsRes` IDs, resolves via Android resources  |
| iOS      | Wraps localization key, resolves via `NSBundle.localizedStringForKey` |

Cross-platform: prefer `StrRes` with `composeString`/`composePluralString` or `text`.
