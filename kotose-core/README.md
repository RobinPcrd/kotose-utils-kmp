# kotose-core

Android context extensions for Compose.

## Installation

```kotlin
implementation("io.github.robinpcrd:kotose-core:<VERSION>")
```

## Usage

### Get Activity from Context

```kotlin
@Composable
fun MyScreen() {
    val context = LocalContext.current
    val activity = context.getActivity()
    // activity is AppCompatActivity? - walks up ContextWrapper chain
}
```
