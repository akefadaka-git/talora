# TALORA A2 Consolidated Assessment Update — Owner Lifecycle Approval

- **Document ID:** TALORA-A2-ASSESSMENT-ADDENDUM-004
- **Version:** 1.0.0
- **Date:** 19 November 2026
- **Repository:** akefadaka-git/talora
- **Branch:** docs/a1-import-export-referral-entitlements
- **Assessment basis:** Existing A2 evidence reconciliation plus Owner approval recorded in TALORA_A2_Owner_Approval_Operational_Lifecycle_Package_v1.0.0.md
- **Assessment type:** Documentation-only A2 status refresh
- **Implementation:** NOT AUTHORIZED by this assessment

## 1. Decision and scope

The Owner approved TDR §21.2 operational lifecycle items 1–8 as written on 19 November 2026. The approval is recorded in the Owner Approval document above.

This addendum supersedes earlier statements in the A2 Technical Decision Register and the A2 Evidence Reconciliation and Closure Assessment that detailed payment/entitlement/refund/chargeback/export-job/referral lifecycle rules still await Owner approval.

It does not supersede the legal/provider qualifications inside the approved package, nor does it turn external evidence gaps into passed gates. The underlying A1 baseline and prior A2 decisions are preserved; this is a status addendum, not a rewrite of historical approvals.

## 2. Revised A2 status register

| Gate / evidence area | Revised status | Evidence and remaining condition |
|---|---|---|
| A1 original baseline and amendment | **VERIFIED-CLOSED** | Approved immutable baseline retained; no reopening |
| ODR-01–ODR-13 product and technical defaults | **VERIFIED-CLOSED FOR POLICY** | Product defaults and bounded v1 format scope approved; no implementation or license clearance implied |
| ODR-14 provider-neutral commerce architecture | **VERIFIED-CLOSED FOR ARCHITECTURE** | Provider-neutral model reconciled; provider selection and merchant/legal readiness remain external |
| Offline paid-entitlement cache maximum | **VERIFIED-CLOSED FOR A2 POLICY** | Owner-approved maximum seven calendar days from last successful synchronization; independent threat model/security review remains open |
| Amharic/Ethiopic acceptance-plan definition | **VERIFIED-CLOSED FOR A2 PLAN** | Owner-approved minimum 30 rights-cleared fixtures, at least two independent competent reviewers and measurable targets; corpus/reviewer/test evidence remains outstanding |
| D01 bounded IROKA development/test baseline | **VERIFIED-CLOSED — BOUNDED SCOPE** | Frozen source SHA 8056cea136f42436d896078046796eba2129092b; exact-head CI run 44457815902 recorded SUCCESS; does not establish production service readiness |
| Detailed payment, entitlement, refund, chargeback, export-job and referral lifecycle | **VERIFIED-CLOSED FOR A2 POLICY** | Owner approved TDR §21.2 items 1–8 as written; operational implementation, legal/provider confirmation and numerical controls remain outstanding |
| TALORA dependency and fixture licensing | **PARTIAL / PRE-IMPLEMENTATION GATE** | No exact parser/writer/renderer/OCR/font dependency set pinned and audited; no complete SBOM or fixture-rights register |
| Payment provider / merchant readiness | **PARTIAL / EXTERNAL GATE** | NBE register and public provider documentation support due diligence only; no TALORA merchant approval, signed contract, negotiated fees/settlement, sandbox proof or live transaction |
| Ethiopia tax, consumer, privacy and data residency | **PARTIAL / LEGAL GATE** | Relevant laws identified; no written tax/consumer/privacy opinion, DPIA, production data map or verified Ethiopia residency/cross-border transfer design |
| Amharic/Ethiopic workflow acceptance | **PARTIAL / NOT VERIFIED** | No completed rights-cleared corpus, appointed/signed-off reviewer roster, measured round-trip/pagination/visual report or completed golden tests |
| IROKA production integration | **NOT VERIFIED** | Bounded fake/local baseline only; no production host, token/key lifecycle, durable task/event store, effective cancellation, TALORA network client, end-to-end network proof, SBOM or independent security review |
| A3 backlog and implementation readiness | **PARTIAL** | Design work packages are documented; dependency and external gates remain; no new TALORA A3 scope is authorized here |

## 3. Interpretation of the approved lifecycle package

The A2 policy decision now fixes the following operating principles:

- Subscription periods use calendar-month semantics, begin at authoritative server-verified settlement, and expire end-exclusively.
- V1 renewal is user-initiated; no automatic renewal or automatic top-up.
- Payment, entitlement, export-job and referral records have distinct state models and idempotent server-side event handling.
- Verified full refunds and lost chargebacks revoke future paid use; won chargebacks restore only unused time; partial refunds remain disabled unless a deterministic rule is separately approved and provider support is confirmed.
- Disputes freeze new watermark-free exports within the disputed scope while preserving source work and completed exports.
- Export jobs bind immutable source and rendering/entitlement context; expiry alone does not invalidate an accepted job, while verified revocation events may stop cancellable jobs.
- Organization-owned project rights remain with the organization; personal license transfer is never implicit; account deletion and transfer require user-controlled and authenticated procedures subject to law.
- Referral activation is a verified distinct account plus creation and save of the invitee's first project; no script upload, sharing, payment or IROKA use is required. Fraud review must be proportionate, reason-coded and appealable without invasive fingerprinting or creative-content inspection.

These are approved policy defaults, not verified software behavior.

## 4. Mandatory external and evidence gates retained

### 4.1 Legal and provider confirmation

Before commerce activation or publication of final customer terms:
- Obtain Ethiopian counsel review of refund rights, digital-service commencement/consent, consumer disclosures, tax invoices, complaints, retention, deletion and e-contract terms.
- Obtain written provider confirmation of merchant eligibility, supported transaction/refund/dispute flows, settlement, fees, operational timing and evidence requirements.
- Preserve the prohibition on automatic renewal in v1 unless a separate Owner decision and lawful/provider-supported route are established.
- Confirm VAT, registration, invoice, withholding, income-tax and cross-border treatment; do not assume a rate or exemption.

### 4.2 Privacy and data residency

Before production processing of personal data or private creative content:
- Map collection, primary storage, replicas, backups, logs, support access, subprocessors, IROKA calls and model-provider regions.
- Demonstrate compliance with the applicable local-storage and cross-border-transfer requirements under Personal Data Protection Proclamation No. 1321/2024.
- Complete the required privacy impact assessment, processing records, safeguards, DPO/authority steps where applicable, and breach-response process.
- Do not infer production data-residency compliance from the development-only IROKA baseline.

### 4.3 Dependency and fixture rights

Before implementation uses any parser, writer, archive library, renderer, OCR package or font:
- Pin exact version/commit and artifact hash.
- Review SPDX identifier, license text, copyright/NOTICE, transitive dependency closure, platform/native components, vulnerability status and distribution/embedding obligations.
- Produce the SBOM and an explicit reviewer decision.
- Record each fixture's author/rights holder, written permission/license, commercial test and redistribution rights, source hash and retention rule.
- Exclude any dependency or fixture with incompatible or ambiguous rights pending a separate decision.

### 4.4 Amharic/Ethiopic verification

The approved acceptance plan remains a plan, not a pass:
- Produce at least 30 rights-cleared synthetic/commissioned screenplay and stage-play fixtures.
- Appoint at least two independent competent Amharic/Ethiopic reviewers and record competence and sign-off.
- Execute lossless round-trip, Unicode fidelity, shaping/glyph, editing, search, pagination and visual-layout tests.
- Demonstrate zero missing/duplicated/reordered code points in lossless TALORA round trips, zero silent normalization changes, zero missing glyphs/clipping/overlap, deterministic pagination for identical inputs, disclosure of lossy transformations and zero open critical/high defects at the release gate.

### 4.5 IROKA

D01 remains closed only for the bounded development/test baseline. D03 API_ONLY and all existing IROKA rights boundaries remain in force. No source copying, vendoring, SDK packaging, redistribution, sublicensing, production data processing or expansion of the previously authorized IROKA A3 slice is authorized by this addendum.

## 5. Repository and stage boundary

The approval and this assessment update are documentation-only changes on docs/a1-import-export-referral-entitlements.

- TALORA main baseline remains 2562962fc44636cdebfa49c7bf3c3cdcb618751c.
- No TALORA application/runtime source, database schema, dependency manifest, payment integration or production configuration is changed by this decision.
- No PR merge, main-branch change, A4 test execution or deployment is authorized.

Stage disposition:

- **A1:** VERIFIED-CLOSED.
- **A2:** PARTIAL / NOT CLOSED.
- **A3:** NOT AUTHORIZED for additional TALORA implementation by this addendum.
- **A4:** NOT STARTED.
- **Merge / PR approval:** NOT AUTHORIZED.
- **A5:** NOT AUTHORIZED.

## 6. A2 conclusion and next step

**A2 remains PARTIAL / NOT CLOSED.** The outstanding lifecycle Owner decision has been resolved. Remaining blockers are now evidence-based and external: dependency/fixture licensing, merchant/provider readiness, Ethiopia tax/consumer/privacy/data-residency confirmation, Amharic corpus/reviewer/test evidence, and IROKA production service/security readiness.

**Next step:** continue read-only A2 evidence reconciliation against the current TALORA and IROKA repository heads, prioritizing exact dependency/fixture rights evidence and the production data-flow/residency map. Do not begin implementation or close A2 until the applicable evidence is independently verified and the Owner receives a consolidated closure recommendation.
