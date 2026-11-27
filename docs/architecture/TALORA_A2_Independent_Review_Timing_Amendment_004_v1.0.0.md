# TALORA A2 Amendment 004 — Independent Review Timing

- **Document ID:** TALORA-A2-IRA-004
- **Version:** 1.0.0
- **Owner decision date:** 27 November 2026
- **Applies to:** TALORA Amharic/Ethiopic language and document-quality acceptance plan
- **Status:** OWNER-APPROVED
- **Scope:** Review cadence only; no change to substantive quality criteria or stage authorization

## 1. Owner decision

The Owner amends the prior TALORA acceptance plan to remove recurring independent-review requirements during routine development and intermediate evidence work.

Independent reviewers are required at the **final pre-deployment acceptance gate**, immediately before any A5 deployment decision. The final review is performed against the release candidate proposed for deployment, not repeatedly against intermediate development snapshots.

## 2. Stage-gated review cadence

| Stage | Independent reviewer requirement |
|---|---|
| A1 — Assessment/specification | None |
| A2 — Read-only reconciliation | None |
| A3 — Implementation | None as a recurring development gate |
| A4 — Verification | Internal verification and retained evidence; no repeated independent reviewer gate |
| Pre-A5 — Final release acceptance | Two independent, competent Amharic/Ethiopic reviewers review the release candidate and provide documented findings |
| A5 — Deployment | Separate explicit Owner authorization remains mandatory |

The two reviewers are engaged once for the final release candidate. A material change after review that affects language fidelity, document rendering, supported formats, security, or the reviewed acceptance surface requires targeted re-review of the affected areas; it does not automatically require replaying the entire review.

## 3. Substantive acceptance criteria retained

This amendment does not reduce or waive the existing acceptance criteria:
- at least 30 rights-cleared screenplay/stage-play fixtures for the approved Amharic/Ethiopic acceptance corpus;
- documented fixture provenance, rights-holder, license/permission, permitted repository/CI/commercial-QA uses, attribution and retention conditions;
- zero lost or reordered Unicode code points;
- zero missing glyphs, clipping or overlap;
- assessment of shaping, fallback, punctuation, cursor movement, selection, editing, wrapping, pagination, semantic screenplay/stage-play structure, supported-format import/export, PDF embedding, offline persistence and recovery;
- no unresolved critical/high defects at final acceptance;
- two independent reviewers' qualifications, independence, review scope, findings and sign-off recorded against the exact release-candidate commit and artifacts.

Fixture rights remain a prerequisite before a fixture is used. This amendment does not defer rights clearance to deployment and does not create rights that have not been granted.

## 4. Separation from dependency evidence

The reviewer-count/cadence rule applies to language and document-quality acceptance. It does not require two independent reviewers for routine Gradle dependency resolution. Dependency evidence must instead be reproducible and tied to the exact source/build: resolved plugin and dependency graph, artifact hashes, SBOM, license/NOTICE review and vulnerability disposition. Any separate security review is risk-based and is not converted into repeated two-person sign-off at every development step.

## 5. Supersession and interpretation

This amendment supersedes only the prior requirement that two reviewers be engaged or attest during A2 or each development step. Earlier records describing the approved 30-fixture/two-reviewer acceptance-plan definition remain part of the historical decision record, but the timing is now governed by this amendment.

The requirement for two independent reviewers remains in force at the final pre-deployment acceptance gate. It is not a legal/universal requirement asserted for every development activity; it is TALORA's Owner-approved release-quality control.

## 6. Current project status

- Reviewer cadence amendment: **OWNER-APPROVED**.
- Rights-cleared 30-fixture corpus and rights register: **MISSING / NOT VERIFIED**.
- Final independent review: **NOT DUE — pre-A5 gate**.
- Resolved dependency graph, SBOM, license/NOTICE and vulnerability disposition: **PARTIAL / NOT CLEARED**.
- Android device acceptance: **FAIL / P0 blocker** on the two recorded runs.
- A2 overall: **PARTIAL / NOT VERIFIED-CLOSED**.
- A3, merge, A4 execution and A5 deployment: remain separately gated; this amendment grants no implementation, merge or deployment authority.
