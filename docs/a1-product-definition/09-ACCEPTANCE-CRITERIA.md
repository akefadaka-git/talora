# Product Acceptance Criteria

These criteria define the evidence required to accept product capabilities. Detailed numeric performance targets must be established through device research and A2/A3 test baselines rather than invented at A1.

## Authoring
- Create and edit screenplay and stage-play documents.
- Support required typed elements and navigation.
- Save reliably across app restarts.
- Undo and redo supported editing operations.
- Preserve document structure during editing.

## Formatting and preview
- Apply the correct template to supported document types.
- Produce stable pagination and print preview.
- Correctly handle Ethiopic and Latin scripts.
- Report unsupported constructs instead of silently dropping them.

## Import and export
- Round-trip supported formats using authorized test fixtures.
- Compare structure, text, scene ordering and formatting.
- Verify generated PDFs visually and textually.
- Provide actionable conversion warnings.

## Offline and synchronization
- Create, edit, search and export supported local documents offline.
- Recover safely after process termination.
- Queue synchronization operations durably.
- Detect concurrent edits.
- Preserve recoverable conflict versions.
- Never silently overwrite user work.

## IROKA
- Respect project permissions.
- Present suggestions separately from canonical content.
- Require explicit acceptance before applying material changes.
- Maintain traceable accepted-change history.
- Degrade gracefully when intelligence services are unavailable.

## Privacy and security
- Enforce project and organization permissions.
- Protect secrets and sensitive local data.
- Prevent cross-project data access.
- Keep advertising systems separate from creative content.
- Provide documented export and deletion behavior.
- Verify third-party dependency and licensing risks.

## Localization
- Validate Ethiopic rendering, cursor behavior, selection, search and export.
- Validate supported language switching.
- Test mixed-script documents.
- Review localized interface strings with qualified speakers.

## Monetization
- Verify free and premium entitlement transitions.
- Verify premium ad suppression.
- Verify purchase restoration and revocation handling.
- Verify advertisements never appear in exported scripts.
- Verify no private creative content is supplied to ad targeting systems.

## Production workflows
- Validate script breakdown against manually reviewed ground truth.
- Validate schedule and cue references.
- Ensure generated production documents remain editable and traceable.

## Release readiness
- Automated tests pass on supported environments.
- Critical defects are resolved or explicitly accepted by Owner.
- Accessibility and performance evidence is available.
- Backup, recovery and support procedures are documented.
- Distribution, billing and legal dependencies are verified.
