# TALORA A2 Final Evidence-Closure Assessment

- **Document ID:** TALORA-A2-FECA-001
- **Version:** 1.0.0
- **Assessment date:** 27 November 2026
- **Repository:** `akefadaka-git/talora`
- **Assessment branch:** `docs/a2-commercial-decision-update-2026-11-27`
- **A2 evidence baseline:** `docs/a1-import-export-referral-entitlements`
- **Application evidence head:** `97e02c0da28d8cbbdc41aff1fbb05958d58fe050`
- **Device-stabilization evidence head:** `911531b762aece4a6df800c91488b87cfee47bce`
- **Repository main baseline:** `2562962fc44636cdebfa49c7bf3c3cdcb618751c`
- **Assessment type:** Read-only A2 evidence reconciliation and Owner-facing closure recommendation
- **Overall disposition:** **A2 PARTIAL / NOT VERIFIED-CLOSED**
- **A3 / merge / deployment:** NOT AUTHORIZED

## 1. Executive decision

The requested evidence-closure work has been advanced as far as the currently available repository, CI and authoritative upstream evidence permits. This report consolidates what is verified, what remains missing, and why some gaps cannot be truthfully closed without a resolved build graph, binary inspection, third-party rights evidence or independent reviewers.

**Do not approve A2 closure on the current evidence.** Four material gates remain unresolved:

1. A complete resolved dependency graph, dependency license/notice review, integrity/vulnerability disposition and generated SBOM do not exist in the inspected repository evidence.
2. The exact official Noto Ethiopic v2.102 release artifacts and OFL 1.1 license are identified, but the actual binary files have not been downloaded, hashed, placed in the TALORA tree or verified in the app build; no app notices are bundled.
3. No 30-item rights-cleared Amharic/Ethiopic acceptance corpus or complete format golden-fixture rights register was found. The two-reviewer requirement is now scheduled once at the final pre-A5 release-acceptance gate under Owner-approved Amendment 004; no final review is due or claimed at A2.
4. Android device/UI acceptance remains failed on two exact PR heads; the technical product trace still contains semantic screenplay/stage-play, pagination, font and interoperability gaps.

A2 has resolved important policy questions, including the FDX v1 exclusion and the rewarded-ad direction. Policy closure is not implementation acceptance.

## 2. Dependency inventory and licensing review

### 2.1 Exact declared build inputs inspected

Source: `android/build.gradle.kts`, `android/app/build.gradle.kts`, `android/settings.gradle.kts` at application head `97e02c0da28d8cbbdc41aff1fbb05958d58fe050`.

**Build plugins:**

| Plugin coordinate | Declared version | Evidence |
|---|---:|---|
| `com.android.application` | 9.1.1 | Root Android Gradle build file |
| `org.jetbrains.kotlin.plugin.compose` | 2.2.10 | Root Android Gradle build file |
| `org.jetbrains.kotlin.plugin.serialization` | 2.2.10 | Root Android Gradle build file |

**Direct application/test dependencies:**

| Coordinate | Version declared | Configuration |
|---|---:|---|
| `androidx.compose:compose-bom` | 2026.11.01 | implementation platform |
| `androidx.activity:activity-compose` | 1.13.0 | implementation |
| `androidx.compose.ui:ui` | BOM-managed | implementation |
| `androidx.compose.ui:ui-tooling-preview` | BOM-managed | implementation |
| `androidx.compose.material3:material3` | BOM-managed | implementation |
| `androidx.lifecycle:lifecycle-runtime-ktx` | 2.11.1 | implementation |
| `org.jetbrains.kotlinx:kotlinx-serialization-json` | 1.7.3 | implementation |
| `androidx.compose.ui:ui-tooling` | BOM-managed | debugImplementation |
| `junit:junit` | 4.13.2 | testImplementation |
| `org.robolectric:robolectric` | 4.14.1 | testImplementation |
| `androidx.test:core` | 1.7.0 | testImplementation |
| `androidx.test:runner` | 1.7.0 | androidTestImplementation |
| `androidx.test.ext:junit` | 1.3.0 | androidTestImplementation |
| `androidx.test.uiautomator:uiautomator` | 2.4.1 | androidTestImplementation |

Repository resolution is restricted to Google Maven, Maven Central and Gradle Plugin Portal for plugin resolution; Google Maven and Maven Central for dependencies.

### 2.2 Resolved dependency graph status

**NOT COMPLETED.** The inspected feature-branch tree contains no:
- Gradle dependency lockfile;
- Gradle wrapper distribution/scripts;
- `gradle/verification-metadata.xml`;
- archived `dependencies` task output or resolved dependency graph;
- complete plugin-resolution graph and artifact hashes.

The Android CI jobs used JDK 17 and Gradle 9.3.1 and successfully built the APK, but the retained run artifacts are unit-test reports, lint reports, APKs and emulator reports—not a dependency graph or SBOM. A Compose BOM controls several versions, so the direct declaration list is not the resolved artifact inventory. Transitive libraries, plugin implementation dependencies and variant-specific artifacts are not established by the Gradle file alone.

### 2.3 License and security disposition

**PARTIAL / NOT CLEARED.** No complete per-artifact license decision can be issued from coordinates alone. The following evidence is still required for every resolved direct, transitive and plugin artifact:
- exact resolved GAV/version, variant, repository and artifact SHA-256;
- authoritative license expression and the exact license text/NOTICE applicable to that release;
- copyright and attribution obligations, source-offer or redistribution conditions if any;
- vulnerability/advisory review at the resolved version;
- accepted exception, owner and expiry for any unresolved finding.

Gradle's official dependency-verification guidance recommends resolving the actual graph, generating checksum/signature metadata, reviewing generated entries and separately screening known vulnerabilities. A checksum is integrity evidence, not a license clearance or vulnerability assessment.

### 2.4 SBOM disposition

**SBOM NOT GENERATED — NOT PRESENT.** No CycloneDX or SPDX bill of materials is included in the inspected source or CI artifacts. It would be misleading to label a direct-declaration list as the complete SBOM. The SBOM gate can close only after the exact CI/build variant resolves all plugins and dependencies and emits a machine-readable SPDX or CycloneDX document, followed by review and retention against the exact application build SHA.

Recommended no-paid-service route for a future authorized build:
1. Resolve release and test configurations on the pinned source head using the project's actual Gradle 9.3.1/JDK 17 build environment.
2. Capture `dependencies` and `dependencyInsight` output for every shipped configuration and plugin graph.
3. Add dependency locking and Gradle verification metadata after independently validating artifact checksums/signatures.
4. Generate CycloneDX or SPDX using a pinned, reviewed generator/plugin; include plugins and build tools in the inventory, and distinguish shipped runtime packages from test/build-only packages.
5. Run vulnerability analysis and manual license/NOTICE review; archive the reports and SBOM beside the exact APK hash.
6. Do not accept a report that omits BOM-managed or transitive dependencies.

No dependency, plugin, build file or workflow was modified in this A2 review.

## 3. Ethiopic font release and licensing verification

### 3.1 Exact upstream release records

The official `notofonts/ethiopic` release records and Google Fonts metadata identify the following exact releases:

| Family | Release tag | Release asset | Size | Upstream source commit |
|---|---|---|---:|---|
| Noto Sans Ethiopic | `NotoSansEthiopic-v2.102` | `NotoSansEthiopic-v2.102.zip` | 29,992,463 bytes | `1cc6933c58b7c42bbade15d6c7173897c24759e3` |
| Noto Serif Ethiopic | `NotoSerifEthiopic-v2.102` | `NotoSerifEthiopic-v2.102.zip` | 23,415,065 bytes | `1cc6933c58b7c42bbade15d6c7173897c24759e3` |

The Google Fonts metadata names the variable font binaries:
- `NotoSansEthiopic[wdth,wght].ttf`
- `NotoSerifEthiopic[wdth,wght].ttf`

Both metadata records specify variable axes `wdth` 62.5–100 and `wght` 100–900 and identify Ethiopic-language coverage including Amharic, Geez, Oromo, Tigrinya, Tigre, Blin and Wolaytta.

### 3.2 License

The upstream `notofonts/ethiopic/OFL.txt` was inspected. It identifies Copyright 2022 The Noto Project Authors and the **SIL Open Font License 1.1**.

The license permits use, study, copying, embedding, modification and redistribution subject to its conditions. In particular:
- each distributed copy must include the copyright notice and OFL license;
- font software may be bundled, embedded, redistributed or sold with software, but may not be sold by itself;
- modified versions must respect reserved font names;
- font software and derivatives must remain under OFL.

This is a viable distribution-license route for TALORA when the exact official release files and required notices are included. This is a technical license review, not a jurisdiction-specific legal opinion.

### 3.3 Binary and app verification status

**PARTIAL — RELEASE AND LICENSE IDENTIFIED; APP BINARY NOT VERIFIED.**

- The official release tags and asset IDs are verified.
- The release API did not expose a SHA-256 digest for either ZIP asset.
- The exact release archives and extracted TTF binaries were not downloaded and independently hashed in this assessment.
- The inspected TALORA Android tree has no `res/font` assets and no bundled OFL/copyright notice.
- No build evidence shows either font included in the APK.
- No TALORA glyph-coverage, shaping, fallback, cursor, line-break, PDF-embedding or device-rendering report exists.

Therefore do not claim that the fonts are integrated, bundled or production-verified. The release selection can be frozen as a recommended exact source baseline, but binary acceptance remains open.

### 3.4 Required closure evidence

For each selected TTF:
1. Obtain the ZIP from the exact upstream release URL.
2. Verify the archive and extracted file; record SHA-256 for both and retain provenance.
3. Confirm the extracted font's family/version/name tables match the selected release.
4. Include the original OFL 1.1 and copyright notice in the distributed application notices.
5. Add the exact binary and notice to TALORA only after separate A3 implementation authorization.
6. Test all approved Amharic/Ethiopic fixture characters, combining marks, punctuation, mixed Latin/Ethiopic text, fallback, cursor movement, selection, line wrapping and PDF embedding on supported Android devices.

## 4. Format and Amharic fixture rights register

### 4.1 Evidence found

The inspected tests contain short, inline, synthetic strings and generated in-memory content:
- `ScriptInterchangeTest.kt`: short Amharic text such as `ሰላም`, English screenplay labels and self-generated FDX/DOCX/RTF round-trip payloads.
- `TaloraArchiveTest.kt`: short synthetic Amharic title/body, metadata, revision and a tiny generated byte-array asset.

No external screenplay, published play, commercial script, third-party format sample, 30-item Amharic/Ethiopic corpus, source-author permission, fixture license, attribution record or independent reviewer attestation was found in the inspected tree.

The inline test strings are not a substitute for the approved 30 rights-cleared fixture corpus. Self-generated round trips test TALORA against its own writer and parser; they do not prove interoperability with independent applications.

### 4.2 Rights disposition

**PARTIAL / NOT ESTABLISHED.** No third-party rights have been evidenced for a 30-item corpus. No such external corpus may be presumed authorized. The existing short synthetic tests appear to be project-authored examples with no third-party source attribution in the files, but their provenance is not a formal fixture-rights register and they are not sufficient acceptance evidence.

### 4.3 Rights-acceptance policy for each future fixture

Accept a fixture only when its record includes:
- unique fixture ID, title, format, language/script, size and SHA-256;
- author/rights holder and source/provenance;
- exact license or signed written permission;
- explicit rights for internal testing, modification, commercial product QA, storage and any redistribution required by CI/repository artifacts;
- attribution/notice requirements and any restrictions;
- reviewer and review date;
- retention and removal conditions.

Preferred routes are original commissioned scripts with a written assignment/license, or original synthetic scripts released under an explicit permissive license such as CC0/CC BY 4.0 by an identified rights holder. Public availability, a web page, a file supplied by a third party or an informal verbal statement is not sufficient proof of the necessary rights. Do not use copyrighted commercial screenplays or published plays as golden fixtures without documented permission.

### 4.4 Amharic/Ethiopic acceptance corpus

The previously approved acceptance plan requires at least 30 rights-cleared screenplay/stage-play fixtures. Owner-approved Amendment 004 places the two independent competent reviewers' review at the final pre-A5 release-acceptance gate, not in A2 or each development step. The current repository does not contain the required corpus or rights register; the final review is NOT DUE at this stage.

Required review dimensions include:
- Amharic/Ethiopic character and combining-mark preservation;
- Unicode code-point order and no normalization-induced source loss;
- glyph coverage, shaping, fallback and punctuation;
- cursor movement, selection, editing, wrapping and pagination;
- screenplay and stage-play semantic structure;
- import/export round trips across the supported formats;
- PDF glyph embedding, clipping, overlap and pagination;
- offline persistence, archive integrity and recovery.

Acceptance criteria remain: zero lost/reordered code points, zero missing glyphs/clipping/overlap, and no unresolved critical/high defects. No measured test result or reviewer sign-off was found.

## 5. Android CI failures preserved as explicit blockers

The exact GitHub Actions run and job evidence was rechecked:

| PR / run | Exact head SHA | Build/unit/lint/APK job | Device/emulator job | Disposition |
|---|---|---|---|---|
| PR #3 / Run #111 — [44594749643](https://github.com/akefadaka-git/talora/actions/runs/44594749643) | `97e02c0da28d8cbbdc41aff1fbb05958d58fe050` | SUCCESS | FAILURE | **BLOCKER** |
| PR #5 / Run #117 — [44690787519](https://github.com/akefadaka-git/talora/actions/runs/44690787519) | `911531b762aece4a6df800c91488b87cfee47bce` | SUCCESS | FAILURE | **BLOCKER** |

Both workflow runs completed their build, unit-test and APK packaging job successfully. Both failed the separate install/launch/lifecycle/SAF/accessibility emulator job. The smoke test's initial launch assertion is `TALORA home screen did not appear`; later assertions for Amharic controls, editor, process-restart persistence, SAF import/export and accessibility were therefore not established by these failed runs.

The device-stabilization PR #5 changed the emulator/workflow configuration and corrected a UiAutomator wait-condition compilation issue, but the subsequent device run still failed. This is not a green end-to-end result.

**A2 record:** retain both failed runs as P0 device acceptance blockers. Do not rerun, modify workflows, alter app source or claim A4 verification in this A2 review. Repair and rerun belong to a separately authorized implementation/verification stage.

## 6. Requirement-to-evidence technical trace

| Requirement / acceptance area | Evidence in inspected source or CI | Result |
|---|---|---|
| Android app compiles and packages | Build/unit/APK job passed on PR #3 and #5 exact heads | **PASS for those exact build jobs only** |
| Android app launches and renders home | Device smoke launch assertion fails on both runs | **FAIL / P0 blocker** |
| Amharic primary controls/accessibility | Test code contains assertions, but launch failed before assertions | **NOT VERIFIED** |
| Editor opens and exposes title/body fields | Smoke test assertions exist; device run did not pass launch | **NOT VERIFIED** |
| Process restart and local persistence | Smoke test contains force-stop/reopen test; device job failed before successful completion | **NOT VERIFIED end-to-end** |
| Android SAF archive export/import | Smoke test covers picker flow; emulator job failed | **NOT VERIFIED end-to-end** |
| Native `.talora` archive integrity | Codec and unit tests cover hashes, path traversal, entries and schema; unit job passed | **PARTIAL PASS at JVM test level; device integration not passed** |
| Project/revision/asset storage | SQLite project/revision/asset implementation and unit tests present | **PARTIAL**; migration/device acceptance not closed |
| TXT/Fountain | Basic source paths and self-tests present | **PARTIAL**; no independent application conformance corpus |
| FDX v1 | ODR-13 excludes v1; code contains experimental self-roundtrip | **POLICY CLOSED — FORMAT NOT SUPPORTED** |
| DOCX | Minimal paragraph-level OOXML path and self-roundtrip test | **PARTIAL**; styles, tables, comments, tracked changes, media/layout not preserved |
| RTF | Bounded text/control handling and self-roundtrip test | **PARTIAL**; not full RTF conformance |
| PDF professional screenplay/stage-play output | Simple offline text-oriented renderer | **NOT ACCEPTED**; pagination and typography requirements not evidenced |
| Typed screenplay/stage-play semantic model | Current content remains substantially source-text-oriented | **GAP** |
| Ethiopic fonts bundled | No font binaries/resources or notices in inspected tree | **MISSING** |
| Ethiopic shaping and glyph acceptance | No 30-fixture corpus or reviewer evidence | **MISSING / NOT VERIFIED** |
| Dependency license/SBOM | Direct declarations only; no resolved graph, SBOM or complete notices | **MISSING / NOT CLEARED** |
| Format fixture rights | No complete provenance/permission register | **MISSING** |
| IROKA private API integration | TALORA-facing contract documents; no verified network client/E2E path | **NOT VERIFIED** |
| Ads/rewarded entitlement implementation | Commercial policy only; no ad SDK or entitlement runtime | **NOT IMPLEMENTED / NOT AUTHORIZED** |
| Privacy, provider and legal gates | No selected provider, completed PIA or verified payment/settlement contract | **OPEN / EXTERNAL GATE** |

## 7. FDX v1 disposition

The Owner-approved ODR-13 exclusion remains binding: **FDX is deferred and not a supported v1 format**. The later experimental parser/writer code and self-generated round-trip test do not amend that decision or establish compatibility with Final Draft. FDX may be reconsidered only through an explicit Owner amendment supported by lawful fixtures, target-version testing and independent conformance evidence.

## 8. Commercial decision reconciliation

The 27 November 2026 Owner direction is recorded in Addendum 002 and sections 22–23 of the A2 technical decision register:
- meaningful Free experience;
- advertising revenue from non-purchasers;
- opt-in rewarded ads grant temporary access only to selected lower-premium-tier capabilities;
- no interruption to writing/editing/saving/export;
- reward duration, eligible capability keys, caps, stacking, provider and privacy controls remain open.

This commercial direction is **VERIFIED-CLOSED FOR POLICY DIRECTION**, not implemented or technically accepted. Earlier referral, project-bound watermark-free, fixed-term watermark-free and export entitlements remain distinct.

## 9. A2 closure gap register and owners/evidence required

| Gap | Current status | Evidence needed to close | Stage |
|---|---|---|---|
| Resolved dependency inventory | PARTIAL | Exact variant/plugin/transitive graph, artifact hashes | Authorized build evidence |
| License/NOTICE review | NOT CLEARED | Per-artifact license and notice review, exceptions | A2 evidence |
| SBOM | MISSING | Valid SPDX/CycloneDX from exact resolved build, reviewed | Authorized build evidence |
| Font release provenance | VERIFIED | v2.102 tags, metadata, upstream OFL 1.1 | A2 complete |
| Font binary/hash | PARTIAL | Downloaded exact ZIP/TTF, SHA-256, name-table verification | Evidence + asset handling |
| Font app notices/integration | MISSING | OFL/copyright notice in package and exact APK inspection | A3 then A4 |
| Format fixture rights | MISSING | Written permission/license and complete provenance register | Owner/rights-holder evidence |
| 30-item Amharic/Ethiopic corpus | MISSING | 30 rights-cleared fixtures with hashes and metadata | Evidence acquisition |
| Independent language review | NOT DUE — PRE-A5 GATE | Two competent reviewers' signed reports against the exact release candidate; Amendment 004 governs timing | Pre-A5 release acceptance |
| Android launch/device tests | FAIL | Repair evidence and successful exact-head device runs | A3/A4 |
| Screenplay/stage-play model and pagination | GAP | Approved typed model, trace and conformance tests | A2 design then A3/A4 |
| External format interoperability | PARTIAL | Independent application/version golden tests for each claimed format | A2 plan then A3/A4 |
| IROKA network integration | NOT VERIFIED | Approved API contract lineage plus TALORA client and E2E test | A2 contract then A3/A4 |
| Ads, provider and privacy | OPEN | Owner parameters, provider/legal/PIA/data-flow evidence | A2 external decision then A3/A4 |

## 10. Final A2 recommendation

- **A1:** VERIFIED-CLOSED.
- **A2:** **PARTIAL / NOT VERIFIED-CLOSED**. Commercial policy and FDX v1 policy are closed at decision level. Dependency/SBOM, font binary/app, fixture-rights/reviewer and device/technical acceptance gates remain open.
- **A3:** NOT AUTHORIZED.
- **A4:** NOT STARTED / NOT AUTHORIZED.
- **A5:** NOT AUTHORIZED.
- **Merge:** NOT AUTHORIZED.

### Owner decision requested

The evidence supports only one honest conclusion: **do not close A2 yet**. The remaining evidence cannot be fabricated or inferred. The necessary build evidence must be produced and lawful fixture permissions obtained or original fixtures commissioned with explicit rights. Under Owner-approved Amendment 004, the two independent reviewers are appointed for the final pre-A5 release-candidate review, not repeatedly during development. A3 implementation remains separately gated.

## 11. Scope and integrity statement

This assessment is based on read-only inspection of the public TALORA repository, exact source files and existing workflow run/job/artifact metadata, plus official Noto Ethiopic release/metadata/license records and official Gradle dependency-verification guidance. No source code, dependency, workflow, database, production configuration, main branch or existing approval record was modified. No workflow was rerun, no paid service was activated, no PR was merged, and no deployment was performed.

The official Noto Ethiopic v2.102 releases and SIL OFL 1.1 are cited as upstream provenance, not as evidence that the font binaries are already present in TALORA. The declared Gradle dependencies are not represented as a resolved graph or SBOM.


## 12. External authoritative references

- [Noto Sans Ethiopic Google Fonts metadata](https://github.com/google/fonts/blob/main/ofl/notosansethiopic/METADATA.pb)
- [Noto Serif Ethiopic Google Fonts metadata](https://github.com/google/fonts/blob/main/ofl/notoserifethiopic/METADATA.pb)
- [Noto Sans Ethiopic v2.102 release](https://github.com/notofonts/ethiopic/releases/tag/NotoSansEthiopic-v2.102)
- [Noto Serif Ethiopic v2.102 release](https://github.com/notofonts/ethiopic/releases/tag/NotoSerifEthiopic-v2.102)
- [Noto Ethiopic upstream OFL.txt](https://github.com/notofonts/ethiopic/blob/main/OFL.txt)
- [Gradle official dependency verification and security guidance](https://docs.gradle.org/current/userguide/dependency_verification.html)
- [Android Developers dependency verification guidance](https://developer.android.com/build/dependency-verification)


## 10. Owner-approved review-timing amendment — 27 November 2026

Amendment 004, [TALORA A2 Independent Review Timing Amendment](TALORA_A2_Independent_Review_Timing_Amendment_004_v1.0.0.md), is binding for reviewer cadence. The two independent competent reviewers are required once at the final pre-A5 release-acceptance gate against the exact release candidate. They are not required at A1, A2, each A3 development step or routine A4 internal verification. Targeted re-review is required only where a material post-review change affects the reviewed acceptance surface. The 30-fixture rights-cleared corpus, fixture rights register and substantive acceptance criteria remain unchanged. Final review is NOT DUE at A2.


## 11. A2 evidence-gap closure continuation — 27 November 2026

Following the Owner's explicit authorization, documentation-only evidence work continued on PR #6. No A3 work, application-source modification, workflow modification/rerun, merge or deployment was performed.

### Gradle dependency evidence

The exact Android build files were inspected at source SHA `911531b762aece4a6df800c91488b87cfee47bce`, the PR #5 source head. Direct plugin and dependency declarations, repository configuration, Android SDK targets, Java compatibility and the existing successful Gradle build event are now recorded in [TALORA A2 Gradle Dependency Evidence Assessment 005](TALORA_A2_Gradle_Dependency_Evidence_Assessment_005_v1.0.0.md).

Run #117 (ID `44690787519`) verified the exact source SHA, used Temurin 17.0.21+9 and Gradle 9.3.1, and completed `gradle -p android testDebugUnitTest lintDebug assembleDebug --no-daemon` successfully. However, the Gradle setup action explicitly had `dependency-graph: disabled` and the log set `GITHUB_DEPENDENCY_GRAPH_ENABLED: false`. No dependency report or SBOM was uploaded. Thus the build proves that dependency resolution was sufficient for that successful build, but it does not provide the complete resolved graph, dependency artifact hashes/provenance, SPDX/CycloneDX SBOM, complete license/NOTICE review or vulnerability disposition. These remain **MISSING / NOT CLEARED**. No workflow or build configuration was changed to obtain them.

### Fixture rights

A controlled 30-slot register and acquisition plan is now recorded in [TALORA A2 Fixture Rights Register and Acquisition Plan 006](TALORA_A2_Fixture_Rights_Register_Acquisition_Plan_006_v1.0.0.md). It defines the permission scope, provenance, rights-holder, attribution, retention and hash evidence required before use. It does not create or presume any rights.

All 30 fixture entries remain **MISSING** pending original/commissioned material and documented rights-holder grants or licenses. No fixture files were added and no fixture rights are claimed.

### Current disposition

- Gradle direct declarations and build execution evidence: **VERIFIED**.
- Resolved dependency/plugin graph, SBOM, artifact-level provenance, license/NOTICE and vulnerability review: **MISSING / NOT CLEARED**.
- Fixture register template/acquisition requirements: **DOCUMENTED**.
- Thirty rights-cleared fixture files and rights evidence: **MISSING**.
- Final two-reviewer review: **NOT DUE — PRE-A5**, per Owner-approved Amendment 004.
- Device acceptance: **FAIL / P0 BLOCKER** remains, based on Run #117 and Run #111.
- A2 overall: **PARTIAL / NOT VERIFIED-CLOSED**.

### Immediate next step

The next technical evidence item is a separately authorized, reproducible Gradle evidence execution that emits the full configuration-specific dependency/plugin graph and SPDX or CycloneDX SBOM tied to the exact Android source SHA, followed by license/NOTICE and vulnerability review. The current workflow does not emit these outputs, so this cannot be honestly completed by documentation alone without a suitable already-authorized execution capability. Fixture rights clearance proceeds in parallel by obtaining original/commissioned fixtures and the corresponding rights-holder grants; no reviewer appointment is needed until the pre-A5 gate.
