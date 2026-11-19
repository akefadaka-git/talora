# TALORA Supported Script Interoperability

**Status:** Development implementation; exact-head Android CI verification pending.

| Format | Import | Export | Scope and behavior |
|---|---:|---:|---|
| .talora | Yes | Yes | Native editable project archive; preserves project metadata, exact source text, revision snapshots and linked assets. |
| .txt | Yes | Yes | UTF-8 plain text; imports into a new local project and preserves decoded source text. |
| .fountain | Yes | Yes | UTF-8 Fountain screenplay text. Export is enabled for screenplay projects; stage plays should use TXT or the native format. |
| .pdf | No | Yes | Offline rendered reading/printing copy. It is not an editable source format and does not replace the native project. |
| Final Draft .fdx | Not yet | Not yet | Deferred until XML mapping, metadata preservation and round-trip fixtures are implemented and validated. |
| DOCX | Yes | Yes | Basic WordprocessingML paragraph text; images, tables, comments, tracked changes, styles and page layout are not preserved. |
| RTF | Yes | Yes | Basic text, paragraph breaks, escaped characters and Unicode; advanced fields, tables, embedded objects and full code-page fidelity are not preserved. |

## Source preservation

- TALORA remains the canonical owner of project source text.
- TXT, Fountain, FDX, DOCX and RTF are text interchange paths; they do not preserve native project revisions or binary assets.
- FDX maps paragraph text and common screenplay paragraph categories; unsupported Final Draft metadata is not retained.
- DOCX intentionally emits a minimal editable Word document and imports document-body paragraphs only.
- RTF handles common text and Unicode controls but is not a complete implementation of every RTF specification extension.
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
- Golden-file tests against independent FDX, DOCX and RTF readers/writers and supported application versions.
- PDF visual checks for Ethiopic glyph fallback, pagination, long dialogue and mixed-script content.
- FDX, DOCX and RTF require separately approved implementation and conformance fixtures before being marked supported.
