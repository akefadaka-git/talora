# TALORA A2 Read-only Technical Reconciliation — Initial Evidence Report

**Assessment date:** 19 November 2026 (UTC)  
**Repository:** `akefadaka-git/talora`  
**A1 specification approval:** Owner approval recorded 19 November 2026  
**Assessment mode:** Read-only repository, source, workflow, and artifact inspection  
**Current disposition:** **A2 PARTIAL / IN PROGRESS — NOT CLOSED**  
**A3 implementation:** NOT AUTHORIZED  
**Merge / deployment:** NOT AUTHORIZED

## 1. Executive determination

The repository contains a functioning early Android offline-authoring test foundation on a feature branch, but the default `main` branch remains a one-file README baseline. The implementation branch is not merged and its PR targets an integration-documentation branch, not `main`.

The exact feature-head push workflow succeeded at `fa8b2c60f1c9787071a98c054ec337d26501ccac`. Its reports show **24 JVM unit tests passed, zero failures/errors/skips**, and Android lint reported **5 warnings**. A debug APK was produced. This establishes a successful test build for that commit; it does not establish device installation, UI/E2E acceptance, production readiness, or a production release.

The implementation is materially below the newly approved professional-studio specification. The most significant gaps are:
- no bundled Ethiopic font resources or verified Ethiopic typography/PDF embedding;
- no semantic screenplay/stage-play document model or professional pagination;
- no character map, structured scene/story management, director workspace, or production-management domains;
- no account, four-tier entitlement engine, payment/referral service, collaboration backend, or sync service;
- no verified TALORA-to-IROKA runtime integration.

The repository also has multiple divergent documentation and implementation branches. An earlier A2 evidence branch contains substantial prior Owner-approved decisions and an A2 report that explicitly remains PARTIAL / NOT CLOSED. That work must be preserved and reconciled, not replaced by the new product-spec branch.

**A2 cannot close on the present evidence.** This report records the initial read-only findings and the remaining reconciliation work. No application source, dependency, database schema, or production configuration was changed.

## 2. Repository and branch baseline

### 2.1 Repository

| Item | Observed value |
|---|---|
| Repository | `akefadaka-git/talora` |
| Visibility | Public |
| Default branch | `main` |
| Main SHA | `2562962fc44636cdebfa49c7bf3c3cdcb618751c` |
| Main tree | One file: `README.md` |
| Branch protection | The seven inspected branches report `protected: false` |
| Repository licence | No licence declared in repository metadata |

The current default branch is not the Android implementation baseline. The application code is on `feat/android-offline-test-mvp`.

### 2.2 Branch inventory

| Branch | Head SHA | Relation to main | Contents / disposition |
|---|---|---:|---|
| `main` | `2562962fc44636cdebfa49c7bf3c3cdcb618751c` | Baseline | README only |
| `docs/a1-product-definition` | `599bcb4a71bd7ca7e1b5945c8034f29ecff4bc06` | 11 commits ahead | Original 11-document A1 specification; historical Owner-approved baseline |
| `docs/a1-import-export-referral-entitlements` | `dca5df61db7ca71aa3e6b1fb8f1a77e7a50e8a74` | 60 commits ahead | Original A1 records, A1 amendment, extensive A2 research/decision reports; not represented by an open PR in the inspected PR list |
| `docs/talora-iroka-integration-contract-v0.1.0` | `6e9c2db1d0779fa099c619b8cf5711f3e479aa79` | 1 commit ahead | Historical IROKA contract v0.1.0 |
| `docs/talora-iroka-a2-reconciliation-v0.1.1` | `c1174778fa4af8c944468e537c73f294fce56a9b` | 3 commits ahead | IROKA contracts v0.1.1/v0.1.2 and A2 integration assessment/decision documents |
| `feat/android-offline-test-mvp` | `fa8b2c60f1c9787071a98c054ec337d26501ccac` | 52 commits ahead | Android implementation, tests, native archive, interchange, PDF exporter, and copied IROKA integration documents |
| `docs/talora-product-spec-four-tier-fonts` | `f5cbad1d4ba75b4d9ebf853580041798e6e33550` | Documentation branch based on main; head recorded in this report | Newly Owner-approved product specification, roadmap, and this A2 report |

The A1/A2 evidence branch at `dca5df6` and Android branch at `fa8b2c6` are **DIVERGED**: comparison reports 60 commits on the A2 evidence side and 52 on the Android side, with no direct ancestry between those branch heads beyond their shared main lineage. Their documentation and decisions are not automatically present in one another.

The integration-contract branch `c117477` and Android branch `fa8b2c6` are related: the Android branch is 42 commits ahead of the integration-contract branch. PR #3 therefore depends on the PR #2 branch as its base.

## 3. Open pull requests and dependencies

Four pull requests were returned as open; all are draft and unmerged.

| PR | Head | Base | Unique scope | A2 finding |
|---|---|---|---|---|
| #1 | `docs/talora-iroka-integration-contract-v0.1.0` | `main` | Initial v0.1.0 IROKA contract | Historical version; preserve its provenance while checking whether it is superseded |
| #2 | `docs/talora-iroka-a2-reconciliation-v0.1.1` | `main` | A2 integration assessment, decision registers, contracts v0.1.1/v0.1.2 | Current integration-document candidate; not merged |
| #3 | `feat/android-offline-test-mvp` | `docs/talora-iroka-a2-reconciliation-v0.1.1` | Android app and test foundation | Depends on PR #2 base; not directly based on main |
| #4 | `docs/talora-product-spec-four-tier-fonts` | `main` | Approved professional-studio specification and roadmap | A1 is Owner-approved; PR remains draft/open/unmerged |

PR #1 and PR #2 are not a simple linear history: compare reports their heads as **DIVERGED** (PR #1 side 1 unique commit; PR #2 side 10 unique commits). PR #2 contains later contract versions, but the v0.1.0 branch remains historical evidence. Do not delete or merge these branches merely by name.

PR #3 is 42 commits ahead of its declared base. It is 52 commits ahead of main because its ancestry includes the integration-document branch. Its current base means a merge sequence would require deliberate dependency handling; no merge is recommended or authorized by this report.

PR #4 remains unmerged. The Owner's approval closes the A1 specification gate; it is not merge or deployment authorization.

## 4. Android implementation baseline

**Inspected source head:** `fa8b2c60f1c9787071a98c054ec337d26501ccac`  
**Branch:** `feat/android-offline-test-mvp`  
**PR:** #3

The branch contains 55 tree entries, including:
- `MainActivity.kt` (948 lines): a single-activity Compose app shell and local writing workflow.
- `ProjectStore.kt` (574 lines): SQLite project, revision, and attachment persistence.
- `TaloraArchive.kt` (300 lines): native `.talora` ZIP/JSON codec.
- `ScriptInterchange.kt` (354 lines): bounded text/Fountain/FDX/DOCX/RTF interchange methods.
- `ScriptPdfExporter.kt` (78 lines): local PDF renderer.
- five JVM test classes and one GitHub Actions workflow.

### 4.1 Build configuration

| Setting | Observed value |
|---|---|
| Android namespace/application ID | `com.talora.app` |
| Debug application ID | `com.talora.app.debug` |
| Version | `0.1.0-test` |
| minSdk | 26 |
| compileSdk / targetSdk | 35 / 35 |
| Java/Kotlin target | 17 |
| CI JDK | 17 |
| CI Gradle | 8.9 |
| UI toolkit | Kotlin + Jetpack Compose |
| Local persistence | SQLite via `SQLiteOpenHelper` |
| Network permission | No `INTERNET` permission in the inspected manifest |

The branch is explicitly documented as an early offline test foundation, not a production release.

### 4.2 Verified narrow capabilities

| Capability | Evidence-based status | Limitation |
|---|---|---|
| Create/reopen local project | **PASS — unit-tested** | No device-level acceptance evidence in this assessment |
| Debounced autosave / local persistence | **PASS — unit-tested** | Crash/device interruption behavior still needs broader verification |
| Ethiopic source-text preservation | **PARTIAL** | Text preservation is not proof of correct glyph rendering, shaping, cursor behavior, pagination, or export |
| Revision snapshots / comparison / restore | **PARTIAL — unit-tested foundation** | Not equivalent to professional revision colors, locked production drafts, or collaborative history |
| Asset attachment and package persistence | **PARTIAL — unit-tested foundation** | Large-file/provider-interruption recovery and media workflow remain unverified |
| Native `.talora` archive | **PARTIAL — codec and tests present** | Schema v1 is source-text-oriented; it is not yet the approved target typed creative-project model |
| TXT / Fountain interchange | **PARTIAL** | Fountain export is screenplay-only; semantic fidelity and pagination entitlements are not implemented |
| FDX interchange | **CODE PRESENT; PRODUCT SCOPE CONFLICT** | Parser/writer methods and tests exist, but the earlier A2 Owner-approved v1 matrix excludes FDX pending lawful, documented, tested compatibility clearance |
| DOCX / RTF interchange | **PARTIAL** | Bounded paragraph/text conversion; advanced Word/RTF styles, tables, tracked changes, embedded media, and layout are not preserved |
| PDF export | **PARTIAL** | A simple offline text renderer exists; it is not professional screenplay pagination or Ethiopic-verified print output |
| IROKA integration | **MISSING / UNRESOLVED** | No TALORA runtime adapter, authenticated service client, model inference, or verified endpoint integration in the Android branch |

## 5. Exact-head CI and artifact evidence

### 5.1 Direct push build — exact branch head

- Workflow: **Android test APK**
- Run #82: [44588001447](https://github.com/akefadaka-git/talora/actions/runs/44588001447)
- Event: `push`
- Branch: `feat/android-offline-test-mvp`
- Source SHA: `fa8b2c60f1c9787071a98c054ec337d26501ccac`
- Conclusion: **SUCCESS**
- Build/test/lint/package steps: all reported successful.

The push event checks out the branch commit directly. This is the preferred exact-head CI evidence; the separate PR-triggered Run #83 is not substituted for it.

### 5.2 Test report

Run #82 test artifact: `14562685867` (`talora-android-unit-test-reports`).

| Test class | Tests | Failures | Errors | Skipped |
|---|---:|---:|---:|---:|
| ProjectStoreTest | 7 | 0 | 0 | 0 |
| ScriptInterchangeTest | 7 | 0 | 0 | 0 |
| TaloraArchiveTest | 6 | 0 | 0 | 0 |
| ProjectTextPolicyTest | 2 | 0 | 0 | 0 |
| RevisionDiffTest | 2 | 0 | 0 | 0 |
| **Total** | **24** | **0** | **0** | **0** |

**Disposition:** **PASS** for the 24 JVM unit tests executed in Run #82. No Android instrumentation/UI test suite or physical-device test result is represented by this report.

### 5.3 Lint report

Run #82 lint artifact: `14561119866` (`talora-android-lint-report`).

**Result: 5 warnings, no lint errors reported.**
- 1 target SDK warning: target SDK 35 was not the latest target available to the lint engine.
- 4 obsolete-dependency warnings: Compose BOM 2024.10.01, activity-compose 1.9.3, lifecycle-runtime-ktx 2.8.7, and androidx.test:core 1.6.1.

These warnings are recorded for A2/A3 dependency review; no dependency update was performed.

### 5.4 Debug APK

Run #82 APK artifact: `14563815039` (`talora-android-debug-apk`).

- File: `app-debug.apk`
- Size: 9,840,185 bytes
- SHA-256: `8fc36d12b9068554dedd4601252e58c4c79d95c8968ee8c152833a44e81ac096`
- Variant: debug
- Source SHA: `fa8b2c60f1c9787071a98c054ec337d26501ccac`

The APK is a test artifact, not a production-signed release. This A2 assessment did not install it on a physical device.

## 6. Requirement-to-implementation reconciliation

| Approved requirement area | Current implementation evidence | Status |
|---|---|---|
| Bundled Noto Sans Ethiopic + Noto Serif Ethiopic | No `res/font` directory or font assets in the inspected source tree/APK | **MISSING** |
| Ethiopic font fallback, mixed-script rendering and PDF embedding | PDF renderer uses Android generic `sans-serif` / `sans-serif-medium`; no font-embedding or golden visual evidence | **MISSING** |
| Semantic screenplay/stage-play model | Project body is stored as a text string; FDX export infers paragraph types heuristically from line text | **MISSING** |
| Professional formatting and pagination | PDF renderer performs simple line wrapping; no screenplay geometry, page header/footer, dual-dialogue layout, continuation or deterministic professional paginator | **MISSING** |
| Character profiles and relationship map | No character domain/table/UI found in the inspected Android tree | **MISSING** |
| Scene/story/beat/act management | Script type is project metadata; no scene/beat/act domain or navigator found | **MISSING** |
| Director workspace and annotation layers | No director/production annotation domain or UI found | **MISSING** |
| Production breakdown and scheduling | No breakdown, cast/crew, locations, call-sheet or schedule domain found | **MISSING** |
| Four-tier subscription entitlements | No account, entitlement, billing or subscription implementation; no network permission | **MISSING** |
| Collaboration and organizations | No server, sync, workspace, role or collaboration implementation | **MISSING** |
| IROKA assistance | Contract documents only; no verified Android runtime connection | **UNRESOLVED / MISSING** |
| Local project storage, revisions, attachments | SQLite and archive code plus unit tests | **PARTIAL / VERIFIED FOUNDATION** |
| Native project archive | Versioned `.talora` codec and round-trip tests | **PARTIAL** |
| Interchange and PDF | Bounded adapters and tests; known scope/fidelity limitations | **PARTIAL** |

## 7. Documentation and decision conflicts

### 7.1 README versus implementation

`README-ANDROID.md` describes native `.talora` package and PDF export as not yet implemented, while the inspected branch contains `TaloraArchive.kt`, `ScriptPdfExporter.kt`, and associated tests. The supported-formats table and native-format specification also describe these capabilities as present.

**Disposition:** documentation is stale/inconsistent. Code presence and tests establish a foundation, not production-quality feature completion. Reconcile wording before using these documents as a release capability statement.

### 7.2 FDX scope

`ScriptInterchange.kt` contains FDX parser/writer methods and FDX tests. However:
- `SUPPORTED-FORMATS.md` marks FDX as not yet supported.
- The earlier A2 evidence report records an Owner-approved bounded v1 matrix excluding FDX pending lawful documented interchange routes, versioned fixtures, exact dependency review, and compatibility testing.

**Disposition:** retain FDX as excluded from the v1 support promise until the Owner-approved format policy is explicitly amended and all legal/technical gates are evidenced. Do not equate source-code presence with supported product compatibility.

### 7.3 Four-tier subscriptions versus earlier commercial decisions

The original approved A1 baseline records a free advertising-supported edition and a one-time ad-free premium purchase. The later approved native-format/entitlement amendment and A2 decision records also include five-page Free export, referrals, one-time project watermark-removal licenses, and fixed-term watermark-free entitlements.

The newly approved specification selects a four-level feature structure (Free, Creator, Professional, Studio). It does not yet define whether these tiers replace, coexist with, or package the earlier one-time license, advertising, referral, export-cap, watermark and 1/3/6/12-month entitlements.

**Disposition:** **UNRESOLVED commercial/entitlement architecture decision.** Preserve all earlier Owner-approved decisions; define a coherent entitlement matrix and migration/compatibility policy before commerce implementation. The four-level structure is approved, but pricing, provider, billing mechanics and how legacy entitlements map into tiers are not yet approved.

### 7.4 Existing A2 assessment branch

The branch `docs/a1-import-export-referral-entitlements` contains the original A1 baseline, its approved amendment, detailed A2 research and decision registers, and a prior A2 closure assessment. That assessment explicitly reports **A2 PARTIAL / NOT READY FOR OWNER CLOSURE**, with remaining blockers including:
- no selectable, verified immutable IROKA integration graph;
- exact parser/writer/renderer/font dependency, fixture and distribution licensing/SBOM evidence;
- numeric validity and security review for offline entitlement cache;
- Amharic/Ethiopic acceptance corpus, competent reviewers and measured golden-test evidence;
- Ethiopia merchant/provider, tax, consumer/privacy and data-residency confirmation.

A later addendum resolves the detailed lifecycle Owner decision for policy purposes but still records A2 PARTIAL / NOT CLOSED. These records remain relevant. The new A1 specification approval does not close or erase those A2 blockers.

## 8. IROKA integration status

The Android feature branch includes copies of IROKA integration contract v0.1.1/v0.1.2 and A2 assessment/decision documents. The contract describes a proposed private backend-mediated API, capability discovery, scoped task requests, typed results and explicit user acceptance of proposed changes.

No Android IROKA client, TALORA backend, verified capability endpoint, authenticated service-to-service path, model adapter, or end-to-end task/result flow was found in the inspected Android source tree. The prior A2 evidence branch records no compatible immutable IROKA component graph and no verified complete TALORA-facing API service.

**Disposition:** integration contract documentation is present; integration runtime is **NOT IMPLEMENTED / NOT VERIFIED**. D01 remains **UNRESOLVED** unless newer authoritative evidence supersedes the prior assessment.

## 9. A2 risk and dependency register

| ID | Risk / gap | Severity | Required A2 evidence or disposition |
|---|---|---|---|
| A2-01 | `main` does not contain the Android implementation | High | Decide and document intended PR/branch integration order; do not merge automatically |
| A2-02 | A2 evidence branch and Android branch are divergent | High | Reconcile unique documents/decisions and establish a non-destructive integration plan |
| A2-03 | PR #3 targets an open documentation branch, not main | High | Resolve PR #2/#3 dependency and base strategy |
| A2-04 | No bundled Ethiopic font resources or visual acceptance corpus | High | Verify exact font files, OFL notices, licensing, shaping, fallback, PDF embedding and rights-cleared test corpus |
| A2-05 | Text-only model cannot support required professional script semantics | High | Specify typed schema, stable element/scene IDs, reversible migration and pagination contract |
| A2-06 | New four-tier packaging is not reconciled with previous approved commerce entitlements | High | Owner-level commercial entitlement mapping; preserve prior rights until explicitly amended |
| A2-07 | No verified IROKA implementation baseline/runtime | High | Continue read-only exact-commit/API/dependency/authority reconciliation |
| A2-08 | Format code/docs/product-policy mismatch (especially FDX) | Medium/High | Freeze truthful capability matrix; dependency/fixture/license evidence |
| A2-09 | Five lint warnings and older dependency declarations | Medium | Review release compatibility, security advisories, upgrade paths and regression impact |
| A2-10 | No device/UI/accessibility/performance verification | High | Define supported device matrix and execute instrumented/manual acceptance tests |
| A2-11 | No dependency SBOM or complete font/parser/fixture rights register | High | Generate and review SBOM and third-party notice inventory before implementation/release |
| A2-12 | Repository branches are unprotected | Medium | Owner decision on protection rules; no protection changes made in A2 |

## 10. A2 exit assessment

| A2 workstream | Status | Evidence / remaining work |
|---|---|---|
| Repository, branch and PR inventory | **PASS — INITIAL INVENTORY** | Seven branches, four open draft PRs, exact refs captured |
| A1 specification decision | **VERIFIED-CLOSED** | Owner approved expanded specification 19 November 2026; historical A1 baseline preserved |
| Android exact-head build/test evidence | **PASS — LIMITED** | Run #82 at exact branch SHA; 24 JVM tests pass; debug APK exists |
| Source-level feature inventory | **PARTIAL** | Main Android files and relevant docs inspected; full behavioral/device acceptance not performed |
| Main/feature/PR dependency reconciliation | **PARTIAL** | Key divergence and PR #2/#3 dependency identified; full unique-work and safe integration register still required |
| Ethiopic fonts and rendering | **MISSING** | No bundled font files, font tests, PDF embedding or visual acceptance evidence |
| Structured editor and professional formatting | **MISSING** | Current project body is plain text; no typed document model or professional paginator |
| Character, scene, director and production domains | **MISSING** | Not present in inspected Android implementation |
| Four-tier subscription and existing entitlement reconciliation | **UNRESOLVED** | Commercial policy interaction and feature-key mapping not finalized |
| IROKA compatibility and runtime | **UNRESOLVED** | Prior A2 report says no verified immutable integration graph; Android runtime absent |
| Dependency, licence, fixture and SBOM review | **NOT RUN / PARTIAL EVIDENCE** | Version declarations visible; full transitive and distribution review outstanding |
| Device, UI, accessibility and performance testing | **NOT RUN** | No evidence in the inspected workflow |
| External provider/legal/data-residency gates | **PARTIAL / EXTERNAL** | Prior A2 research exists; merchant-specific and legal evidence remains outstanding |

**Overall A2 disposition: PARTIAL / IN PROGRESS — DO NOT CLOSE.**

## 11. Recommended remaining A2 sequence

1. Complete the unique-content and dependency register across PRs #1–#4 and the unassociated A1/A2 documentation branch. Preserve historical contracts and Owner-approved decision records.
2. Reconcile the expanded approved product specification with the original A1 baseline and the native-format/import/export/entitlement amendment. Resolve the four-tier versus one-time/project-license/referral/watermark/export model without silently revoking prior decisions.
3. Complete a requirement-to-file/test traceability matrix for the Android feature branch, separating implemented, tested, partially implemented, documentation-only, and missing behavior.
4. Complete exact dependency, font, parser/writer, renderer, fixture and distribution-license review; generate an SBOM and notices inventory.
5. Complete Amharic/Ethiopic font and acceptance-evidence planning: rights-cleared corpus, qualified reviewer plan, shaping/cursor/search/accessibility/PDF test cases and measurable thresholds.
6. Continue read-only IROKA commit-graph, API, tenant/authority, policy, memory, audit and dependency reconciliation. Do not compose or vendor branches during A2.
7. Define the required device/UI/accessibility/performance verification plan and distinguish it from the 24 passing JVM unit tests.
8. Produce the final A2 evidence report, risk register, decision register and prioritized A3 work-package proposal for Owner review.

## 12. Governance disposition

- A1 expanded product specification: **VERIFIED-CLOSED** by Owner approval on 19 November 2026.
- A2 technical reconciliation: **IN PROGRESS / NOT CLOSED**.
- A3 implementation: **NOT AUTHORIZED**.
- A4 verification: **NOT AUTHORIZED as a stage transition**; current A2 only inspected existing CI evidence.
- A5 deployment: **NOT AUTHORIZED**.
- PR merges: **NOT AUTHORIZED**.
- Application source, dependencies, database schema and production settings: **UNCHANGED** by this assessment.

**Immediate next step:** complete the branch/PR unique-work reconciliation and resolve the four-tier entitlement model against the previously approved one-time project-license, referral, watermark and fixed-term subscription decisions. Only then prepare the consolidated A2 closure recommendation.
