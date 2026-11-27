# TALORA A2 FDX, Licensing and Technical Reconciliation Addendum

- **Document ID:** TALORA-A2-TRA-003
- **Version:** 1.0.0
- **Assessment date:** 27 November 2026
- **Repository:** `akefadaka-git/talora`
- **A2 evidence branch:** `docs/a1-import-export-referral-entitlements`
- **Inspected implementation head:** `feat/android-offline-test-mvp` — `97e02c0da28d8cbbdc41aff1fbb05958d58fe050`
- **Current main baseline:** `2562962fc44636cdebfa49c7bf3c3cdcb618751c`
- **Assessment mode:** Read-only source, documentation, branch and CI evidence reconciliation
- **Disposition:** FDX policy resolved; implementation/licensing and broader technical acceptance remain PARTIAL
- **Implementation / merge / deployment:** NOT AUTHORIZED

## 1. Executive finding

This addendum reconciles the previously Owner-approved v1 format policy against the later Android implementation and current product documentation.

**FDX remains excluded from TALORA v1's supported-format promise.** The Android feature branch contains experimental FDX import/export code and tests, but code presence does not supersede the Owner-approved ODR-13 exclusion or establish Final Draft compatibility, lawful fixtures, complete metadata preservation, or interoperability.

The source/dependency and technical review identifies specific implementation evidence, but cannot close the licensing or release-readiness gates: no dependency lockfile, generated SBOM, complete third-party notice set, font binaries, authorized FDX fixtures, or completed Amharic acceptance corpus/reviewer evidence was found in the inspected implementation tree.

## 2. FDX — binding product disposition

### 2.1 Existing approved policy

The Owner-approved ODR-13 decision register defines the bounded v1 format matrix and expressly excludes Final Draft FDX from the initial v1 promise. Reconsideration requires a lawful official interchange route, versioned authorized fixtures, compatibility review and conformance evidence.

The A2 external-format research separately records that:
- Final Draft's published support guidance is version/product-specific;
- an XML file that resembles FDX is not proof of genuine Final Draft compatibility;
- interoperability must be tested in the relevant target application/version;
- no legal opinion or complete conformance study was obtained in the A2 desk review.

**A2 decision:** retain FDX as **DEFERRED / NOT SUPPORTED IN V1**. No Owner amendment to ODR-13 was found in the evidence reviewed for this addendum.

### 2.2 Current implementation evidence

At Android head `97e02c0da28d8cbbdc41aff1fbb05958d58fe050`:

- `ScriptInterchange.kt` defines `TextImportFormat.FDX`, filename/MIME detection, `importFdx` and `exportFdx`.
- Import extracts paragraph text from XML and joins it as plain text. It does not preserve the full FDX semantic/metadata model.
- Export infers paragraph categories heuristically from line text (for example uppercase lines and common scene-heading prefixes), then emits a simplified XML document.
- Unit tests exist for interchange, but the inspected evidence does not establish genuine versioned FDX fixtures, independent Final Draft application round trips, or full construct fidelity.
- `docs/interoperability/SUPPORTED-FORMATS.md` still says FDX is “Not yet” supported and requires independent golden-file tests.

### 2.3 Required product/documentation treatment

- Keep FDX absent from the v1 supported-format list and customer-facing compatibility claims.
- Treat the code path as experimental/unreleased and inaccessible as a supported product promise.
- Preserve the ODR-13 exclusion until a later Owner decision explicitly amends it after legal, fixture, version and interoperability evidence.
- Do not delete or rewrite historical FDX code as part of A2; any removal, gating or implementation change is A3 work and requires separate authorization.
- If FDX is reconsidered later, define supported FDX versions and constructs, secure lawful fixtures, map unsupported metadata, run adversarial XML tests, and test import/export in authorized target applications.

**FDX status:** **VERIFIED-CLOSED FOR V1 POLICY**; **NOT VERIFIED / NOT SUPPORTED as an implemented compatible product format**.

## 3. Dependency, font and fixture licensing

### 3.1 Direct dependency declarations observed

The inspected Android `android/app/build.gradle.kts` at the stated head declares:

| Component | Declared version/profile |
|---|---|
| AndroidX Compose BOM | 2026.11.01 |
| AndroidX Activity Compose | 1.13.0 |
| AndroidX Lifecycle Runtime KTX | 2.11.1 |
| Kotlinx Serialization JSON | 1.7.3 |
| JUnit | 4.13.2 |
| Robolectric | 4.14.1 |
| AndroidX Test Core | 1.7.0 |
| AndroidX Test Runner | 1.7.0 |
| AndroidX Test Ext JUnit | 1.3.0 |
| AndroidX UI Automator | 2.4.1 |

This is a direct-declaration inventory, not a complete dependency closure or license determination. The Compose BOM coordinates multiple Compose artifacts; the resolved transitive graph must be captured from the exact build.

### 3.2 Missing evidence

In the inspected Android feature-branch tree, the following were not found:
- Gradle dependency lockfiles or a complete resolved dependency report;
- a generated SPDX or CycloneDX SBOM;
- a complete third-party license/NOTICE inventory covering direct and transitive dependencies;
- exact artifact hashes and vulnerability/advisory disposition;
- Noto Sans Ethiopic or Noto Serif Ethiopic font binaries under Android resources;
- font license/NOTICE files bundled with the app;
- authorized FDX, DOCX, RTF, Fountain or screenplay/stage-play golden fixture provenance records;
- a fixture-rights register demonstrating commercial test-use and redistribution rights.

The earlier A2 research identifies Noto Sans Ethiopic and the Fountain reference implementation as candidate routes only. Candidate license availability does not clear an exact binary, release, checkout, transitive dependency, fixture, embedding use or redistribution obligation.

### 3.3 Required licensing closure evidence

Before an implementation package is treated as cleared:
1. Resolve exact direct and transitive dependencies for the pinned build; record group/artifact/version, source, artifact hash, SPDX expression, license text, notices, platform/native components and distribution obligations.
2. Generate and retain an SPDX or CycloneDX SBOM from the exact build inputs.
3. Review vulnerabilities and known security advisories against the exact versions.
4. Record reviewer disposition for app distribution, commercial use, modification, attribution and notices.
5. For each font, record exact family, version/release, file hash, upstream source, license text, embedding/distribution obligations and in-app notice.
6. For every fixture, record author/rights holder, source, written permission/license, commercial test-use and redistribution rights, hash and retention policy.
7. Exclude any item with ambiguous or incompatible rights until separately resolved.

**Licensing status:** **PARTIAL / PRE-IMPLEMENTATION GATE — NOT CLEARED**.

## 4. Technical reconciliation against the approved product target

| Area | Observed evidence | A2 disposition |
|---|---|---|
| Main branch | Main SHA `2562962fc44636cdebfa49c7bf3c3cdcb618751c`; tree contains README only | Android feature is not on main; no merge implied |
| Android feature branch | PR #3 head `97e02c0da28d8cbbdc41aff1fbb05958d58fe050` | Development/test foundation only |
| Device smoke test | PR #3 Run #111 and PR #5 Run #117 both failed at “TALORA home screen did not appear” | **FAIL / P0 implementation blocker**; no green device acceptance claim |
| Project data model | SQLite project/revision/attachment foundations; script content remains primarily source text | PARTIAL; not the approved semantic screenplay/stage-play model |
| Native archive | Versioned ZIP/JSON archive with integrity checks and project/revision/assets | PARTIAL; current content model does not yet represent the complete target typed creative model |
| FDX | Experimental text extraction and heuristic XML writer | Not supported in v1; no interoperability claim |
| DOCX | Minimal paragraph-level OOXML import/export | Partial; styles, tables, comments, tracked changes, embedded media and layout are not preserved |
| RTF | Bounded text/control-word handling | Partial; not full RTF conformance |
| PDF | Simple offline renderer | Partial; not professional screenplay/stage-play pagination or verified Ethiopic embedding |
| Ethiopic fonts | No font binaries/resources in inspected tree | MISSING |
| Amharic/Ethiopic acceptance | Approved plan exists; no completed 30-fixture corpus, two-reviewer sign-off or measured report found | PARTIAL / NOT VERIFIED |
| Ads and entitlements | No advertising SDK, account/entitlement service or commerce runtime in inspected branch | MISSING; A3 not authorized |
| IROKA | Contract documents only in TALORA; no verified TALORA network client/E2E path | UNRESOLVED / NOT VERIFIED |

### 4.1 Exact-head CI qualification

The prior A2 report's successful JVM-only run at SHA `fa8b2c60f1c9787071a98c054ec337d26501ccac` is historical evidence for that exact commit. It must not be substituted for the later PR #3 and PR #5 device smoke failures.

The PR #3 smoke test explicitly waits for the visible text “TALORA” after launching the app and fails with “TALORA home screen did not appear.” PR #5 changed emulator/workflow configuration, but the reported smoke test still failed. A4/device acceptance remains NOT PASSED.

## 5. A2 decision and evidence register update

| Item | Status | Meaning |
|---|---|---|
| ODR-13 FDX v1 exclusion | **VERIFIED-CLOSED FOR POLICY** | FDX remains excluded from v1 |
| FDX implementation compatibility | **NOT VERIFIED** | Source code/tests do not prove Final Draft interoperability |
| Direct dependency inventory | **PARTIAL** | Direct declarations identified; transitive resolution and licensing incomplete |
| SBOM and notices | **MISSING** | No complete generated SBOM/notice package found |
| Ethiopic font binaries and notices | **MISSING** | Approved font families are not bundled/verified in inspected tree |
| Authorized fixture rights | **MISSING** | No complete fixture rights/provenance register found |
| Android device/UI smoke acceptance | **FAIL** | Exact PR #3 and #5 device smoke runs failed |
| Amharic/Ethiopic acceptance evidence | **PARTIAL / NOT VERIFIED** | Plan approved; corpus, reviewers and measured evidence absent |
| Professional screenplay/stage-play engine | **MISSING / PARTIAL FOUNDATION** | Typed semantic model and professional paginator not evidenced |
| Overall A2 | **PARTIAL / NOT CLOSED** | Multiple technical and external evidence gates remain |

## 6. Remaining A2 closure work

The following are still required before an honest final A2 closure recommendation can be marked VERIFIED-CLOSED:

1. Preserve FDX's v1 exclusion consistently across specification, format matrix and release claims; no FDX code removal is authorized in A2.
2. Generate the exact resolved Android dependency graph from the pinned build and complete the license, notice, vulnerability and SBOM review.
3. Identify exact font releases, obtain and verify the two approved Ethiopic font families, record hashes/notices and confirm redistribution/embedding rights. The app must not claim bundled-font readiness before assets exist.
4. Produce the approved minimum 30 rights-cleared screenplay/stage-play fixtures and rights register; appoint at least two independent competent reviewers.
5. Complete the read-only requirement-to-code trace for archive migration, screenplay/stage-play semantics, pagination, PDF, accessibility, privacy, entitlement and IROKA interfaces.
6. Record the failed Android smoke runs as acceptance blockers and define the required evidence for a future authorized A3/A4 repair; do not rerun or alter workflows without authorization.
7. Keep provider, legal, tax, privacy and data-residency questions explicitly deferred or externally gated; no external merchant or counsel evidence is represented as complete.
8. Reconcile the commercial tier matrix with the new rewarded-ad direction in Addendum 002 and the existing project/referral/export entitlements. Reward duration, eligible capability keys, caps and provider/privacy controls remain open.

## 7. Stage conclusion

- **A1:** VERIFIED-CLOSED.
- **A2:** **PARTIAL / NOT CLOSED** — FDX policy disposition is resolved; dependency/font/fixture licensing and technical acceptance evidence remain incomplete.
- **A3:** NOT AUTHORIZED.
- **A4:** NOT STARTED / NOT AUTHORIZED.
- **Merge:** NOT AUTHORIZED.
- **A5:** NOT AUTHORIZED.

This addendum is evidence reconciliation only. It changes no application source, dependencies, workflows, database schema, production configuration or main branch. It does not convert missing external evidence or failed tests into passed gates.
