# TALORA A2 Evidence Reconciliation and Closure Assessment

- **Document ID:** TALORA-A2-ERA-001
- **Version:** 1.0.0
- **Assessment date:** 19 November 2026
- **Repository:** `akefadaka-git/talora`
- **Assessment branch:** `docs/a1-import-export-referral-entitlements`
- **A1 approved baseline:** `599bcb4a71bd7ca7e1b5945c8034f29ecff4bc06`
- **TALORA main reference:** `2562962fc44636cdebfa49c7bf3c3cdcb618751c`
- **IROKA main reference inspected:** `1aea1447eadf1d827d702fd6c4034fa058aac21e`
- **Status:** **A2 PARTIAL / NOT READY FOR OWNER CLOSURE APPROVAL**
- **Implementation:** NOT AUTHORIZED
- **Merge:** NOT AUTHORIZED
- **A5 deployment:** NOT AUTHORIZED

## 1. Executive determination

The A2 evidence reconciliation has been completed to the extent supported by the current repository and public evidence. The historical A1 approval discrepancy is resolved; ODR-11 is approved and recorded; ODR-13 format evidence and ODR-14 payment architecture have been reconciled; all nine TALORA–IROKA Owner dispositions D01–D09 have been transcribed into the integration contract and decision register.

**A2 is not ready for closure.** The decisive blocker is D01: the inspected IROKA component branches do not form one compatible, immutable integration graph. The current IROKA main reference is not automatically approved, and the candidate G03, G05, G08 and CIW workstreams have divergent histories and unresolved owning-domain interfaces. No exact IROKA service/API implementation baseline is selected or verified. ODR-13 also still requires Owner confirmation of the proposed v1 format support matrix.

This is an evidence-based NOT-CLOSED finding, not a request to start A3. The work completed here is documentation and read-only reconciliation only.

## 2. Governance and scope

This assessment covers:
- Reconciliation of the approved A1 baseline record.
- Owner decisions ODR-01–ODR-15 for native format, conversion and export entitlements.
- Public format and Ethiopia payment-provider evidence.
- TALORA–IROKA A2 dispositions D01–D09.
- IROKA component ownership, branch ancestry, API readiness and dependency compatibility.
- A2 exit criteria and remaining closure blockers.

This assessment does not:
- Implement or change application/runtime source code.
- Select or activate a payment provider or model provider.
- Merge any branch or pull request.
- Provision credentials, production keys or paid services.
- Authorize A3, A4 execution, or A5 deployment.

## 3. A1 baseline reconciliation — VERIFIED

The user explicitly approved the complete 11-document TALORA A1 specification on **18 November 2026 (UTC)**.

| Evidence | Value |
|---|---|
| Repository | `akefadaka-git/talora` |
| Original A1 documentation branch | `docs/a1-product-definition` |
| Immutable approved baseline commit | `599bcb4a71bd7ca7e1b5945c8034f29ecff4bc06` |
| Main reference at original baseline | `2562962fc44636cdebfa49c7bf3c3cdcb618751c` |
| Owner action | Explicit “Approve” of the 11-document A1 specification |
| Approval effect | A1 approved; scope and decisions frozen; later changes require authoritative reason and explicit approval |

The carried-forward file previously said “Draft prepared for Owner review; not frozen.” That wording was inconsistent with the recorded Owner approval. The A2 branch copy of `docs/a1-product-definition/00-A1-BASELINE.md` has now been corrected to state OWNER-APPROVED / FROZEN and records the immutable approved commit. The historical commit was not rewritten. The later native-format/import/export/entitlement amendment is a separately approved amendment and is not retroactively folded into the original A1 commit.

**ODR-15: RESOLVED.** A1 is not reopened.

## 4. TALORA native format, export and entitlement decisions

### 4.1 Owner-approved decisions

ODR-01 through ODR-12 are recorded as Owner-approved technical defaults. Key constraints:
- Native project extension: `.talora`.
- Versioned ZIP package, JSON manifest and canonical typed document model.
- Self-contained package; current revision mandatory; revision history selectable.
- Free export cap: five pages per export.
- Premium has access to all officially supported formats and removes the five-page cap while active.
- Premium does not automatically remove PDF watermark.
- Ten verified referrals: one lifetime reward per account for one full-script watermarked PDF export.
- Watermark-free one-time license: one selected project and its revisions; PDF only initially.
- Watermark-free subscription terms: 1, 3, 6 and 12 months; longer terms optional.
- Failed/cancelled exports do not consume entitlements; completed jobs are idempotently accounted.
- Signed bounded offline entitlement cache; numeric validity period remains subject to security review.

### 4.2 ODR-11 — VERIFIED APPROVED

On 19 November 2026, the Owner approved the canonical-pagination policy for TXT and Fountain:
- Apply a fixed, named, versioned TALORA canonical pagination profile before serialization.
- Export only complete semantic content fitting within canonical pages 1–5 for Free.
- Do not invent page-break markers or artificial pagination in TXT/Fountain.
- Do not truncate within a semantic block; stop before the next complete block and disclose the included range and stopping boundary.
- Bind the export to immutable revision, document type, profile ID, renderer version and settings digest.
- Failed/cancelled jobs do not consume; completed jobs are idempotently accounted.
- Amharic/Ethiopic, mixed-script, screenplay and stage-play golden tests are required before release.

The concrete page geometry, font metrics, renderer and golden fixtures are not implemented or verified. Their future design and tests are not claimed complete.

## 5. ODR-13 — Format compatibility and licensing reconciliation

### Evidence findings

- Fountain is published as a plain-text screenplay markup language. Its own FAQ explains that pagination is generated by the writing application and that generic TXT import may lose styling, dual dialogue and centred text.
- Fountain's official developer page offers reference code under a permissive MIT License. This is a potential lawful reference path, not a blanket license clearance for an uninspected repository checkout, dependencies, fixtures or trademarks.
- Microsoft documents DOCX as Office Open XML and publishes implementation information for ISO/IEC 29500 and Office extensions. A bounded OOXML subset is appropriate; universal fidelity is not implied.
- Microsoft describes TXT as plain text without formatting, RTF as rich-text interchange, and PDF as fixed-layout.
- Final Draft's official support documentation says it does not directly import `.fountain` or `.spmd`; third-party-generated FDX may not be genuine Final Draft XML or behave as expected.
- No parser/writer dependency was selected, installed or license-audited. No external application was executed and no round-trip test was performed.

### Recommended bounded v1 support matrix — OWNER CONFIRMATION REQUIRED

| Format | Recommended v1 scope | Fidelity / restriction |
|---|---|---|
| `.talora` | Native full-project import/export | Exact for supported schema versions |
| PDF | Export P0; import deferred to reviewed reconstruction | PDF import is L3 reconstruction/OCR, not guaranteed semantic recovery |
| TXT | Import/export P0 | Text-level; no semantic round-trip guarantee |
| Fountain | Import/export P1 | Supported syntax subset; explicit conversion warnings; no intrinsic pagination |
| DOCX | Import/export P1, bounded OOXML subset | L1/L2 by feature; macros, active content and unsafe external relationships excluded |
| RTF | Export P1; import deferred until safe parser validation | Rich text but screenplay semantics not guaranteed |
| Final Draft FDX | Excluded from initial v1 promise | Reconsider only after lawful official route, versioned fixtures and compatibility review |
| Fade In/Celtx native files | Excluded from initial v1 promise | Reconsider only after documented lawful and testable interchange route |
| HTML/other | Later assessment | Defined semantic subset only |

**ODR-13 disposition:** Evidence reconciliation complete; exact v1 support list remains a recommendation pending Owner confirmation. Every exact parser/writer library, transitive dependency, fixture and distribution obligation must be reviewed before implementation. No proprietary format is cleared by this report.

## 6. ODR-14 — Ethiopia payment evidence and architecture

The current NBE public register lists Arif Pay, Chapa, Santim Pay, Addis Pay, Yagout Pay, Fenanpay and StarPay as commercialized payment-gateway operators. Telebirr and M-PESA are listed as commercialized mobile-money issuers. The NBE register is regulatory-status evidence only; it does not establish TALORA merchant acceptance, pricing, settlement, enabled payment rails or recurring-billing eligibility.

Current public technical evidence:
- Chapa publishes test/live environments, server-side transaction verification and v2 webhook HMAC-SHA256 verification over the exact raw request body, with idempotent event handling and fulfillment verification.
- ArifPay publishes sandbox documentation, C2B/checkout flows, transaction verification and transaction-lifecycle/webhook documentation.
- Telebirr has an official C2B integration guide.

A provider-neutral transaction/entitlement ledger remains the A2 architecture. Chapa and ArifPay are primary due-diligence candidates; Telebirr C2B is a direct-wallet alternative; SantimPay and other NBE-commercialized operators may be compared. **No provider or price is selected.**

Mandatory pre-payment-implementation evidence:
- Written TALORA merchant eligibility and KYC requirements.
- Fees, settlement currency/timing, refund/reversal/chargeback handling.
- Supported payment methods and currencies for the actual merchant.
- One-off 1/3/6/12-month entitlement purchase support; recurring billing only if provider capability, consent, cancellation and renewal notices are verified.
- Sandbox access, webhook security/retry guarantees, API verification, SLA/support and data-protection terms.
- Qualified legal review of applicable NBE directives, tax, consumer, FX and payment obligations.

No account was created, no sandbox key used, no live payment initiated and no paid activation performed.

**ODR-14 disposition:** RECONCILED AT A2 ARCHITECTURE LEVEL; provider selection and merchant/legal due diligence remain mandatory before payment implementation.

## 7. TALORA–IROKA integration reconciliation

### 7.1 Nine binding Owner dispositions — 19 November 2026

| ID | Exact Owner value | Binding interpretation |
|---|---|---|
| D01 | `RECONCILE` | Reconcile exact components, dependencies and compatible immutable commits before selecting a baseline; do not adopt current main or any candidate branch automatically |
| D02 | `API` | Private authenticated service API only; no distributable SDK |
| D03 | `API_ONLY` | API/service integration only; no source copying, vendoring, modification, redistribution, sublicensing or SDK packaging |
| D04 | `DEV_FIRST` | Hosted development/test first; no production topology, production creative data or residency claim until separately reviewed |
| D05 | `FAKE_LOCAL` | Deterministic fake/local adapter only; live external providers disabled |
| D06 | `HARD_CAPS` | Hard preflight quotas and ETB ceilings; no silent overrun, auto-top-up or paid activation without numeric approval; external paid spend remains zero until approved |
| D07 | `OPTIN` | Ephemeral task context by default; persistent project memory explicit opt-in, project-scoped and deletable |
| D08 | `REVIEWED` | Amharic-first; no full-workflow claim until rights-cleared/reviewed corpus, competent reviewers and measurable acceptance tests pass |
| D09 | `MEASURE_FIRST` | No production SLO or support commitment until topology and operational performance are measured and ownership defined |

The reconciled integration contract is v0.1.2. The decision register is v0.1.1. Both are documentation-only A2 records.

### 7.2 IROKA repository and licensing facts

- IROKA main reference inspected: `1aea1447eadf1d827d702fd6c4034fa058aac21e`.
- Current IROKA main README states that no explicit open-source license is granted and that public visibility does not grant permission to copy, modify, redistribute or sublicense project material.
- The earlier v0.1.1 contract wording that referred to a `LicenseRef-Proprietary` marker has been corrected in v0.1.2 to reflect the current README evidence.
- D03 authorizes API/service integration only within this TALORA scope. It does not grant source reuse, SDK distribution or sublicensing.
- The integration contract is a proposed API contract. Its endpoints and schemas are not evidence that an IROKA API runtime currently exists.

### 7.3 Component graph and branch-lineage evidence

Branch comparisons were performed against IROKA main reference `1aea1447eadf1d827d702fd6c4034fa058aac21e`.

| Workstream | Candidate branch | Compare result | Finding |
|---|---|---|---|
| G05 approved memory contract | `architecture/g05-owner-approved-contract-v1` | Diverged; 1 ahead / 3 behind; merge base `1a6f30cc3e64b8551f8e725c4cad8fcc3fa2c472` | Owner-approved logical contract; implementation not started |
| G05 + G01/G02/G11 amendment | `architecture/g05-g01-g02-g11-integration-contracts` | Diverged; 4 ahead / 3 behind; merge base `1a6f30cc3e64b8551f8e725c4cad8fcc3fa2c472` | Design decisions approved; domain confirmation and implementation prerequisites remain open |
| G08 orchestration A2 | `architecture/g08-orchestration-a2-contract` | Diverged; 3 ahead / 302 behind; merge base `143311a655be68acea2700650b7f88c9fb085526` | Stale lineage; tenant-context and authority dependencies unresolved |
| CIW A2 contracts | `docs/ciw-a2-integration-contracts` | Diverged; 14 ahead / 302 behind; merge base `143311a655be68acea2700650b7f88c9fb085526` | Documentation-only A2 candidate; not an integration baseline |
| G03 Brain A2 contracts | `architecture/brain-a1-a2-proposal` | Diverged; 26 ahead / 118 behind; merge base `a709fc473b23494839417449e34bebf4cb8be77a` | Owner-approved design-only contract; no merged runtime |
| TALORA integration docs | `docs/talora-iroka-a2-reconciliation-v0.1.1` | 3 ahead / 0 behind main | Documentation-only branch |

Additional dependency findings:
- G01 authenticated principal does not itself resolve tenant membership or conversation ACLs.
- G02 signed/persisted policy lifecycle and canonical organization reviewer authority are not established as compatible owning-domain interfaces for TALORA.
- G05's logical contract is not an implemented memory API. The G05/G01/G02/G11 amendment leaves canonical organization authority and reviewer delegation prerequisites open; the affected operations must fail closed.
- G08 A2 draft records a missing authoritative tenant resolver and stale Kernel lineage.
- CIW A2 contract records that the public Kernel API does not supply tenant membership, session lifecycle, conversation persistence or streaming.
- CIW PR #23 candidate head `69f3c181791a8dd3c5baf3d18d4556f8f316b1a1` was based on `813b24cbf81f049f2675fbb5492bdc9f262a9d87`; comparison to current main was DIVERGED (172 ahead / 22 behind). It is not adopted.
- G04 executable API and G06 perception integration were not verified in the inspected Brain A2 report.

**D01 result: RECONCILIATION PERFORMED; NO COMPATIBLE IMMUTABLE GRAPH SELECTED.** No branch is selected as the integration baseline. No arbitrary branch composition, cherry-pick, merge or source vendoring is permitted.

## 8. A2 exit assessment

| Exit criterion | Status | Evidence / remaining work |
|---|---|---|
| Original A1 approval traceable and frozen | **VERIFIED-CLOSED** | Owner approval 18 Nov 2026; immutable baseline SHA recorded; carried-forward record corrected |
| Native `.talora` architecture | **PARTIAL** | Owner-approved direction; schema, migrations and limits remain future engineering specification |
| Canonical document model | **PARTIAL** | Design direction approved; exact schema and migration profile not implemented or verified |
| Free/Premium/referral/watermark entitlement semantics | **VERIFIED-CLOSED FOR A2 DEFAULTS** | ODR-01–ODR-12 approved; operational tests remain A4 |
| ODR-11 non-paginated export policy | **VERIFIED-CLOSED FOR POLICY** | Owner approval recorded; profile and golden tests remain future work |
| Format compatibility and licensing | **PARTIAL** | Evidence reconciled; v1 matrix recommendation requires Owner confirmation; exact libraries/fixtures require review |
| Payment architecture | **VERIFIED-CLOSED FOR PROVIDER-NEUTRAL DESIGN** | No provider selected; written merchant/vendor/legal verification remains pre-implementation gate |
| Referral lifecycle and anti-fraud | **PARTIAL** | Main policy recorded; detailed fraud thresholds, review/appeal and operational controls require specification |
| Project license and subscription lifecycle | **PARTIAL** | Core scope recorded; refunds, chargebacks, account deletion, renewals and transfer edge cases require specification |
| Offline entitlement | **PARTIAL** | Signed bounded cache approved; numeric validity and security verification remain open |
| Security/privacy and file-processing | **PARTIAL** | Threat controls specified; independent review, dependency/SBOM and implementation tests not run |
| Amharic acceptance corpus | **PARTIAL** | Required corpus/test classes identified; corpus rights, reviewers and measured acceptance thresholds not approved |
| TALORA–IROKA D01 compatible immutable graph | **UNRESOLVED / BLOCKED** | No compatible graph selected; candidate branches diverged and APIs/authorities remain missing |
| TALORA–IROKA D02–D09 | **VERIFIED-CLOSED AS OWNER DISPOSITIONS** | All exact values recorded; downstream implementation and operational evidence remain blocked |
| Traceable A3 backlog | **PARTIAL** | A2 work packages identified; no A3 execution authorized |

## 9. Remaining closure blockers

1. **D01 — IROKA integration graph:** obtain exact branch HEADs, dependency manifests, public API surfaces, ancestry and compatible test/security evidence for the proposed API service graph. Do not choose an integration baseline until this passes.
2. **ODR-13 — v1 format support matrix:** Owner must approve or amend the bounded v1 format list. Exact dependency and fixture licensing remains mandatory before implementation.
3. **A2 completeness for entitlements:** close detailed refund/chargeback, renewal/cancellation, fraud review/appeal, account deletion and organization/project transfer state transitions as design decisions.
4. **Offline/security numeric boundaries:** approve a numeric offline entitlement validity policy only after security threat analysis; define the approved fallback behavior.
5. **Amharic assurance:** approve rights-cleared corpora, competent reviewers and measurable acceptance thresholds before claiming full-workflow support.
6. **External vendor/legal gates:** verify payment provider merchant eligibility, terms and applicable Ethiopian legal requirements before payment implementation.

These blockers are not waived by the current report. They are not A3 tasks authorized for execution.

## 10. A3 backlog traceability — planning only

The following are candidate work packages for a future, separately authorized A3 plan. They are not started:

| Candidate | Scope | Required A2/A3 entry gates |
|---|---|---|
| A3.1 Native package/model | `.talora` reader/writer, canonical schema, migration and integrity | Owner-confirmed schema; security/resource limits; dependency license |
| A3.2 Conversion framework | Format adapters, sandboxing, capability matrix, conversion reports | Owner-confirmed v1 support matrix; exact dependency/fixture review |
| A3.3 Rendering/export | Pagination profiles, PDF, TXT/Fountain/DOCX/RTF and watermarking | ODR-11 profile; Amharic golden corpus; renderer selection |
| A3.4 Entitlements | Free cap, Premium format permissions, usage ledger and idempotency | Final entitlement state machine and acceptance tests |
| A3.5 Referral engine | Attribution, verified activation, fraud, reward and appeal ledger | Fraud thresholds, review/appeal and privacy retention policy |
| A3.6 Commerce | Provider adapter, one-time project license and 1/3/6/12-month entitlements | Merchant/legal due diligence, provider choice, refund/cancellation policy |
| A3.7 Android experience | Import review, export settings, entitlement and referral UI | Approved product behavior and API contracts |
| A3.8 IROKA API integration | Private authenticated API, fake/local provider, scoped task/result contracts | D01 graph; D02–D09; cross-project API rights; tenant/policy/memory/audit API verification |

No work package may start without a separate explicit Owner authorization identifying repository, scope, branch and starting SHA.

## 11. Decision and stage-gate recommendation

**A2 closure recommendation: DO NOT CLOSE YET.** The evidence work is materially advanced, but D01 remains unresolved and ODR-13's launch format matrix is not Owner-frozen. The A2 integration baseline is therefore not complete.

The appropriate next action remains read-only A2 work: reconcile exact IROKA component/API commits and dependencies, then present a single compatible baseline proposal; obtain Owner confirmation of the v1 format support matrix; close the remaining entitlement/security/localization design details.

- **A2:** PARTIAL / NOT CLOSED.
- **A3:** NOT AUTHORIZED.
- **Merge:** NOT AUTHORIZED.
- **A4:** NOT STARTED.
- **A5:** NOT AUTHORIZED.

No repository main branch was modified by this assessment. No implementation, merge, paid service activation, production provider activation or deployment was performed.
