# TALORA A2 Technical Decision Register — Native Format, Conversion and Export Entitlements

- **Document ID:** TALORA-A2-TDR-002
- **Version:** 1.0.0
- **Date:** 19 November 2026
- **Status:** A2 working decision register; technical defaults ODR-01–ODR-12 approved by Owner on 19 November 2026, subject to stated validation gates
- **Branch:** `docs/a1-import-export-referral-entitlements`
- **Base A2 assessment:** `TALORA_A2_Impact_Assessment_Native_Format_Export_Economy_v1.0.0.md`
- **Implementation:** NOT AUTHORIZED

## 1. Executive disposition

This register advances A2 by defining implementable technical recommendations, recording the Owner's approval of the proposed technical defaults, and isolating decisions that remain open or require external evidence.

### Owner decision record — 19 November 2026

The Owner answered **Yes** on 19 November 2026 to the explicit ODR-11 proposal. Accordingly, ODR-01 through ODR-12 are recorded as approved technical defaults, subject to the mandatory external, security, licensing, compatibility and test gates stated throughout this document. ODR-11 now has a precise approved policy: canonical pagination profile, complete semantic content within pages 1–5, no artificial page breaks or silent mid-block truncation, and explicit disclosure of partial exports. This approval settles the product/technical default; it does not certify external compliance or evidence, and does not authorize implementation, merge or deployment.

The following Owner-approved product requirements are fixed inputs:
- Native extension `.talora`.
- Free export limit: five pages per export.
- Premium: all officially supported import/export formats.
- Ten verified referrals: one full-script export entitlement.
- Watermark-free one-time license: one selected project, including revisions.
- Watermark-free subscription terms: one, three, six and twelve months; longer terms optional.
- Referral qualification requires verified account, activation and anti-fraud checks.

**No source code, database schema, application implementation, payment integration, merge or deployment is authorized.**

## 2. Binding requirements versus technical proposals

| Requirement | Authority | A2 treatment |
|---|---|---|
| `.talora` extension | Owner-approved | Fixed requirement |
| Five-page Free export limit | Owner-approved | Fixed requirement; counting semantics still need definition |
| Premium access to all supported formats | Owner-approved | Fixed entitlement; support matrix remains capability-based |
| Ten verified referrals = one full-script export | Owner-approved | Fixed reward; output format and reward recurrence need clarification |
| One-time watermark-free project license includes revisions | Owner-approved | Fixed commercial rule; project transfer/fork lifecycle needs definition |
| 1/3/6/12-month watermark-free subscriptions | Owner-approved | Fixed term options; renewal/payment policy remains open |
| ZIP package, JSON manifest | Owner-approved 19 Nov 2026 | Approved technical default |
| Canonical typed document model | Existing A1 direction + Owner-approved technical default | Approved design direction; schema details remain subject to A3 design review |
| Premium removes five-page cap | Owner-approved 19 Nov 2026 | Approved technical default |
| Premium does not automatically remove watermark | Owner-approved 19 Nov 2026 | Approved; watermark-free rights remain separate entitlements |
| Referral reward defaults to watermarked PDF | Owner-approved 19 Nov 2026 | Approved; one full-script watermarked PDF reward per first ten qualified referrals |
| One-time project license covers PDF watermark removal only | Owner-approved 19 Nov 2026 | Approved initial scope; do not imply watermark removal for all formats |

## 3. A2-ADR-01 — Native .talora package

**Owner-approved default (19 November 2026): adopt a versioned ZIP-based package with a JSON manifest and typed structured content.**

### Package layout proposal

```
project.talora
  manifest.json
  document/
    project.json
    documents/
      <document-id>.json
    revisions/
      <revision-id>.json        # optional, policy-controlled
  assets/
    <content-hash>.<extension>
  previews/
    <preview-id>.<extension>   # optional, regenerable
```

### Required properties

- Stable format identifier and semantic schema version.
- Stable project and document identifiers.
- UTF-8 content and explicit language/locale metadata.
- Typed screenplay and stage-play elements, not rendered text alone.
- Stable element IDs for scene, block, character reference and cue.
- Asset inventory with media type, byte size and cryptographic digest.
- Explicit relationships between project, documents, revisions and assets.
- Optional revision-history inclusion policy; current approved revision must always be recoverable.
- Unknown extension preservation where safe, to improve forward compatibility.
- No executable scripts, macros or active content.
- Atomic save/replacement; interrupted writes must not corrupt the last valid package.
- Strict archive path, size, entry-count, compression-ratio and nesting limits.
- Schema validation before a package is opened into the canonical document model.
- Integrity verification before assets are trusted.

### Manifest proposal

The manifest should identify:
- `format_id`: stable TALORA format identifier.
- `format_version`: package schema version.
- `minimum_reader_version`: minimum compatible application version.
- `project_id`, `created_at`, `modified_at`.
- `documents`: document IDs, types, current revision IDs and language.
- `assets`: relative path, media type, size and SHA-256 digest.
- `features`: optional capabilities required to interpret the package.

The package should use a deterministic serialization profile for manifest hashing. A signature is not required for ordinary user-created files in v1; authenticity must not be confused with integrity. If signatures are introduced later, key lifecycle and account portability must be separately designed.

### Revision policy

- A normal save updates the current working state using the app's transactional persistence model.
- A portable package must include the current document state.
- Revision history inclusion is selectable; it must not be silently omitted where the user explicitly requests a full project archive.
- Importing a package must not overwrite an existing project without an explicit merge/replace choice.
- Revision IDs and source provenance must survive round-trip when the target format supports them.

### Open A2 items

- Maximum package size and asset limits: establish from device/storage testing.
- Whether history is included by default: Owner/product decision.
- Whether the initial v1 format is a single file only or supports linked external assets: recommend self-contained package for reliable transfer.

## 4. A2-ADR-02 — Canonical document model

A1 already requires a structured, typed document model independent of UI presentation. The native package and converters must use that canonical model.

### Proposed top-level types

- Project
- Workspace reference
- Document
- Document revision
- Act
- Scene
- Typed block
- Character/persona reference
- Location reference
- Transition
- Stage direction
- Cue and cue reference
- Production annotation
- Media/asset reference
- Format-specific extension node

### Core invariants

1. Content is stored semantically, not only as rendered pages or a single plain-text field.
2. Every document, scene and editable block has a stable identity.
3. Screenplay and stage-play constructs remain distinct where their semantics differ.
4. Formatting is represented as style/profile metadata and semantic node types, not fragile UI-only styling.
5. Unknown but valid extension nodes are preserved during import/export where possible.
6. A converter must report every unsupported or transformed construct.
7. No converter may silently delete source content.
8. A conversion is bound to a source revision and emits a conversion report.

### Conversion fidelity levels

- **L0 — Exact/lossless:** semantic content and supported formatting survive round-trip.
- **L1 — Near-lossless:** minor presentation differences only; no material semantic loss.
- **L2 — Lossy with disclosure:** known structure or formatting cannot be represented; user is warned and may inspect the conversion.
- **L3 — Text extraction/reconstruction:** structure is inferred from flattened text or PDF; user review is mandatory.
- **L4 — Unsupported:** no safe/reliable conversion path.

A format's fidelity level must be measured per format version and construct set, not claimed universally.

## 5. A2-ADR-03 — Import/export compatibility matrix

The following is a planning matrix, not a statement of implemented support or completed legal clearance.

| Format | Import direction | Export direction | Initial fidelity expectation | A2 priority |
|---|---|---|---|---|
| `.talora` | Required | Required | Exact for supported schema versions | P0 |
| PDF | Text/layout extraction and reconstruction | Required | Import L3; export deterministic rendered output | P0 |
| TXT | Required | Required | Text-level; semantic structure may be lost | P0 |
| Fountain | Required | Required | L0/L1 for supported Fountain constructs | P1 |
| DOCX | Required | Required | L1/L2 depending on document features | P1 |
| RTF | Assess and support where reliable | Required | L1/L2 | P1 |
| Final Draft FDX | Assess lawful, documented interchange | Assess lawful, documented interchange | L1/L2, format-version-specific | P1 |
| HTML | Assess | Assess | L1/L2 for defined semantic subset | P2 |
| Fade In native format | No promise absent documented lawful route | No promise absent documented lawful route | L4 until verified | Research |
| Celtx native/project format | No promise absent official export/interchange route | No promise absent official export/interchange route | L4 until verified | Research |
| Other screenplay apps | Per-format assessment | Per-format assessment | Must be independently validated | Ongoing |

### Compatibility release rule

A format may be displayed as supported only after:
- A documented specification or official interchange route is identified.
- License and redistribution obligations are reviewed.
- Parser/writer dependency licenses are reviewed.
- A versioned support profile is published.
- Authorized golden fixtures exist.
- Round-trip and regression tests pass.
- Security tests cover malformed and adversarial files.
- Known losses and unsupported features are disclosed.

PDF import is reconstruction, not guaranteed recovery of the original semantic structure. The import flow must offer a review/edit stage.

## 6. A2-ADR-04 — Free page limit and export job accounting

**Owner-approved product rule:** Free users may export up to five pages per export.

### Proposed semantics

- Page count is determined after applying a named, versioned layout profile and pagination engine.
- A PDF export is bound to a project ID, immutable document revision, output format, renderer version and export settings digest.
- The entitlement service is authoritative.
- A failed or cancelled export does not consume the allowance or a one-time reward.
- A completed export is idempotently recorded; retries for the same job must not double-consume.
- The system must not allow repeated page-range exports to be misrepresented as a full-script export reward.
- Page-count results and entitlement decisions are auditable without retaining full script text in billing records.

### Non-paginated formats — ODR-11 APPROVED (19 November 2026)

TXT and Fountain do not have intrinsic pages. Their Free export limit is therefore determined by TALORA's deterministic, fixed and versioned canonical pagination profile, not by arbitrary byte/character counts or inserted text page breaks.

- Apply the same named canonical pagination profile to the source revision before serialization, with fixed page geometry, margins, typography/font metrics, line spacing, screenplay/stage-play layout rules and renderer version.
- Maintain a stable mapping from semantic content ranges (including scene/block IDs and offsets) to canonical pages.
- Include only complete semantic content that fits within canonical pages 1–5.
- Do not insert artificial page-break markers into TXT or Fountain merely to simulate pages.
- Do not silently truncate within a semantic block. Stop before the next complete semantic block that cannot fit, and disclose the partial-export status, included range and stopping boundary in export metadata and the completion UI (without contaminating the plain-text script body with invented pagination).
- If the complete document fits within five canonical pages, export it in full.
- Premium/full-script entitlements may export the complete document subject to the applicable format and watermark rules.
- Bind every export job to the immutable source revision ID, document type, canonical pagination profile ID, renderer version and settings digest.
- Failed or cancelled jobs do not consume an entitlement; completed jobs are idempotently accounted.
- Golden pagination and boundary tests must cover Amharic/Ethiopic and mixed-script content, screenplay and stage-play profiles, and deterministic repeatability.

The profile's concrete page geometry, font set/metrics and versioned renderer configuration remain engineering specifications to be validated in A3/A4; their existence and exact values are not represented as implemented.

### Premium cap

The phrase "Premium format access: all supported import/export formats" does not explicitly state whether Premium removes the five-page limit. The product design needs a full-project export capability. **Owner-approved default (19 November 2026): Premium removes the five-page cap for full-project exports while active.**

## 7. A2-ADR-05 — Referral full-script export entitlement

**Owner-approved rule:** Ten verified referrals grant one full-script export entitlement.

### Proposed lifecycle

`created → attributed → pending_verification → qualified → reward_granted → redeemed`

Additional terminal or review states:
- rejected
- reversed
- disputed
- expired (only if an expiry policy is approved)

### Qualification requirements

- Invitee account is distinct and verified.
- Invitee completes a documented activation event.
- Attribution is recorded server-side and idempotently.
- Self-referral and duplicate-account checks pass.
- Suspicious patterns enter review; legitimate shared devices and household cases need a fair exception path.
- Referral reward is not granted merely because an install event occurred.
- Invitee data is minimized and is not used to profile the invitee's creative content.

### Activation recommendation

Use account verification plus a meaningful first-use event, such as creating and saving a first project. Do not require uploading a script or sharing private creative content to qualify.

### Reward semantics — Owner-approved defaults (19 November 2026)

- Is the milestone once per referring account lifetime, or repeatable for each additional group of ten qualified referrals?
- Does the reward apply to one full PDF export only, or one full export in any supported Free-eligible format?
- Is the reward watermarked by default? The product request describes watermarked full-script PDF export separately from paid watermark-free export; recommendation is watermarked PDF.
- Does the reward expire? Recommendation: no expiry while the account remains in good standing, subject to fraud reversal.

**Owner-approved default (19 November 2026):** One lifetime reward per referring account for the first ten qualified referrals, redeemable for one full-script watermarked PDF export. Further referral rewards require separately approved campaigns.

## 8. A2-ADR-06 — Watermark-free project license and subscriptions

### One-time project license

Owner-approved scope: one selected project, including revisions.

**Owner-approved identity default (19 November 2026):**
- Bind the license to immutable project UUID and owning account/organization scope.
- Revisions of the same project remain covered.
- A fork, duplicate or copied project receives a new project UUID and is not covered unless an explicit transfer/relicense operation is approved.
- A project ownership transfer must not silently move the license.
- Already exported files remain valid copies and are not remotely altered.
- Refund, chargeback, account deletion and organization ownership behavior require policy decisions.

### Subscription terms

Approved term options: 1, 3, 6 and 12 months. Longer terms are optional future products.

The backend entitlement ledger should store:
- subscription product/term ID
- verified provider transaction reference
- start and end timestamps
- state (pending, active, grace, cancelled, expired, refunded/revoked)
- renewal/cancellation status where applicable
- project or account scope
- audit provenance

Expiration must not invalidate prior downloads. The policy for in-progress exports at expiry must be defined; recommendation: entitlement is evaluated at job acceptance and a short-lived job token permits that accepted job to finish.

### Approved initial scope — 19 November 2026

The Owner approved watermark-free entitlement for PDF only in the initial release. Other formats should not carry a TALORA watermark unless the Owner separately approves format-specific branding.

## 9. A2-ADR-07 — Payment and commerce boundary

- Use a provider-neutral internal transaction and entitlement model.
- Verify provider webhooks/signatures server-side.
- Enforce idempotency for purchase, renewal, refund and chargeback events.
- Never grant paid entitlements from client-only callbacks.
- Support purchase restoration and account/device migration.
- Define refund and dispute behavior.
- No prices or provider are selected by this record.
- Validate Ethiopia payment-provider availability, app-store rules, taxes, FX and consumer obligations before selecting a provider.
- No automatic top-ups, hidden recurring charges or unapproved spending.
- The existing Premium one-time core software purchase must remain distinct from the new time-limited watermark-free export subscription unless a future approved bundle explicitly changes that.

## 10. A2-ADR-08 — Offline entitlement policy

Ordinary project creation, editing and local storage remain available offline.

Recommended entitlement behavior:
- Cache a signed, time-bounded entitlement snapshot.
- Bind snapshot to account, device registration and entitlement scope.
- Use a monotonic time source where available; detect wall-clock rollback and require reconciliation.
- Do not allow offline referral qualification or payment-state mutation.
- Define maximum offline validity based on security review and operational requirements.
- When the signed snapshot expires, preserve local editing and allow only the explicitly approved offline export fallback.
- Never delete or lock the user's source project because entitlement verification is unavailable.

Numeric offline validity period remains open pending security and product decision.

## 11. A2-ADR-09 — Security and privacy requirements

### File processing

- Treat every import as hostile input.
- Process complex formats in isolated workers with CPU, memory, time and output-size limits.
- Reject path traversal, symlink escape, archive bombs, excessive nesting, malformed XML, external entity resolution and unsafe embedded content.
- Disable macros, scripts, external resource fetches and active content.
- Use temporary-file isolation and deterministic cleanup.
- Validate output before making it available to the user.
- Do not let parser content or embedded instructions alter IROKA/system authorization.

### Data and audit

- Keep creative content out of referral and payment ledgers.
- Store only IDs, digests, format, counts and necessary operational metadata.
- Define retention and deletion for source uploads, temporary conversions and exports.
- Ensure project authorization is checked on every conversion job and download.
- Keep security/admin events auditable.
- Preserve user control over creative content and collaborator access.

## 12. A2-ADR-10 — Amharic and multilingual fidelity

Required test corpus:
- Amharic/Ethiopic text in scene headings, dialogue, character names and stage directions.
- Mixed Ethiopic and Latin script.
- Ethiopic punctuation and numerals where used.
- Combining marks and Unicode normalization edge cases.
- Font embedding and glyph fallback.
- Line wrapping, dialogue indentation, page breaks and headers.
- DOCX/PDF round-trip and copy/paste.
- Search, cursor navigation and text selection.
- Right-to-left content only where supported by the target format and product requirements.

Normalization must be non-destructive. Export must disclose font substitution or unsupported glyphs. Human review is required for Amharic layout acceptance.

## 13. Proposed data ownership and service boundaries

Conceptual domains only; not approved database schema:

- **Creative Project service:** canonical projects, documents, revisions and permissions.
- **Package service/library:** .talora package validation, import and export.
- **Conversion service:** isolated parser/writer jobs and conversion reports.
- **Rendering service:** pagination, PDF generation, watermark application and visual QA.
- **Entitlement service:** Free, Premium, export usage and project license decisions.
- **Referral service:** promo attribution, qualification and reward ledger.
- **Commerce adapter:** provider verification and transaction reconciliation.
- **Audit service:** entitlement, payment, administrative and security event evidence.

The client must not be authoritative for page counts, referral qualification, payment confirmation or watermark entitlement.

## 14. A2 risk register

| Risk | Rating | Required mitigation/evidence |
|---|---|---|
| Parser exploit or archive bomb | Critical | Isolated conversion worker, resource limits, adversarial tests |
| Payment spoofing or replay | Critical | Verified webhook, idempotency, reconciliation |
| Cross-project license leakage | High | Stable project UUID, tenant checks, authorization tests |
| Referral abuse | High | Verification, activation, risk review, appeals |
| Page-limit bypass | High | Server-side deterministic pagination and job binding |
| Lossy conversion | High | Fidelity labels, warnings, golden fixtures |
| Proprietary format rights | High | Per-format legal/license review |
| Ethiopic glyph/pagination loss | High | Golden corpus, visual and text regression |
| Offline clock rollback | High | Signed bounded entitlement, reconciliation |
| Data leakage through logs | High | Data minimization and redaction review |

## 15. A2 open decision register

| ID | Decision required | Recommendation | Status |
|---|---|---|---|
| ODR-01 | ZIP + JSON .talora container | Adopt | **APPROVED — 19 Nov 2026** |
| ODR-02 | Self-contained package default | Adopt | **APPROVED — 19 Nov 2026** |
| ODR-03 | Include revision history by default | Current revision mandatory; history selectable | **APPROVED — 19 Nov 2026** |
| ODR-04 | Premium removes five-page cap | Yes | **APPROVED — 19 Nov 2026** |
| ODR-05 | Premium includes watermark-free PDF | No; separate entitlement | **APPROVED — 19 Nov 2026** |
| ODR-06 | Referral reward output | One watermarked full-script PDF | **APPROVED — 19 Nov 2026** |
| ODR-07 | Referral milestone recurrence | First ten once per account; additional campaigns separate | **APPROVED — 19 Nov 2026** |
| ODR-08 | Referral reward expiry | No expiry while account is good standing | **APPROVED — 19 Nov 2026** |
| ODR-09 | Watermark license output scope | PDF only initially | **APPROVED — 19 Nov 2026** |
| ODR-10 | Project fork/transfer license behavior | Fork is new project; transfer requires explicit operation | **APPROVED — 19 Nov 2026** |
| ODR-11 | Non-paginated Free export limit | Deterministic TALORA canonical pagination; export complete semantic content fitting pages 1–5; disclose partial range/boundary; bind to revision/profile/renderer | **APPROVED — 19 Nov 2026** |
| ODR-12 | Offline entitlement validity | Signed bounded cache; numeric period after security review | **APPROVED DEFAULT — numeric period deferred to security review** |
| ODR-13 | Initial proprietary format support | Official interchange route only | External validation |
| ODR-14 | Payment provider and prices | Chapa and ArifPay primary due-diligence shortlist; Telebirr direct and SantimPay secondary; no selection until merchant eligibility, fees, settlement, payment methods and legal review are verified | **PARTIAL — public evidence collected; vendor confirmations and legal review outstanding** |
| ODR-15 | Existing A1 freeze status | Repository A1 baseline file still says draft/not frozen; reconcile with Owner's later A1 approval | Unresolved evidence discrepancy |

## 16. A2 supporting research

The detailed non-paginated export rationale and Ethiopia payment-provider evidence are recorded in `TALORA_A2_Payment_Provider_And_NonPaginated_Export_Research_v1.0.0.md` (TALORA-A2-RM-003). ODR-11 is approved as recorded above; implementation and pagination-profile validation remain future gates. ODR-14 has a documented shortlist, but no provider is selected and merchant eligibility, fees, settlement, recurring-payment support and legal requirements remain unverified.

## 17. A1 baseline consistency finding

The repository's `docs/a1-product-definition/00-A1-BASELINE.md` still states “Draft prepared for Owner review; not frozen” and says A1 closure requires review of all specification documents. The later Owner approval of the product and the new amendment must be reconciled with that file before an A2 closure claim. This assessment does not silently rewrite the baseline status.

## 18. A2 exit criteria and present disposition

A2 remains **PARTIAL / OPEN** until:
- Approved ODR-11 policy is translated into a testable pagination profile specification, boundary behavior and export metadata contract.
- External format licensing and payment-provider facts are researched and recorded.
- Initial support matrix is finalized with lawful routes and fidelity classifications.
- Canonical document model and .talora schema proposal are internally consistent.
- Page counting and non-paginated Free export rules are unambiguous.
- Referral, payment, subscription, refund and project-license state machines are specified.
- Security/privacy and offline policies are reviewed.
- Amharic acceptance corpus and test ownership are defined.
- The A1 freeze-status discrepancy is resolved.
- A traceable A3 backlog is prepared.

**Current gate:** Owner-approved technical defaults recorded for ODR-01–ODR-12. ODR-11 is approved; its detailed pagination profile and test evidence remain to be specified and validated. ODR-13 and ODR-15 remain unresolved; ODR-14 has a public-evidence shortlist but awaits vendor and legal validation. The A1 freeze-status discrepancy remains open. A2 is not closed. A3 implementation, PR merge and deployment remain unauthorized.
