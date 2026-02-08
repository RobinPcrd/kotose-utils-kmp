# Contributing

Contributions are welcome! Here's how to get started.

## Submitting changes

1. Fork the repository and create a feature branch from `main`.
2. Make your changes and ensure tests pass.
3. Submit a Pull Request with a clear description of the change.

## Building

```bash
./gradlew build
```

## Running tests

```bash
# All tests (requires Android SDK for device tests)
./gradlew test

# Common tests only (runs on all targets without a device)
./gradlew :kotose-resources:allTests
```

## License

By contributing, you agree that your contributions will be licensed under
the [Apache License 2.0](LICENSE).
