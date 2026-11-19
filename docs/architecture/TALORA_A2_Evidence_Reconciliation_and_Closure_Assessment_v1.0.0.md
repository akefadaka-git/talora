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

**A2 is not ready for closure.** D01 has since been VERIFIED-CLOSED for selection and freeze of the bounded development-only IROKA baseline at source commit 8056cea136f42436d896078046796eba2129092b. This does not establish compatibility of the wider IROKA component graph or production readiness. Remaining A2 blockers are detailed entitlement/refund and referral-review decisions, offline-cache numeric validity/security review, rights-cleared Amharic acceptance corpus/reviewer approval, and exact dependency/fixture/distribution licensing evidence. ODR-13 product scope is Owner-approved; its dependency clearance remains a pre-implementation gate.

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

### Owner-approved bounded v1 support matrix — approved 19 November 2026

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

**ODR-13 disposition — APPROVED:** The Owner approved this bounded v1 support matrix on 19 November 2026. FDX, Fade In and Celtx native/project formats are excluded from the initial v1 support promise pending lawful, documented and tested interchange routes. Every exact parser/writer library, transitive dependency, fixture and distribution obligation must still be reviewed before implementation. No proprietary format is cleared by this report.

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
- CIW PR #23 candidate head `69f3c181791a8dd3c5baf3d18d4556f8f316b1a1` was based on `813b24cbf81f049f2675fbb5492bdc9f262a9d87`; comparison to current main was DIVERGED (172 ahead / 25 behind). It is not adopted.
- G04 executable API and G06 perception integration were not verified in the inspected Brain A2 report.

**D01 result: RECONCILIATION PERFORMED; NO COMPATIBLE IMMUTABLE GRAPH SELECTED.** No branch is selected as the integration baseline. No arbitrary branch composition, cherry-pick, merge or source vendoring is permitted.

## 7.4 Exact runtime candidate heads and dependency reconciliation

The exact candidate branch heads were retrieved from GitHub, and workspace manifests/lockfiles were inspected.

| Domain candidate | Exact branch HEAD | Comparison to IROKA main `1aea1447eadf1d827d702fd6c4034fa058aac21e` | Workspace/dependency evidence |
|---|---|---|---|
| G01/G02/G11 Kernel A3 | `bb8b9f0ee81b59e793c30cc5c9a0357d3ed48e4a` | 0 ahead / 7 behind; branch is an ancestor of main | PR #13 implementation is in main lineage; main still lacks the proposed TALORA API service crate |
| G05 reviewer/SQLite | `47edea227fdf1dc7b6ac1667b7e7f9cbb5b4cf39` | 155 ahead / 3 behind; merge base `1a6f30cc3e64b8551f8e725c4cad8fcc3fa2c472` | Workspace adds G04/G05; G05 uses SQLite `rusqlite 0.32.1`; Cargo.lock blob `8944877dd6d2ac88a21e02c0ce84da9873033909` |
| G05 PostgreSQL/G11 multi-worker | `2bfac619b9601330b3343f86277bb5ba29ae22b2` | 334 ahead / 3 behind; same merge base `1a6f30cc3e64b8551f8e725c4cad8fcc3fa2c472` | Adds PostgreSQL `0.19.12`, `postgres-native-tls 0.5.0`, `native-tls 0.2.12`, `tokio-postgres 0.7.18` alongside SQLite; Cargo.lock blob `e01999aeab00fa657ac4f5d02ec36d4305ad880d` |
| G08 durable coordinator | `2cfcf8db9437824cc8181ad8af1e8d819b936e2c` | 81 ahead / 3 behind; same merge base `1a6f30cc3e64b8551f8e725c4cad8fcc3fa2c472` | G08 modules added to Kernel on separate branch; root workspace/lockfile matches main, but source ancestry is divergent |
| CIW answer-only chat | `69f3c181791a8dd3c5baf3d18d4556f8f316b1a1` | 172 ahead / 25 behind; merge base `813b24cbf81f049f2675fbb5492bdc9f262a9d87` | Adds `iroka-conversation`, SQLite and separate workspace; Cargo.lock blob `c6caa987bb11aa8342bd5dd7981fefb646e0c7ac` |
| G03 Brain core | `9d68b580e06bb8335a626524d64b345c3a5a38aa` | 10 ahead / 3 behind; merge base `1a6f30cc3e64b8551f8e725c4cad8fcc3fa2c472` | Adds `iroka-brain` with `serde`/`serde_json`; Cargo.lock blob `5171bde5fd4a34737d20b3d0622f45367cc12025` |

The current main workspace is intentionally small: root `Cargo.toml` blob `d64d3143b762589346fb19ee77e6f40d4af8c77c` lists only `crates/iroka-kernel`; `Cargo.lock` blob is `ad864ae2b098c1cdea7a81d2d2fc8cdcf9fddf63`. It does not contain the proposed TALORA-facing service API crate.

The two G05 runtime candidates are mutually DIVERGED: G05 SQLite/reviewer versus G05 PostgreSQL is 210 ahead / 31 behind, merge base `c57e4298005222ce476f685972062983c78d626f`. G05 PostgreSQL versus G08 is 81 ahead / 334 behind; G05 PostgreSQL versus Brain A3 is 10 ahead / 334 behind; CIW A3 versus G05 PostgreSQL is 356 ahead / 172 behind. The lockfiles differ. Some crates share versions, but that does not establish compatible workspace membership, trait/API contracts, tenant authority, or runtime behavior.

**D01 conclusion strengthened:** exact candidate heads and dependency manifests are now identified, but no compatible immutable graph exists as an evidenced, tested, approved unit. The proper status is **RECONCILIATION COMPLETE — BASELINE NOT SELECTABLE / BLOCKED**, not “baseline selected.” A future read-only assessment must prove the selected graph's dependency resolution, API surfaces, tenant/policy/memory/audit authority boundaries and exact-head tests before the Owner can consider freezing it.

## 7.5 Exact-HEAD CI evidence

GitHub Actions run records were retrieved for the exact candidate commits. All listed runs completed successfully. The job covered formatting, compilation, complete unit/contract tests, production-feature exclusion checks and Clippy. CIW additionally checked browser JavaScript syntax and patch whitespace.

| Candidate exact HEAD | Workflow run | Verified result |
|---|---:|---|
| Kernel A3 `bb8b9f0ee81b59e793c30cc5c9a0357d3ed48e4a` | [42566869592](https://github.com/akefadaka-git/iroka/actions/runs/42566869592) | **PASS** |
| G05 reviewer/SQLite `47edea227fdf1dc7b6ac1667b7e7f9cbb5b4cf39` | [42957299023](https://github.com/akefadaka-git/iroka/actions/runs/42957299023) | **PASS** |
| G05 PostgreSQL/G11 `2bfac619b9601330b3343f86277bb5ba29ae22b2` | [43240878046](https://github.com/akefadaka-git/iroka/actions/runs/43240878046), [43240392654](https://github.com/akefadaka-git/iroka/actions/runs/43240392654) | **PASS / PASS** |
| G08 coordinator `2cfcf8db9437824cc8181ad8af1e8d819b936e2c` | [43230367894](https://github.com/akefadaka-git/iroka/actions/runs/43230367894) | **PASS** |
| CIW answer-only `69f3c181791a8dd3c5baf3d18d4556f8f316b1a1` | [42562324737](https://github.com/akefadaka-git/iroka/actions/runs/42562324737) | **PASS** |
| Brain core `9d68b580e06bb8335a626524d64b345c3a5a38aa` | [42576912231](https://github.com/akefadaka-git/iroka/actions/runs/42576912231) | **PASS** |

The evidence is candidate-specific. It does not prove that separate branches can be composed, that their workspace dependencies and API/tenant/policy/memory/audit interfaces agree, or that a combined TALORA service exists. No combined-graph workflow run was found or executed. **Integrated A4 exact-head verification: NOT RUN.**

## 7.6 Supplemental D01 exact API and immutable-graph verification — 19 November 2026

This pass inspected the exact candidate workspace manifests, Kernel public exports, G05 HTTP host, CIW HTTP routes and the separate Brain fake adapter. It was read-only; no source, manifest, lockfile, workflow or implementation branch was changed.

### Exact candidates and API boundaries

| Candidate | Exact HEAD | Verified boundary |
|---|---|---|
| IROKA main | 1aea1447eadf1d827d702fd6c4034fa058aac21e | Root Cargo workspace contains only iroka-kernel; no TALORA task service |
| G03/G08 | 0680337b7dd0966374acd2649cd9197e56b8d92a | Kernel exports inference/premise/uncertainty/workflow graph; workflow graph validates structure but does not schedule or execute |
| G05/G02/G11 | 8f7ddb9e9dd2b796cd303ba645a1a3c843eb3810 | Workspace contains kernel + G05 memory; G05 manifest includes SQLite and PostgreSQL/TLS dependencies; HTTP host exposes only POST /v1/g05/execute and GET /healthz |
| BRAIN/G05/G01/G02/G11 | 0968221b55563a9aacbec4fa499fdac7679e9915 | Workspace contains kernel + G04 contract + SQLite G05; external G01/G02 authority-provider dependencies remain fail-closed; tenant resolver is single-node |
| Brain fake/local | 9d68b580e06bb8335a626524d64b345c3a5a38aa | Implements ModelAdapter and DeterministicFakeAdapter, but TaskEnvelope.validate() rejects populated principal, tenant or policy context and non-empty domain input refs; no HTTP service |
| CIW | 69f3c181791a8dd3c5baf3d18d4556f8f316b1a1 | Browser conversation and generation/event routes; not private TALORA task API |

### Concrete source-level incompatibility

The G05/G02/G11 memory crate imports Kernel public symbols G05ActivationDecision, G05ActivationTuple, OrganizationAuthorityResolver, ReviewerKeyBindingResolver, ReviewerProofVerifier, SignedReviewerProof, TenantContext and VerifiedReviewerProof. The G03/G08 Kernel public lib.rs at 0680337b... exports none of these. Conversely, G03/G08 exports InferenceRulesOutput, PremiseModel and workflow_graph, absent from the G05/G02/G11 Kernel lib.rs.

The G03/G08 and G05/G02/G11 histories are diverged: 378 unique commits versus 391 unique commits from common base 1a6f30cc3e64b8551f8e725c4cad8fcc3fa2c472. Their compare includes shared changed files Cargo.toml, Cargo.lock, Kernel lib.rs, identity_crypto.rs, security_store.rs and trust_lifecycle.rs. Brain fake/local versus G03/G08 is also diverged (378 / 10 commits). These are incompatible public API surfaces, not merely separate branch names.

### Exact-head verification

The exact heads 0680337b..., 8f7ddb9e..., 0968221b..., 9d68b580... and d51a971d... returned no combined status checks and no PR-triggered workflow runs in the available first-page lookup. Status is **NOT VERIFIED**, not FAIL. Successful workflows on older G05 HTTP/PostgreSQL or CIW predecessors do not transfer to these exact heads. No combined workspace resolution, build/test, migration test, SBOM or end-to-end private API run was performed.

### D01 conclusion

No compatible immutable graph can be selected and verified for the approved API-only TALORA scope. G03/G08 and G05/G02/G11 Kernel exports are incompatible; the isolated fake adapter rejects the trusted context and domain input references required by TALORA; and no candidate implements the full private capabilities/task/status/events/cancel/memory API.

**D01 remains UNRESOLVED — NO SELECTABLE, VERIFIED IMMUTABLE INTEGRATION BASELINE.** This is the result of the requested read-only reconciliation, not authorization to compose source branches. D03 remains API_ONLY; no source copying, vendoring, modification, SDK distribution or redistribution is authorized by this assessment.

## 8. A2 exit assessment

| Exit criterion | Status | Evidence / remaining work |
|---|---|---|
| Original A1 approval traceable and frozen | **VERIFIED-CLOSED** | Owner approval 18 Nov 2026; immutable baseline SHA recorded; carried-forward record corrected |
| Native `.talora` architecture | **PARTIAL** | Owner-approved direction; schema, migrations and limits remain future engineering specification |
| Canonical document model | **PARTIAL** | Design direction approved; exact schema and migration profile not implemented or verified |
| Free/Premium/referral/watermark entitlement semantics | **VERIFIED-CLOSED FOR A2 DEFAULTS** | ODR-01–ODR-12 approved; operational tests remain A4 |
| ODR-11 non-paginated export policy | **VERIFIED-CLOSED FOR POLICY** | Owner approval recorded; profile and golden tests remain future work |
| Format compatibility and licensing | **PARTIAL** | Bounded v1 product scope Owner-approved; exact libraries/fixtures and distribution obligations require review |
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

1. **D01 — IROKA integration graph:** exact candidate branch HEADs, workspace manifests, lockfiles and ancestry have now been captured. They show divergent G05/G08/CIW/G03 code lines and no TALORA-facing API service in current main. Further read-only build/API/dependency/security compatibility assessment is required before any immutable graph can be proposed. Do not choose an integration baseline until that evidence passes.
2. **ODR-13 — v1 format support matrix:** Owner approval recorded 19 November 2026. Exact dependency and fixture licensing remains mandatory before implementation.
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

**A2 closure recommendation: DO NOT CLOSE YET.** The evidence work is materially advanced and ODR-13's bounded v1 launch format matrix is Owner-approved. D01 remains unresolved: no compatible immutable IROKA integration baseline has been established. The A2 integration baseline is therefore not complete.

The appropriate next action remains read-only A2 work: reconcile exact IROKA component/API commits and dependencies, then present a single compatible baseline proposal; close the remaining entitlement/security/localization design details.

- **A2:** PARTIAL / NOT CLOSED.
- **A3:** NOT AUTHORIZED.
- **Merge:** NOT AUTHORIZED.
- **A4:** NOT STARTED.
- **A5:** NOT AUTHORIZED.

No repository main branch was modified by this assessment. No implementation, merge, paid service activation, production provider activation or deployment was performed.


## 12. Owner-approved D01 bounded development baseline — 19 November 2026

**Owner decision: APPROVED.** The Owner explicitly approved freezing the exact bounded development-only TALORA integration baseline proposed in the D01 reconciliation. This approval is limited to the existing immutable IROKA service-core snapshot and does not authorize new A3 implementation, source composition, merge, deployment or production use.

**Frozen source-code baseline:** IROKA repository `akefadaka-git/iroka`, branch `integration/talora-a3-private-api`, verified code commit `8056cea136f42436d896078046796eba2129092b`.

**Current documentation-only branch snapshot at approval:** `00b323750fb120f80d56191069d10a825e66dc41`. The two commits between the verified code head and this snapshot change only the implementation-status document. The source-code baseline remains `8056cea...`.

**Base:** IROKA main `1aea1447eadf1d827d702fd6c4034fa058aac21e`. **PR:** [#129](https://github.com/akefadaka-git/iroka/pull/129), OPEN / DRAFT / UNMERGED.

**Immutable verification evidence:** [GitHub Actions run 44457815902](https://github.com/akefadaka-git/iroka/actions/runs/44457815902) passed formatting, workspace compilation, no-default-features compilation, 45 BRAIN tests, 71 Kernel tests, 2 integration tests, 13 TALORA API tests and Clippy on the exact code head. [Run 44458023955](https://github.com/akefadaka-git/iroka/actions/runs/44458023955) passed on documentation snapshot `00b323...`.

**Approved scope:** development/test only; private API core; deterministic fake/local execution; synthetic inputs; bounded task contracts; no remote provider; no persistent memory; no production creative data. The workspace consists of `iroka-kernel`, `iroka-brain` and `iroka-talora-api`, with committed Cargo manifests and lockfile.

**Explicit exclusions and limitations:** no runnable network listener/HTTP host, production workload-token verifier, issuer/revocation configuration or key rotation; no durable task/event persistence or effective in-flight cancellation; no G05 governed memory, G03/G08 workflow, G05/G02/G11 authority graph or CIW integration; no TALORA backend network client, end-to-end network test, SBOM or independent security review. The fake/local adapter is synchronous and process-local. This baseline is not production-ready and does not establish compatibility of the wider IROKA component graph.

**D01 disposition:** OWNER-APPROVED — FREEZE BOUNDED DEVELOPMENT BASELINE. The earlier conclusion that no baseline of any kind was selectable is superseded only for this explicitly bounded scope. Full IROKA graph integration remains NOT VERIFIED. D01 is closed for baseline selection only; remaining integration/security work stays gated.

**Stage gates:** A1 VERIFIED-CLOSED; A2 remains PARTIAL / NOT CLOSED pending other A2 exit criteria; A3 NOT AUTHORIZED; A4 NOT STARTED; merge NOT AUTHORIZED; A5 NOT AUTHORIZED. No implementation, branch mutation, merge or deployment is authorized by this decision.


## 10. Supplemental remaining-A2 reconciliation — 19 November 2026

This section supersedes earlier statements in Sections 1, 7, 8 and 9 that D01 has no selectable baseline. The Owner subsequently approved and froze the bounded development-only IROKA baseline. It does not supersede the wider-graph incompatibility findings or authorize A3.

### 10.1 D01 current authoritative disposition

**D01: VERIFIED-CLOSED for selection/freeze of the bounded development-only baseline.**

- IROKA repository: akefadaka-git/iroka (canonical slug: akefadaka-git/iroka).
- Branch: integration/talora-a3-private-api.
- Frozen verified source code commit: 8056cea136f42436d896078046796eba2129092b.
- Documentation-only branch snapshot at approval: 00b323750fb120f80d56191069d10a825e66dc41.
- PR #129 remains OPEN / DRAFT / UNMERGED.
- Exact source-head CI run 44457815902 PASS; snapshot run 44458023955 PASS.
- Three-crate graph: iroka-kernel, iroka-brain, iroka-talora-api.

Scope is development/test only, deterministic fake/local execution, synthetic-only inputs, no remote provider and no persistent memory. It is not a production network service. No runnable network host, production workload-token verifier/key lifecycle, durable task/event store, effective in-flight cancellation, governed G05 memory, G03/G08 workflow, G05/G02/G11 authority graph, CIW integration, TALORA backend network client, end-to-end network test, SBOM or independent security review is established. The wider IROKA graph remains NOT VERIFIED. D03 remains API_ONLY; the baseline freeze grants no IROKA source copying, vendoring, modification, SDK packaging, redistribution or sublicensing rights.

### 10.2 Entitlement lifecycle reconciliation

The existing Owner-approved product defaults are settled: Free five-page export; Premium all supported formats and full-project export while active; referral first-ten once-per-account lifetime reward for one full-script watermarked PDF, no expiry while in good standing; watermark-free one-time PDF license for one project and its revisions; one/three/six/twelve-month watermark-free PDF terms; failed/cancelled export jobs do not consume credits; completed jobs are idempotently accounted.

The operational state machines are now documented as proposals in the A2 Technical Decision Register: distinct payment, entitlement, export-job and referral ledgers; server-side verification; acceptance-time entitlement snapshot and short-lived completion lease; refund/chargeback, dispute, account deletion and organization ownership rules. These detailed refund, chargeback, partial-refund, cancellation, account deletion and transfer rules remain PROPOSED / REQUIRE OWNER AND LEGAL APPROVAL. No provider has been selected and no merchant agreement or consumer-law opinion exists.

Referral count/output/recurrence/expiry are Owner-approved. Activation definition, fraud thresholds, manual-review triggers, appeal deadline/reviewer independence, reversal rules, account-good-standing definition and retention remain PROPOSED / REQUIRE OWNER APPROVAL.

### 10.3 Offline security reconciliation

ODR-12 remains approved only as a signed, bounded cache with numeric validity deferred to security review. The register now specifies a proposed device-bound signed snapshot, Android Keystore protection, pinned server verification key/key rotation, rollback/replay detection, no offline payment/referral/ownership mutation, online reconciliation and preservation of local editing/source projects after cache expiry.

A seven-calendar-day maximum cache validity is recorded as a PROPOSAL ONLY, not an approved numeric policy or security guarantee. It requires threat modelling and independent security review for rooted devices, clock rollback, backup/restore, reinstall, key rotation, revocation and delayed reconciliation. Paid/watermark-free export must fail closed when entitlement validity/time cannot be established; local editing remains available.

### 10.4 Amharic/Ethiopic acceptance evidence

A1 and A2 already define required language/script behaviors and test classes. Additional public evidence identifies Noto Sans Ethiopic as a candidate with 566 glyphs/505 supported characters across four Ethiopic blocks and an upstream SIL Open Font License 1.1 statement. It is not yet pinned, selected or validated for TALORA. Unicode Ethiopic block/extension documentation is linked in the decision register.

No rights-cleared TALORA screenplay/stage-play corpus, approved reviewer roster, golden pagination fixtures or measured acceptance report exists in the repository. The proposed 30-fixture synthetic/commissioned corpus, two independent competent reviewers and measurable zero-loss/zero-clipping/deterministic-pagination thresholds remain PROPOSED / REQUIRE OWNER AND LANGUAGE-REVIEW APPROVAL. No full Amharic workflow acceptance claim is made.

### 10.5 Dependency and licensing reconciliation

ODR-13 product support scope is Owner-approved: .talora, PDF, TXT, Fountain, DOCX and RTF in the bounded v1 matrix; FDX, Fade In native and Celtx native/project formats excluded from the initial promise. This closes product scope only.

No exact TALORA parser/writer/renderer/OCR dependency has been selected, pinned, installed or license-audited. No TALORA dependency lockfile, SBOM, fixture provenance register, round-trip tests or legal opinion was found. Fountain MIT reference-code availability and Noto Sans Ethiopic OFL 1.1 are candidate routes only; they do not clear any particular package checkout, transitive dependencies, fixtures, font binary, trademarks or distribution obligations. Each dependency requires exact version/commit/hash, SPDX/license text, notices, dependency closure, vulnerability review, distribution/embedding analysis and SBOM. Each fixture requires documented provenance and commercial test-use rights. IROKA use remains API_ONLY.

### 10.6 Revised A2 exit assessment

| Exit criterion | Current status | Evidence / unresolved item |
|---|---|---|
| Original A1 baseline | VERIFIED-CLOSED | Owner-approved immutable baseline and A1 amendment |
| ODR-01–ODR-13 product/technical defaults | VERIFIED-CLOSED FOR POLICY | Product defaults recorded; implementation remains gated |
| D01 bounded IROKA baseline selection | VERIFIED-CLOSED — BOUNDED SCOPE | Frozen SHA 8056cea...; exact-head CI pass; not production-ready |
| Wider IROKA component graph | NOT VERIFIED | G03/G08 and G05/G02/G11 incompatibility remains; outside bounded baseline |
| Detailed refund/chargeback/cancellation/account lifecycle | PARTIAL | State-machine proposal recorded; Owner/legal decisions remain |
| Referral activation/fraud/review/appeal operations | PARTIAL | Owner-approved reward rules; detailed operating thresholds and appeals unresolved |
| Offline entitlement security | PARTIAL | Signed bounded cache direction approved; numeric validity and independent review unresolved |
| Amharic acceptance evidence | PARTIAL | Requirements and candidate font evidence; rights-cleared corpus, reviewers and thresholds not approved |
| Format product support scope | VERIFIED-CLOSED FOR POLICY | ODR-13 approved |
| Parser/writer/renderer/font dependency licensing | PARTIAL / PRE-IMPLEMENTATION GATE | No exact dependency selected, pinned or audited; no SBOM/fixture register |
| Payment provider and legal readiness | PARTIAL / EXTERNAL GATE | Provider-neutral design/shortlist only; no provider or merchant terms selected |
| A3 backlog traceability | PARTIAL | Work packages identified; no A3 authorization |

### 10.7 A2 closure decision

**A2 remains PARTIAL / NOT READY FOR FINAL CLOSURE APPROVAL.** The D01 blocker is closed only for the bounded development/test baseline. Remaining design proposals require Owner decisions; licensing, payment and legal matters require external evidence; Amharic acceptance requires rights-cleared corpus and competent reviewer approval. A2 closure cannot claim these as complete merely because gates are documented.

No implementation, merge, PR approval, paid activation, production data use or deployment is authorized. A3 remains NOT AUTHORIZED; A4 NOT STARTED; A5 NOT AUTHORIZED.
