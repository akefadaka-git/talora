# TALORA A2 Technical Decision Register — Native Format, Conversion and Export Entitlements

- **Document ID:** TALORA-A2-TDR-002
- **Version:** 1.0.0
- **Date:** 19 November 2026
- **Status:** A2 decision register; ODR-01–ODR-13 Owner-approved; ODR-14 architecture reconciled subject to external vendor/legal gates; ODR-15 resolved; D01 VERIFIED-CLOSED for bounded development-only baseline selection; A2 remains PARTIAL / NOT CLOSED pending remaining lifecycle, offline-security, Amharic-evidence and dependency/licensing gates
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

The following is the Owner-approved bounded v1 product support scope (19 November 2026). It is not a statement of implemented support, completed dependency licensing, or completed compatibility testing.

| Format | Import direction | Export direction | Initial fidelity expectation | A2 priority |
|---|---|---|---|---|
| `.talora` | Required | Required | Exact for supported schema versions | P0 |
| PDF | Text/layout extraction and reconstruction | Required | Import L3; export deterministic rendered output | P0 |
| TXT | Required | Required | Text-level; semantic structure may be lost | P0 |
| Fountain | Required | Required | L0/L1 for supported Fountain constructs | P1 |
| DOCX | Required | Required | L1/L2 depending on document features | P1 |
| RTF | Assess and support where reliable | Required | L1/L2 | P1 |
| Final Draft FDX | Excluded from initial v1 promise | Excluded from initial v1 promise | No v1 support claim; reconsider only after lawful official route, versioned fixtures and compatibility review | Deferred |
| HTML | Assess | Assess | L1/L2 for defined semantic subset | P2 |
| Fade In native format | Excluded from initial v1 promise | Excluded from initial v1 promise | No v1 support claim; reconsider only after documented lawful and testable interchange route | Deferred |
| Celtx native/project format | Excluded from initial v1 promise | Excluded from initial v1 promise | No v1 support claim; reconsider only after official documented and testable interchange route | Deferred |
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
| ODR-13 | Initial format support and licensing | Owner-approved bounded v1 support matrix; defer FDX/Fade In/Celtx native formats pending lawful, documented and tested routes | **APPROVED — Owner confirmation recorded 19 Nov 2026; exact dependency/fixture license review remains a pre-implementation gate** |
| ODR-14 | Payment provider and prices | Provider-neutral ledger; Chapa and ArifPay primary due-diligence shortlist; Telebirr direct alternative; SantimPay and other NBE-commercialized operators for comparison; no selection or price until written merchant/legal verification | **RECONCILED AT A2 ARCHITECTURE LEVEL — no provider selected; vendor and legal gates remain mandatory before payment implementation** |
| ODR-15 | Existing A1 freeze status | Record explicit 18 Nov 2026 Owner approval of the 11-document A1 baseline at SHA `599bcb4a71bd7ca7e1b5945c8034f29ecff4bc06`; correct the carried-forward baseline record without rewriting the historical commit | **RESOLVED — approval and immutable baseline recorded; current A1 record updated** |

## 16. A2 supporting research

The detailed non-paginated export rationale and Ethiopia payment-provider evidence are recorded in `TALORA_A2_Payment_Provider_And_NonPaginated_Export_Research_v1.0.0.md` (TALORA-A2-RM-003). ODR-11 is approved as recorded above; implementation and pagination-profile validation remain future gates. ODR-14 has a documented shortlist, but no provider is selected and merchant eligibility, fees, settlement, recurring-payment support and legal requirements remain unverified.

## 17. A1 baseline consistency finding

The original 11-document A1 specification was explicitly approved by the Owner on 18 November 2026 at immutable baseline commit `599bcb4a71bd7ca7e1b5945c8034f29ecff4bc06`. The current A2 branch copy of `docs/a1-product-definition/00-A1-BASELINE.md` has been corrected to record OWNER-APPROVED / FROZEN and to preserve the original commit as the historical authority. The later native-format/import-export amendment remains a separate Owner-approved amendment. The historical baseline commit was not rewritten; ODR-15 is RESOLVED.

## 18. A2 exit criteria and present disposition

A2 is **PARTIAL / NOT CLOSED**. The product/export/entitlement decision set is substantially reconciled, but the following closure blockers remain:
- ODR-13 product-scope approval is recorded. The exact parser/writer dependency licenses, transitive dependencies, fixtures and distribution obligations must still be verified before implementation.
- IROKA D01: no single compatible immutable component graph has been established. The current main reference and candidate G03/G05/G08/CIW/G01/G02/G11 branches are not one verified graph. See the mirrored TALORA–IROKA contract v0.1.2 and A2 closure assessment.
- External payment-provider merchant eligibility, fees, settlement, refunds, recurring/one-off terms, payment methods, sandbox access and legal review remain mandatory before payment implementation (ODR-14).
- Independent security review, exact dependency/SBOM review and A4 test evidence remain future gates; no evidence is claimed as complete.
- A traceable A3 backlog can be planned from this register, but A3 implementation requires separate explicit Owner authorization.

**Resolved:** ODR-01–ODR-13 Owner-approved; ODR-11 policy and ODR-13 bounded v1 format scope recorded; ODR-15 A1 approval discrepancy corrected. ODR-14 architecture is provider-neutral and reconciled, with vendor/legal validation deferred to the pre-implementation gate.

**Current gate:** A2 CLOSURE ASSESSMENT = PARTIAL / NOT READY FOR OWNER CLOSURE APPROVAL because D01 has no compatible immutable integration baseline. ODR-13 product scope is Owner-approved; exact dependency/fixture license review remains a pre-implementation gate. A3 implementation, PR merge and deployment remain unauthorized.


### D01 supplemental exact API compatibility evidence — 19 November 2026

The G03/G08 Kernel head 0680337b7dd0966374acd2649cd9197e56b8d92a does not export the G05/G02/G11-required Kernel symbols G05ActivationDecision, G05ActivationTuple, OrganizationAuthorityResolver, ReviewerKeyBindingResolver, ReviewerProofVerifier, SignedReviewerProof, TenantContext and VerifiedReviewerProof. The G05/G02/G11 head 8f7ddb9e9dd2b796cd303ba645a1a3c843eb3810 does not export the G03/G08 symbols InferenceRulesOutput, PremiseModel and workflow_graph. The two heads diverge by 378 / 391 commits from common base 1a6f30cc3e64b8551f8e725c4cad8fcc3fa2c472 and both change the Kernel public API and dependency manifests.

A separate Brain candidate 9d68b580e06bb8335a626524d64b345c3a5a38aa implements DeterministicFakeAdapter, but its TaskEnvelope validator rejects populated principal, tenant and policy context and non-empty domain input references. It is not an integrated TALORA adapter. The G05 HTTP host only exposes /v1/g05/execute; CIW is a browser conversation API. No candidate provides the complete private TALORA task API.

Exact-head combined statuses/workflow runs were not returned for the principal current candidates; this is NOT VERIFIED, not FAIL. No combined build, migration, SBOM or end-to-end API evidence exists.

**D01 current disposition:** RECONCILIATION PERFORMED; NO SELECTABLE, VERIFIED IMMUTABLE GRAPH. The decision remains OWNER-APPROVED — RECONCILE, but its technical exit condition is not met. No source composition, A3 implementation, merge or A5 deployment is authorized.


## 19. Supplemental A2 reconciliation — entitlement lifecycle, offline security, Amharic acceptance and dependency rights (19 November 2026)

This section reconciles existing requirements and evidence. Items labelled PROPOSED are not Owner-approved product rules. No implementation or external activation is authorized.

### 19.1 Entitlement and commerce lifecycle

Keep payment transactions, entitlements, export jobs and referral rewards as separate state machines.

**Payment transaction:** created → awaiting_provider → provider_verified → settled. Exception/reconciliation states: verification_pending, failed, refund_pending, partially_refunded, refunded, disputed, chargeback_won, chargeback_lost and reversed. Provider callbacks are untrusted signals until signature/origin and server-side transaction verification succeed. Events and transitions must be idempotent and auditable.

**Entitlement:** pending → active → (cancel_at_period_end | expired | refund_pending | disputed | revoked). Use grace only where a provider-supported settlement delay is evidenced and approved. For initial 1/3/6/12-month products, prefer one-off fixed-term purchases with explicit start/end timestamps; automatic recurring billing is not approved. Cancellation stops a future renewal instruction but does not prematurely remove already-paid access.

**Export job:** created → authorized → accepted → running → completed, with terminal alternatives failed, cancelled, timed_out and rejected. Evaluate entitlement at job acceptance and issue a short-lived job authorization bound to user, tenant, project, revision, output format, watermark mode, pagination profile and renderer. An accepted job may finish after entitlement expiry only within that bounded lease. Failed/cancelled jobs do not consume a one-time reward; successful completion consumes exactly once. Repeated idempotency keys return the same job/result.

**Refund/chargeback treatment — PROPOSED, Owner/legal approval required:**
- A refund request enters refund_pending; a provider callback alone does not prove completion.
- A verified full refund revokes future use of the associated paid entitlement. Partial refunds require a separately defined adjustment; do not infer one.
- A chargeback/dispute freezes new watermark-free exports for the disputed transaction/project scope while preserving local editing, source files and already-exported copies. Reinstate access only after authoritative dispute resolution in the user's favor.
- A lost chargeback revokes future use of the affected paid entitlement. Preserve audit evidence and do not remotely alter already-exported files.
- Define statutory refund rights, timing, partial-refund calculations, consumer notices and evidence retention with qualified counsel before commerce launch.

**Project license and account/organization lifecycle — PROPOSED, Owner/legal approval required:**
- The one-time watermark-free PDF license remains attached to project UUID plus owning account/organization scope. Revisions remain covered; fork/duplicate creates a new project UUID; transfer requires an explicit authorized transfer/relicense operation.
- For organization-owned projects, the legal organization/tenant is the license owner; a member leaving the organization does not take the license.
- Account deletion must not silently transfer a license. Provide an explicit pre-deletion export/transfer path where lawful; delete/anonymize personal data under the approved retention schedule while preserving only legally required transaction/audit evidence.
- Project deletion does not automatically create a refund. Refund eligibility follows published consumer terms and applicable law.
- Define recovery and ownership disputes before launch.

### 19.2 Referral lifecycle and anti-fraud controls

Owner-approved constraints remain: first ten qualified referrals per referring account, once per lifetime; one full-script watermarked PDF export; no expiry while the account remains in good standing; further campaigns require separate approval.

Recommended operational states: created → attributed → pending_verification → pending_activation → risk_review (if flagged) → qualified → reward_granted → redeemed. Exception states: rejected, reversed, appeal_pending, appeal_upheld and appeal_denied.

**Proposed qualification event:** invitee verifies a distinct account and creates/saves a first project. Do not require script upload, sharing, IROKA processing or payment. Attribute server-side with a unique referral token and idempotent event key.

**Proposed controls:** self-referral and duplicate-account checks; velocity and referral-cluster anomaly detection; rate limits; evidence/reason codes; manual review for ambiguous shared-device/household cases; separate appeal reviewer; immutable audit trail; minimal retention. Avoid invasive device fingerprinting and do not inspect creative content for fraud scoring. Reversal requires documented fraud or a verified qualifying-event reversal, not mere inactivity. If a reward was redeemed, an exported file cannot be recalled; prevent further rewards and apply only a separately approved account-level remedy.

**Owner decisions still required:** exact activation definition; risk thresholds; review service levels; appeal deadline and reviewer independence; when a pending/qualified referral can be reversed; account-good-standing definition; retention period and user notice. Do not invent numeric fraud thresholds before observing legitimate shared-device/household patterns.

### 19.3 Offline entitlement security boundary

Owner-approved default: signed, bounded entitlement cache; numeric validity deferred to security review (ODR-12).

**Proposed security design:**
- Server signs a canonical entitlement snapshot using an asymmetric signing key. App pins the verification public key; server key rotation is versioned and overlap-supported.
- Snapshot binds account, installation/device key, tenant/project scope, entitlement IDs, product/term, issued-at, not-before, expiry, policy version, allowed offline operations and unique snapshot ID.
- Protect the device private key using Android Keystore where available; encrypt cached snapshots at rest. A copied cache on another installation must fail device binding.
- Persist last trusted server time and a monotonic elapsed-time checkpoint; detect wall-clock rollback, reinstall/backup restore, snapshot replay and key invalidation. Where trusted time cannot be established, fail closed for paid/watermark-free export and reconcile online.
- Offline use cannot create purchases, renewals, refunds, chargebacks, referral qualifications/rewards, ownership transfers or entitlement mutations.
- Local editing and access to source projects remain available regardless of cache expiry or network status.
- Free export may apply the deterministic five-page rule and watermark locally. Premium full-script and watermark-free PDF exports require a valid cached entitlement and a tamper-evident local reconciliation queue. No offline reward redemption or entitlement expansion.
- A short-lived accepted-job lease may finish after subscription expiry as specified in §19.1.

**Numeric validity proposal — NOT OWNER-APPROVED:** maximum seven calendar days from last successful server synchronization for cached paid entitlements, with immediate local fail-closed behavior on detected rollback or device-key invalidation. Seven days is a product/security trade-off proposal, not a verified security guarantee. Threat modelling and independent review must test rooted devices, backup/restore, clock rollback, app reinstall, key rotation, revoked accounts and delayed reconciliation before this value can be frozen. If controls cannot be validated, shorten the window or require online verification for watermark-free exports.

### 19.4 Amharic/Ethiopic acceptance evidence

Existing A1 localization requirements require Unicode-safe storage/editing, shaping, font fallback, cursor/selection/copy-paste, mixed Ethiopic/Latin, search, non-destructive normalization, export glyph coverage and representative Amharic, Afaan Oromo, Tigrinya, Maale and mixed-language scripts. A2 additionally lists scene/dialogue/character/stage-direction coverage, punctuation/numerals, combining marks, font embedding, line wrapping, pagination, PDF/DOCX round-trip and human review.

**New evidence:** the official Noto Fonts specimen describes Noto Sans Ethiopic as containing 566 glyphs and supporting 505 characters across Ethiopic, Ethiopic Extended, Ethiopic Extended-A and Ethiopic Supplement. The Noto Ethiopic source repository identifies SIL Open Font License 1.1. This is a viable font candidate, not a selected/pinned TALORA dependency and not proof of coverage for every required character. Unicode's Ethiopic chapter documents core and extension blocks, including characters used by languages beyond Amharic. Sources:
- https://notofonts.github.io/noto-docs/specimen/NotoSansEthiopic/
- https://github.com/notofonts/ethiopic
- https://www.unicode.org/versions/Unicode18.0.0/core-spec/chapter-19/

No rights-cleared TALORA screenplay/stage-play acceptance corpus, signed reviewer roster, measured acceptance report or golden pagination fixture was found in the current A2 repository evidence. Public Amharic speech corpora are not substitutes for screenplay/stage-play layout corpora; dataset rights and research-only restrictions must be checked independently before any reuse.

**Proposed acceptance corpus — Owner/reviewer approval required:**
- Commission or author original synthetic screenplay and stage-play samples; record author, permission/license, allowed uses, source hash and fixture ID for each. Do not place private user scripts in tests without separate explicit consent.
- Minimum proposed set: 30 fixtures, including Amharic-only, Amharic/Latin mixed-script and punctuation/numeral/Unicode-edge cases, spanning screenplay and stage-play layouts. Include character names, scene headings, dialogue, action, stage directions, long names, Ethiopic wordspace/punctuation and supported numeral forms.
- Review by at least two independent competent Amharic/Ethiopic readers/editors, with qualification and sign-off recorded. Extend to Afaan Oromo, Tigrinya and Maale only when each language's launch scope and competent reviewers are approved.
- Acceptance thresholds: zero missing, duplicated or reordered text code points in lossless .talora round trips; zero silent normalization changes; zero missing-glyph/tofu substitutions for the approved corpus; zero clipped/overlapping text; deterministic page count and semantic page-boundary mapping for identical revision/profile/renderer inputs; all L2/L3 losses disclosed; zero open critical/high defects at release gate. Human reviewers sign off visual regressions; remaining medium/low issues are documented.
- Track text fidelity, shaping/glyph, editing interaction, search, pagination and visual-layout results separately. Font coverage scans alone are not acceptance.

Corpus size, reviewers and thresholds above are proposals pending Owner approval; no full Amharic workflow acceptance claim is made.

### 19.5 Dependency, fixture and distribution-license gate

**Verified status:** no TALORA parser/writer/rendering dependency has been selected, pinned, installed or license-audited; no TALORA SBOM or dependency lockfile was found on the documentation-only project branches; no round-trip test was run; no fixture provenance register or legal opinion was found. There is therefore no exact dependency graph to certify.

| Format/component | Current evidence | Remaining gate |
|---|---|---|
| .talora ZIP/JSON | Owner-approved format direction; TALORA-owned schema not implemented | Select archive/JSON libraries; pin exact versions; inspect transitive licenses/security advisories; archive traversal/bomb tests |
| TXT | Public interchange; no serializer selected | Verify encoding/newline behavior and exact text-preservation tests |
| Fountain | Published syntax; official reference-code path described as MIT | Pin exact repository commit/package; inspect LICENSE, notices, dependency closure, fixture provenance and trademark use |
| DOCX | OOXML/ISO 29500 documented subset | Pin exact parser/writer and transitive set; verify macro/external-relationship handling, compatibility and notices |
| RTF | Documented interchange; export in v1 scope | Select writer; parser import remains deferred until security validation |
| PDF | Export P0; import deferred to reviewed reconstruction | Select renderer/OCR only when needed; review renderer, font embedding, bundled assets and font license/notice |
| Noto Sans Ethiopic candidate | Upstream source states SIL OFL 1.1 | Pin release/commit and exact font binaries; include OFL/copyright notices; check Reserved Font Name conditions, embedding and glyph coverage; test Android/PDF rendering |
| FDX / Fade In / Celtx native | Explicitly excluded from initial v1 promise | No adapter, fixtures or reverse engineering until separate lawful documented route and Owner scope approval |

**Required pre-implementation evidence package per dependency:** package name, exact version/commit, source URL, package hash, SPDX license identifier and license text, copyright/NOTICE obligations, transitive dependency closure, platform/native components, known vulnerabilities, distribution/embedding obligations, SBOM, reviewer and decision. Per fixture: provenance, author/rights holder, written permission/license, allowed commercial/test redistribution, source hash and retention rule. If any obligation is incompatible or ambiguous, exclude the dependency/fixture and return for a separate Owner decision. Public availability is not permission.

**IROKA restriction:** TALORA's D03 disposition remains API_ONLY. The D01 freeze does not grant source copying, vendoring, modification, SDK packaging, redistribution or sublicensing of IROKA code. IROKA use remains within the approved private service boundary and separately verified contractual/Owner permissions.

### 19.6 Consolidated A2 classification after this reconciliation

- **Owner-approved and closed for policy:** ODR-01–ODR-13 product defaults; referral reward count/output/recurrence/expiry; PDF-only watermark entitlement; project UUID/fork/transfer principle; canonical non-paginated pagination; bounded development-only D01 baseline selection.
- **Architecture reconciled, external gate remains:** provider-neutral commerce design and Ethiopia provider due-diligence shortlist; no provider, merchant eligibility, fees, settlement or legal terms approved.
- **Design proposal only — Owner approval required:** detailed refund/chargeback/account-deletion/organization transfer rules; referral activation/risk/review/appeal mechanics; numeric offline cache validity; Amharic corpus size/reviewer/threshold acceptance plan.
- **Pre-implementation evidence gate:** exact parser/writer/rendering dependency versions, transitive licenses, SBOM, fixture rights/provenance, font binary/release pinning, IROKA API/service rights.
- **A4/release verification, not A2 completion evidence:** implementation tests, deterministic pagination, Amharic visual/regression runs, security penetration review, operational payment/referral simulations.

No source code, parser, font, payment provider, live key, paid service or production system was activated or modified by this reconciliation.
