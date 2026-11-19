# TALORA

TALORA is an Ethiopia-first, Android-first creative writing application for screenwriters, stage writers, and production teams. It is designed for Amharic-first writing, offline use, and structured project portability.

## Current Android foundation

- Kotlin and Jetpack Compose application shell.
- Amharic-first interface with an English language switch.
- Local SQLite project storage.
- Script editor with debounced local autosave.
- Source-text preservation for Ethiopic and mixed-script content.
- GitHub Actions workflow for JVM unit tests and installable debug APK packaging.

## Native project format

The version 1 `.talora` archive codec is implemented in the Android codebase. It uses a versioned ZIP container with a JSON manifest, editable document content, revision snapshots, embedded assets, and SHA-256 payload verification.

The codec rejects unsupported schema versions, path traversal, duplicate entries, missing references, unknown payload entries, and integrity mismatches. Storage Access Framework import/export and persistence integration remain in development.

See [the TALORA v1 file-format specification](docs/file-format/talora-v1.md).

## Build and test

From the repository root:

```bash
gradle -p android testDebugUnitTest assembleDebug --no-daemon
```

The debug APK is produced at:

`android/app/build/outputs/apk/debug/app-debug.apk`

GitHub Actions publishes the APK as a short-lived workflow artifact for test installation.

## Product scope

The current branch is an implementation baseline, not a production release. Import/export user flows, full revision persistence, linked-asset persistence, additional document-format compatibility, export entitlements, verified referral rewards, and IROKA integration remain future implementation work.

Premium, referral, and licensing entitlements are separate from the native file format. A `.talora` file does not grant paid features or transfer account entitlements.
