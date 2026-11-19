# TALORA Supported Script Interoperability

**Status:** Development implementation; exact-head Android CI verification pending.

| Format | Import | Export | Scope and behavior |
|---|---:|---:|---|
| .talora | Yes | Yes | Native editable project archive; preserves project metadata, exact source text, revision snapshots and linked assets. |
| .txt | Yes | Yes | UTF-8 plain text; imports into a new local project and preserves decoded source text. |
| .fountain | Yes | Yes | UTF-8 Fountain screenplay text. Export is enabled for screenplay projects; stage plays should use TXT or the native format. |
| .pdf | No | Yes | Offline rendered reading/printing copy. It is not an editable source format and does not replace the native project. |
| Final Draft .fdx | Not yet | Not yet | Deferred until XML mapping, metadata preservation and round-trip fixtures are implemented and validated. |
| DOCX / RTF | Not yet | Not yet | Deferred; no compatibility claim is made until parser/writer behavior is tested against representative documents. |

## Source preservation

- TALORA remains the canonical owner of project source text.
- TXT and Fountain are UTF-8 text interchange paths; they do not preserve native project revisions or binary assets.
- PDF is a presentation/export artifact only.
- The .talora archive remains the only currently supported portable format that preserves project structure, revisions and attachments.
- Format import does not grant subscription, referral or other entitlements.
- Unknown formats are not silently treated as TALORA archives or converted.

## Current script-writing controls

- Screenplay and stage-play project types are distinct project metadata.
- Screenplay templates: scene heading, action, character, dialogue and transition.
- Stage-play templates: act, scene, stage direction, character and dialogue.
- Fountain export is limited to screenplay projects. Stage-play scripts remain fully editable and portable through the native format and TXT.
- PDF rendering is local/offline and bounded to 1,000,000 source characters and 500 pages.

## Validation required before wider compatibility claims

- Android import/export workflow tests on the exact branch SHA.
- Golden-file tests for Fountain interoperability with independent parsers/editors.
- PDF visual checks for Ethiopic glyph fallback, pagination, long dialogue and mixed-script content.
- FDX, DOCX and RTF require separately approved implementation and conformance fixtures before being marked supported.
