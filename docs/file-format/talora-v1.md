# TALORA Native Project Format — Version 1

**Status:** Core archive codec and first Android Storage Access Framework / SQLite integration are implemented on the development branch. Exact-head CI verification is pending.

## Goals

The native `.talora` file is a portable, self-contained project container. It preserves editable source text, project identity and metadata, revision history, and linked binary assets. It is not a rendered PDF and must not flatten a project into a single export.

## Container

A `.talora` file is a ZIP archive with UTF-8 JSON metadata:

- `manifest.json` — format name, schema version, project identity, timestamps, current revision, revision IDs, asset descriptors, and SHA-256 hashes.
- `document.json` — editable document content, script type, language tag, metadata, and references to revisions and assets.
- `revisions/{revision-id}.json` — immutable revision snapshots.
- `assets/{asset-id}/content.bin` — embedded binary assets; the original filename and media type are stored in the manifest.

The manifest hashes every payload entry except itself. Import verifies the entry list and all hashes before returning a project.

## Version 1 data model

The document records:
- Stable project ID and title
- Script type (for example, screenplay or stage play)
- BCP-47 language tag
- Exact source content, without whitespace or Unicode normalization
- Creation and update timestamps
- String metadata
- Revision IDs and asset IDs

A revision records its own ID, owning document ID, timestamp, title, full source content, and optional change note. Assets record a stable ID, original filename, media type, archive path, and SHA-256 digest.

## Import safety

The codec:
- Rejects unsupported schema versions, malformed JSON, duplicate entries, duplicate IDs, path traversal, unknown archive entries, missing references, and hash mismatches.
- Rejects absolute paths, backslashes, and `.` / `..` path segments.
- Limits compressed input to 60 MiB, total uncompressed data to 50 MiB, individual entries to 20 MiB, and archive entries to 256.
- Does not extract paths to the filesystem; payloads remain in memory until the caller chooses a destination.

## Compatibility policy

Schema changes require a new schema version and an explicit migration path. Version 1 readers fail closed on unknown versions. The app must preserve original source text exactly; display/search normalization must never overwrite canonical content.

## Android integration status

- Android document picker opens `.talora` packages and safe ZIP/octet-stream fallbacks.
- Android document picker exports `.talora` packages using the registered TALORA MIME type.
- Import validates the complete archive before writing and imports project, revision, and asset records in a single SQLite transaction.
- Local SQLite schema version 2 adds project creation time, script type, language tag, metadata, current revision reference, revision snapshots, and binary asset storage.
- Project, revision, and asset IDs are remapped on collision; archive references are updated consistently.
- Project switching and import flush the current editor state before changing the active project.
- Users can create named revision snapshots, browse revision history, and restore a selected snapshot.
- Users can attach files through Android's document picker, inspect attachment names/types/sizes, and remove attachments.
- Package import/export preserves revision snapshots and attached binary assets.
- Import/export errors are surfaced in the app; archive reads remain bounded by the codec size limits.

## Remaining work

- Verify the new Robolectric database migration and package round-trip tests on the exact branch head.
- Add revision comparison and richer revision metadata/editing.
- Add asset preview, replacement, and save-as workflows.
- Add export/import progress for large packages and stronger recovery for provider interruptions.
- Define entitlement enforcement separately from the file format; the archive itself must not encode or grant premium/referral rights.
