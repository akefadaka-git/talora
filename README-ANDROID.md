# TALORA Android — offline test foundation

This branch introduces a first Android implementation slice using Kotlin, Jetpack Compose and a local SQLite project store.

## Current test-build scope

- Amharic-first / English-switchable application shell.
- Create and reopen local writing projects.
- Local script text editing with debounced autosave.
- SQLite persistence on the device; no network permission or remote content transfer.
- Source-text preservation test for Ethiopic and mixed-script text.
- GitHub Actions workflow that runs JVM tests and packages an installable debug APK.

## Build

Requirements: JDK 17, Android SDK Platform 35 and Gradle 8.9.

```bash
gradle -p android testDebugUnitTest assembleDebug
```

APK output:

`android/app/build/outputs/apk/debug/app-debug.apk`

The debug application ID is `com.talora.app.debug`.

## Important limitations

This is an early offline authoring foundation, not the complete TALORA product and not a production release. It does not yet implement the structured screenplay/stage-play document engine, native `.talora` package, deterministic pagination, PDF export, account system, synchronization, collaboration, ads, payments, organization roles, or IROKA inference.

No IROKA source is copied or redistributed. No model provider is connected. No API keys, signing secrets, production credentials, or private script fixtures are included.

A debug APK is for device testing only. A4 verification, independent security review, provider/legal decisions, production signing, data-residency clearance and separate A5 authorization remain required before production deployment.
