# TALORA A2 Impact Assessment — Native Format, Interoperability and Export Economy

- **Document ID:** TALORA-A2-IA-002
- **Version:** 1.0.0
- **Date:** 19 November 2026
- **Status:** A2 assessment draft; read-only architectural impact assessment
- **Related amendment:** TALORA-A1-AMD-002
- **A1 parent baseline:** `599bcb4a71bd7ca7e1b5945c8034f29ecff4bc06`
- **Implementation status:** NOT AUTHORIZED

## 1. Executive assessment

The approved requirements materially expand TALORA beyond a screenplay editor. They introduce a native project-package format, a document conversion subsystem, backend-controlled entitlements, referral attribution and rewards, payment verification, and project-bound watermark licenses.

The requirements are coherent, but they are not yet implementation-ready. A2 must settle format semantics, conversion fidelity, entitlement boundaries, fraud controls, payment-provider integration, privacy/retention, offline authorization and commercial economics.

**A2 conclusion:** PARTIAL / OPEN. This document identifies impact and required decisions; it does not approve architecture or implementation.

## 2. Impact map

| Domain | Impact | A2 work required |
|---|---|---|
| Native project model | High | Define package manifest, schema, assets, revisions and migrations |
| Android storage/editor | High | Define open/save/import/export lifecycle and safe temporary files |
| Backend/API | High | Define conversion jobs, entitlement checks and audit APIs |
| Conversion engine | Very high | Format adapters, parser sandboxing, fidelity and lossy-conversion reporting |
| PDF/rendering | High | Deterministic pagination, font embedding, watermark pipeline |
| Billing/commerce | High | Payment verification, project licenses, subscriptions, refunds |
| Referral/rewards | High | Promo attribution, activation, fraud signals and idempotent reward ledger |
| Database | High | Entitlement ledger, project-license binding, subscription and referral records |
| Security | Very high | Untrusted file processing, authorization, replay, abuse and payment integrity |
| Privacy | High | Data minimization, referral data, script confidentiality and deletion |
| Localization | High | Ethiopic glyphs, mixed script, pagination and round-trip conversion |
| Offline mode | High | Cached entitlement policy, expiry and reconciliation |
| Operations/support | Medium-high | Failed conversions, disputes, refunds, reward reversals and audit tools |

## 3. Architecture decisions required before A3

### A2-01 — Native .talora container

Decide:
- ZIP-based package versus another container.
- Manifest format and schema versioning.
- Structured script representation and canonical serialization.
- Media embedding versus content-addressed asset references.
- Whether revision history is included in every package or exported selectively.
- Integrity/signature model and package compatibility policy.
- Maximum file size, entry count, compression ratio and extraction limits.
- Migration behavior for old package versions.

**Recommendation:** Use a documented ZIP container with a versioned JSON manifest, structured UTF-8 content and explicit asset inventory. Never execute embedded content. Define safe extraction limits and atomic save semantics.

### A2-02 — Canonical internal document model

Decide how screenplay, stage play, television and other writing forms map to a common document model without flattening specialized constructs.

**Recommendation:** Use a canonical semantic document model with typed blocks/nodes and format-specific extension namespaces. Preserve unknown nodes during round trips where safe; warn when a target format cannot represent them.

### A2-03 — Interoperability support matrix

For each importer/exporter, record:
- Format specification and version.
- Licensing and trademark restrictions.
- Parser/writer implementation and dependency license.
- Supported constructs and unsupported constructs.
- Fidelity classification: lossless, near-lossless, lossy, text-only or unsupported.
- Golden test fixtures and round-trip tests.
- Security and malformed-input coverage.

Candidate formats must not be represented as shipped support until verified. Final Draft, Fade In, Celtx and other proprietary ecosystems may require vendor-approved interchange paths. PDF import should be treated as reconstruction/OCR/layout analysis, not guaranteed structural recovery.

### A2-04 — Page counting and Free export policy

Owner-approved rule: Free exports are limited to five pages per export operation.

Unresolved details:
- Page count definition for PDF and DOCX.
- Page size, font, margins, headers/footers and pagination profile used to calculate the count.
- Whether user-selected ranges can be exported and how page selection is enforced.
- What limits apply to non-paginated formats such as TXT and Fountain.
- Whether an export job is bound to an immutable project revision.
- How retry, cancellation and failed jobs affect entitlement consumption.

**Recommendation:** Calculate page limits using the server-approved render profile and bind each export job to project revision, renderer version and export settings. Do not charge/consume a credit for a failed export. Define a separate output-size or content-unit policy for non-paginated formats rather than inventing page counts.

### A2-05 — Reconcile Free, Premium and watermark entitlements

Approved rules:
- Free: five pages per export.
- Premium: all officially supported import/export formats.
- Ten qualified referrals: one full-script export entitlement.
- Referral-earned full-script export is watermarked by default.
- Watermark-free one-time license: one project, including revisions.
- Watermark-free subscriptions: one, three, six and twelve months; longer terms optional.

Required clarification:
- Does Premium include the five-page cap removal for full-project exports? The amendment currently proposes yes.
- Does Premium include watermark-free PDFs? The amendment currently proposes no, keeping watermark removal separate.
- Does the referral full-script export apply to PDF only or to a defined set of formats? The amendment currently scopes it to one complete script export and defaults it to watermarked; initial release should define PDF as the rewarded full-script output unless the Owner decides otherwise.
- Is the referral reward per referring user lifetime, per campaign or per milestone cycle? The amendment currently assumes one-time milestone redemption; final anti-abuse policy must make this explicit.
- What happens to in-progress exports when Premium or a subscription expires?

### A2-06 — Referral qualification and anti-fraud

Define:
- Invitee identity verification method.
- Activation event and time window.
- Referral attribution window and code-entry rules.
- Self-referral, duplicate accounts, device farms, emulator and automation risk handling.
- Household/shared-device and legitimate organization use exceptions.
- Reward pending period and fraud review.
- Reversal and appeal procedure.
- Idempotency key and immutable reward ledger.
- Privacy notice, data minimization and retention.

**Recommendation:** Reward only after server-confirmed account verification and a meaningful activation event, not installation alone. Use risk-based fraud scoring and manual review for suspicious clusters. Avoid invasive device fingerprinting; document any signals and retention.

### A2-07 — Project-bound watermark-free one-time license

Define stable project identity, owner/tenant binding, project transfer, organization ownership, project duplication/fork semantics, account deletion, refunds and payment reversals.

**Recommendation:** License one canonical project identity and its revisions. A duplicated/forked project should be a separate project unless an explicit transfer operation is approved. Preserve already-exported files if a refund or subscription expiration occurs, subject to applicable law and payment dispute policy.

### A2-08 — Subscription and payment architecture

Define payment providers available in target markets, supported payment methods, webhook verification, transaction idempotency, refunds, cancellation, renewals, grace periods, taxes, invoices and currency rules.

**Recommendation:** Keep a provider-neutral entitlement ledger. A verified provider transaction grants or revokes an entitlement through server-side webhook verification. Never trust Android client callbacks alone. Do not finalize prices until provider fees, taxes, FX and local payment accessibility are assessed.

### A2-09 — Offline entitlement behavior

TALORA must remain useful offline, but cannot allow unlimited offline bypass of export restrictions.

Define:
- Which exports are allowed offline.
- Signed, time-bounded entitlement cache.
- Cached subscription expiry and clock rollback handling.
- Offline referral redemption policy.
- Reconciliation and conflict resolution.
- Behavior when the device remains offline beyond authorization expiry.

**Recommendation:** Allow ordinary local editing offline. Use bounded signed entitlement snapshots for limited offline exports; referral rewards and payment changes require online reconciliation. Never let device clock changes extend a paid entitlement.

### A2-10 — Security and privacy

Required controls:
- Sandboxed parsers and conversion workers.
- Archive traversal, decompression bomb, oversized file and parser denial-of-service protections.
- Malware/active-content handling for embedded media and document formats.
- Strict tenant/project authorization for export jobs and license checks.
- Short-lived job tokens and secure temporary-file deletion.
- Script contents excluded from referral analytics and advertising.
- Minimal payment/referral metadata; no unnecessary storage of script contents in billing logs.
- Retention/deletion policy for uploaded source files, conversion artifacts, exports and audit events.

### A2-11 — Localization and fidelity

Build golden test fixtures for Amharic/Ethiopic text, mixed Ethiopic/Latin scripts, punctuation, line wrapping, page breaks, embedded fonts, Unicode normalization and round-trip import/export.

No destructive normalization. The app must disclose when a target format cannot represent a source construct or glyph.

## 4. Proposed data model domains

These are conceptual entities for A2 schema design, not approved database tables:

- `talora_packages`: package format/schema metadata and integrity information.
- `document_revisions`: immutable revision identity and source digest.
- `conversion_jobs`: import/export job, input/output format, renderer/parser version and outcome.
- `conversion_warnings`: unsupported constructs and fidelity warnings.
- `export_entitlements`: entitlement type, scope, status, source and validity.
- `export_usage_ledger`: page counts, format, revision, consumption and idempotency.
- `project_export_licenses`: project-bound watermark-free one-time license.
- `subscriptions`: provider-neutral subscription period and state.
- `payment_transactions`: verified provider references, amounts, currency and reconciliation state.
- `referral_codes`: code ownership, campaign and status.
- `referral_attributions`: invitee attribution and qualification state.
- `referral_rewards`: granted/redeemed/reversed rewards and audit history.

Avoid storing full creative content in entitlement, referral or payment tables. Use stable IDs and cryptographic digests where appropriate.

## 5. Priority-ordered A2 work plan

| Priority | Task | Required output | Closure evidence |
|---:|---|---|---|
| P0 | Confirm entitlement semantics | Decision record for Premium cap, watermark scope and referral output | Owner-approved decisions |
| P0 | Define .talora v1 | Container, manifest, canonical model and migration specification | Format specification and threat model |
| P0 | Define conversion compatibility policy | Import/export matrix and licensing review | Format-by-format support decisions |
| P0 | Define export counting/rendering | Pagination and non-paginated format policy | Deterministic counting specification |
| P1 | Define referral qualification | Activation and anti-fraud policy | Abuse cases and acceptance tests |
| P1 | Define payment and license lifecycle | Provider-neutral billing and entitlement contract | Payment state machine and refund rules |
| P1 | Define offline entitlement policy | Signed cache and expiry/reconciliation contract | Clock rollback and offline threat model |
| P1 | Define security/privacy controls | File-processing, retention and audit architecture | Security review checklist |
| P2 | Define localization test suite | Ethiopic and mixed-script fixtures | Round-trip and rendering acceptance suite |
| P2 | Consolidate A3 backlog | Sequenced implementation epics and dependencies | Traceability matrix |

## 6. Proposed A3 work packages after separate authorization

1. **A3.1 — Native format and canonical model:** implement `.talora` package reader/writer, schema validation, migration and integrity checks.
2. **A3.2 — Conversion framework:** isolated adapters, conversion job lifecycle, capability registry and warning reports.
3. **A3.3 — PDF/document rendering:** deterministic pagination, PDF/DOCX/RTF/TXT/Fountain exporters and watermark pipeline.
4. **A3.4 — Entitlement ledger:** Free page cap, Premium format permissions, usage accounting and idempotent export jobs.
5. **A3.5 — Referral engine:** promo attribution, verified activation, anti-fraud controls and ten-referral reward.
6. **A3.6 — Commerce:** project-bound one-time watermark license and 1/3/6/12-month subscription entitlements after payment-provider approval.
7. **A3.7 — Android experience:** import wizard, conversion review, export settings, reward wallet and subscription/license screens.
8. **A3.8 — Verification:** compatibility fixtures, security tests, payment/referral simulations, offline tests and end-to-end acceptance.

No A3 task is authorized by this assessment.

## 7. Risks and mitigations

| Risk | Severity | Mitigation |
|---|---|---|
| Proprietary format incompatibility or licensing | High | Use official interchange formats; verify rights per adapter |
| Lossy PDF reconstruction | High | Review workflow and explicit fidelity warnings |
| Malicious imported documents | Critical | Sandboxed parsers, resource limits and security testing |
| Referral fraud | High | Verified activation, idempotency, risk review and appeal |
| Payment callback spoofing | Critical | Server-side webhook signature and transaction verification |
| Page-limit bypass | High | Server-authoritative render/count and immutable job snapshot |
| Watermark license abuse across projects | High | Stable project binding and auditable license checks |
| Offline clock manipulation | High | Signed bounded entitlements and server reconciliation |
| Ethiopic rendering defects | High | Golden fixtures and human-reviewed acceptance |
| Unsustainable inference/payment economics | High | Explicit budgets, provider rates and unit-economics analysis |

## 8. A2 exit criteria

A2 can be considered for Owner closure only when:
- Native `.talora` format v1 architecture is fully specified.
- Canonical document model and version migration rules are agreed.
- Every initial import/export target has a lawful support route and fidelity classification.
- Free, Premium, referral and watermark entitlements have no contradictory or ambiguous states.
- Page counting and non-paginated output policy are defined.
- Referral qualification, fraud, reversal and appeal rules are defined.
- Project license ownership/transfer/fork behavior is defined.
- Payment, subscription, refund and offline expiry rules are defined.
- Security, privacy, localization and data-retention controls are specified.
- A3 backlog, dependencies and acceptance tests are traceable to approved requirements.

## 9. Current gate status

| Gate | Status |
|---|---|
| A1 amendment requirements | OWNER-APPROVED |
| A2 impact assessment | DRAFT COMPLETE; technical decisions open |
| A2 closure | NOT CLOSED |
| A3 implementation | NOT AUTHORIZED |
| PR merge | NOT AUTHORIZED |
| A4 verification | NOT STARTED |
| A5 deployment | NOT AUTHORIZED |

## 10. Immediate next action

Complete the remaining A2 decision records, beginning with entitlement semantics (Premium export cap and watermark scope), `.talora` package/canonical document model, supported-format licensing/fidelity matrix, and referral/payment lifecycle. Then present a consolidated A2 closure package for separate Owner approval.


## 11. Superseding evidence reconciliation — 19 November 2026

This section records the current A2 evidence position and supersedes earlier open-decision wording in this impact assessment. The detailed disposition is in `TALORA_A2_Evidence_Reconciliation_and_Closure_Assessment_v1.0.0.md`.

- **A1:** The original 11-document A1 specification was explicitly approved on 18 November 2026 at immutable commit `599bcb4a71bd7ca7e1b5945c8034f29ecff4bc06`. The carried-forward baseline record is corrected; A1 is not reopened.
- **ODR-11:** Owner-approved 19 November 2026. TXT and Fountain Free exports use the deterministic TALORA canonical pagination profile, include only complete semantic content fitting pages 1–5, do not invent page breaks or truncate mid-block, and disclose partial range/boundary.
- **ODR-13:** Public format evidence is reconciled. A bounded v1 support-matrix recommendation excludes FDX, Fade In and Celtx native formats pending lawful, documented and testable routes. Owner confirmation of the exact v1 list and exact dependency/fixture licensing remain open.
- **ODR-14:** Provider-neutral commerce architecture is reconciled. Chapa and ArifPay remain due-diligence candidates; Telebirr C2B is an alternative. No provider or price is selected. Written merchant, vendor and legal validation remains mandatory before payment implementation.
- **ODR-15:** Resolved by the A1 approval record update.
- **TALORA–IROKA:** D01–D09 are recorded exactly as Owner-approved on 19 November 2026. D01 reconciliation was performed, but no single compatible immutable IROKA component graph was identified. The integration baseline remains unresolved. D02–D09 constrain any future integration but do not authorize implementation.

**Current A2 status:** PARTIAL / NOT READY FOR OWNER CLOSURE APPROVAL. The decisive blocker is D01's unresolved compatible IROKA graph. ODR-13's bounded v1 support matrix is Owner-approved; exact parser/writer and fixture licensing remains a pre-implementation gate. A3, merge and A5 remain unauthorized.


- **ODR-13 Owner decision (19 November 2026):** The Owner approved the bounded v1 support matrix in the A2 closure assessment. The initial v1 promise excludes Final Draft FDX, Fade In native files and Celtx native/project files until lawful, documented and tested interchange routes are verified. This closes the product-scope decision only; exact parser/writer, transitive dependency, fixture and distribution-license reviews remain mandatory before implementation.
