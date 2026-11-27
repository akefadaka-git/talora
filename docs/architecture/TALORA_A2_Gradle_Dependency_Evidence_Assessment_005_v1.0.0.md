# TALORA A2 — Gradle Dependency Evidence Assessment

- **Document ID:** TALORA-A2-DEP-005
- **Version:** 1.0.0
- **Assessment date:** 27 November 2026
- **Evidence source:** TALORA Android CI run #117 and source commit `911531b762aece4a6df800c91488b87cfee47bce`
- **Related PR:** #5 — https://github.com/akefadaka-git/talora/pull/5
- **A2 documentation record:** PR #6
- **Disposition:** **DIRECT DECLARATIONS VERIFIED; RESOLVED GRAPH / SBOM NOT PRODUCED**

## 1. Exact source and build evidence

The inspected Android source commit is `911531b762aece4a6df800c91488b87cfee47bce`. The following build files were fetched at that exact commit:

| File | Git blob SHA |
|---|---|
| `android/build.gradle.kts` | `6f0c3df25d5c6d060ed38ff75f430259268d80ce` |
| `android/app/build.gradle.kts` | `5a30e703fc41b14861514ef3c39282226a3dfba5` |
| `android/settings.gradle.kts` | `e163fc93065b13384eda80c264d884dc35929392` |
| `android/gradle.properties` | `73403e4fc822ce5be20e6b4bf4a0e62ac3e463aa` |

The same build-file blob SHAs were observed at PR #3 head `97e02c0da28d8cbbdc41aff1fbb05958d58fe050` and PR #5 head `911531b762aece4a6df800c91488b87cfee47bce`.

### Declared Gradle plugins

| Plugin ID | Declared version |
|---|---:|
| `com.android.application` | 9.1.1 |
| `org.jetbrains.kotlin.plugin.compose` | 2.2.10 |
| `org.jetbrains.kotlin.plugin.serialization` | 2.2.10 |

### Direct declared application/test dependencies

| Configuration | Coordinate |
|---|---|
| implementation platform | `androidx.compose:compose-bom:2026.11.01` |
| implementation | `androidx.activity:activity-compose:1.13.0` |
| implementation (BOM-managed) | `androidx.compose.ui:ui` |
| implementation (BOM-managed) | `androidx.compose.ui:ui-tooling-preview` |
| implementation (BOM-managed) | `androidx.compose.material3:material3` |
| implementation | `androidx.lifecycle:lifecycle-runtime-ktx:2.11.1` |
| implementation | `org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3` |
| debugImplementation (BOM-managed) | `androidx.compose.ui:ui-tooling` |
| testImplementation | `junit:junit:4.13.2` |
| testImplementation | `org.robolectric:robolectric:4.14.1` |
| testImplementation | `androidx.test:core:1.7.0` |
| androidTestImplementation | `androidx.test:runner:1.7.0` |
| androidTestImplementation | `androidx.test.ext:junit:1.3.0` |
| androidTestImplementation | `androidx.test.uiautomator:uiautomator:2.4.1` |

### Repository configuration

- Plugin repositories: Google Maven, Maven Central, Gradle Plugin Portal.
- Dependency repositories: Google Maven, Maven Central.
- Android namespace: `com.talora.app`.
- Compile SDK: 37; min SDK: 26; target SDK: 37.
- Java source/target compatibility: 17.
- AndroidX enabled; non-transitive R class enabled.

This is a direct-declaration inventory only. BOM-managed modules do not have versions in the app build file; the BOM's constraints and all transitive/plugin dependencies must be resolved from an actual build.

## 2. Existing CI resolution event

Run #117: https://github.com/akefadaka-git/talora/actions/runs/44690787519

- Workflow: **Android test APK**; workflow ID `423636737`.
- Run ID: `44690787519`; run number: `117`.
- Source SHA checked out and verified by CI: `911531b762aece4a6df800c91488b87cfee47bce`.
- Build job ID: `136135805536`; conclusion: **SUCCESS**.
- Emulator job ID: `136135805760`; conclusion: **FAILURE**.
- Build runner: GitHub-hosted Ubuntu runner; log reports Ubuntu 26.04.1 LTS / image `ubuntu-26.04`.
- JDK: Temurin 17.0.21+9.
- Gradle: 9.3.1, installed by `gradle/actions/setup-gradle@v4`.
- Android SDK platform/build tools: API 37 / build-tools 37.0.0.
- Exact executed build command: `gradle -p android testDebugUnitTest lintDebug assembleDebug --no-daemon`.
- Result: `BUILD SUCCESSFUL in 2m 44s`.

The build succeeded and therefore demonstrates that the build resolved enough dependencies to compile, test, lint and package the APK at that time. It does **not** preserve a complete dependency graph or artifact-level provenance record.

## 3. Why the resolved graph and SBOM remain missing

The Run #117 build log explicitly reports:
- Gradle setup action `dependency-graph: disabled`.
- `GITHUB_DEPENDENCY_GRAPH_ENABLED: false`.
- The workflow uploads unit-test reports, lint report, APK and emulator reports only.
- The build command does not invoke a dependency-report task or SBOM generator.

Repository checks at the inspected application source commit did not locate:
- a Gradle dependency lockfile at the checked root/Android locations;
- Gradle dependency verification metadata (`verification-metadata.xml`);
- an archived `dependencies` / `dependencyInsight` output;
- SPDX or CycloneDX SBOM output;
- a complete plugin and transitive dependency inventory.

No complete wrapper distribution configuration was found at the checked root or `android/gradle/wrapper` paths. CI used the explicitly installed Gradle 9.3.1 executable; the build record must not be misrepresented as a wrapper-pinned build.

## 4. Artifact evidence retained by CI

Run #117 uploaded the following artifacts (the artifact metadata exposes SHA-256 digests for the ZIP archives, not for every dependency):

| Artifact | Artifact ID | Size | ZIP SHA-256 |
|---|---:|---:|---|
| `talora-android-emulator-reports` | 14609248755 | 54,386 bytes | `a8ab04e237d0451a393d4c9287aa5265a8254154909c55e04b3ab79efeb6b929` |
| `talora-android-unit-test-reports` | 14609175079 | 15,796 bytes | `7226057ad5845100310b797107d0b08fbb2f2222e1b9a7ec02797fe1f4273b8` |
| `talora-android-debug-apk` | 14608951005 | 12,715,800 bytes | `68495dbe32dcb6f293230da322caa7a3f485a089e9dd01fc02e85d008b4e9bb0` |

The artifact archive digests do not substitute for hashes of the resolved dependency artifacts. The artifacts were retained by GitHub with an expiry of 4 December 2026; preserve them before expiry if they are needed as evidence.

## 5. CI failure remains separate from dependency resolution

The build/unit/lint/APK job passed. The emulator job failed both device tests at the shared launch assertion: `TALORA home screen did not appear`. The failure occurs before later UI/SAF assertions can be accepted as end-to-end evidence.

This failure does not prove a dependency-resolution failure. It remains a separate P0 device-acceptance blocker.

## 6. Closure requirements

To produce a verifiable resolved dependency record, a future separately approved technical evidence run must, at minimum:
1. Check out and verify the exact application source SHA.
2. Record the complete Gradle plugin and configuration-specific dependency graph (including runtime, debug, unit-test and instrumentation configurations).
3. Retain the exact build environment, Gradle/AGP/JDK/SDK versions and repository configuration.
4. Generate a machine-readable SPDX or CycloneDX SBOM from the resolved graph.
5. Capture artifact coordinates, selected versions, repository origins and cryptographic hashes where available.
6. Review all direct/transitive/plugin licenses and required NOTICE obligations.
7. Run vulnerability analysis against the resolved versions and record database/tool versions, timestamp, findings, exceptions and remediation.
8. Preserve raw reports and their hashes, linked to the exact source SHA.

The existing workflow does not emit this evidence. This assessment intentionally does not modify the workflow or application source and does not claim the missing outputs were generated. A new evidence-producing execution path or a suitable existing capability must be separately authorized before it is used.

## 7. Final status

| Evidence | Status |
|---|---|
| Exact source commit | **VERIFIED** |
| Direct Gradle plugin declarations | **VERIFIED** |
| Direct dependency declarations | **VERIFIED** |
| Repository configuration and Android/Java build targets | **VERIFIED** |
| Existing successful Gradle build event | **VERIFIED** |
| Complete resolved plugin/transitive graph | **MISSING** |
| Dependency locks / verification metadata | **NOT FOUND** |
| Dependency artifact hashes/provenance | **MISSING** |
| SPDX/CycloneDX SBOM | **MISSING** |
| License/NOTICE review of all resolved dependencies | **NOT CLEARED** |
| Vulnerability disposition of resolved dependencies | **NOT CLEARED** |

**Overall: PARTIAL / NOT CLEARED.** No dependency graph, SBOM, license clearance or vulnerability clearance is fabricated or inferred from the successful build.
