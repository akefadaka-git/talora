# System Architecture

## Architecture objectives

- Android-first, offline-first.
- Shared domain model independent of presentation.
- IROKA integrated through stable contracts.
- Modular backend services and versioned APIs.
- Future desktop and web reuse.
- Secure collaboration and data portability.

## Logical layers

1. **Android presentation:** Kotlin, Jetpack Compose, navigation, accessibility and adaptive layouts.
2. **Application/domain layer:** project, document, scene, character, production and entitlement use cases.
3. **Creative document engine:** structured screenplay and stage-play model, editing operations, pagination, formatting and validation.
4. **Local persistence:** Room/SQLite, migration strategy, local media index and protected files.
5. **Synchronization:** durable outbox, idempotent operations, version vectors or equivalent concurrency model, conflict detection and recovery.
6. **IROKA integration:** capability contracts, context assembly, policy checks, inference adapters and audit records.
7. **Backend:** versioned APIs, identity, projects, collaboration, media, entitlements and organization services.
8. **Infrastructure:** PostgreSQL, S3-compatible object storage, observability, backup and recovery.

## Android technology direction

- Kotlin.
- Jetpack Compose.
- Room / SQLite.
- Android Keystore for protected key material where applicable.
- Background work using supported Android scheduling APIs.
- Modular Gradle build.
- Automated unit, integration and UI tests.

## Backend technology direction

Rust-compatible modular services are preferred to align with IROKA architecture, subject to A2 compatibility and operational review.

- PostgreSQL for relational application data.
- Object storage behind an S3-compatible abstraction.
- Versioned REST or other documented API contracts.
- Authentication and authorization services.
- Audit and operational telemetry.
- Explicit schema migrations and backup/recovery procedures.

## Suggested domain boundaries

- Identity and access.
- Creative projects and workspaces.
- Documents and formatting.
- Scenes and characters.
- Production planning.
- Media and attachments.
- Collaboration and synchronization.
- IROKA intelligence.
- Monetization and entitlements.
- Administration and audit.

## Structured document model

The model should represent project, document, act, scene, typed block/paragraph, dialogue, character reference, stage direction, transition, cue, production annotation, revision and stable element identity.

Document content must not be stored solely as rendered text. Rendering and pagination are derived views of canonical structured content.

## Synchronization requirements

- Local edits persist before network synchronization.
- Operations are idempotent and safely retryable.
- Concurrent edits are detected rather than silently overwritten.
- Conflicts retain both recoverable versions until resolved.
- Revision history is preserved.
- Uploads resume safely.
- Deletion and tombstone behavior is explicit.
- Sync state is visible to users.

## Export architecture

Use format-specific adapters over the canonical document model. Each adapter must declare supported constructs, validate output and produce a conversion report. PDF pagination and layout require golden-file or visual regression testing. FDX compatibility must be validated against real, authorized sample documents.

## Architectural constraints

No assumption that IROKA's internal services or APIs are already available to TALORA. A2 must discover actual interfaces and establish supported integration contracts. No credentials or private IROKA implementation details belong in public documentation.
