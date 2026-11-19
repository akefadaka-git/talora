# TALORA Development, Production-Readiness, and Consolidated Independent Review Policy

**Project:** TALORA — Android-first scriptwriting and creative production application  
**Repository:** `akefadaka-git/talora`  
**Status:** Owner-directed development policy  
**Authority:** The Owner retains approval authority over scope, stage transitions, release, and deployment.

## 1. Policy decision

TALORA is to be fully implemented, integrated, enhanced, and internally verified before it is submitted for independent technical and security review.

Independent review is a **single consolidated final-stage activity**, not a recurring approval requirement for every feature, implementation task, remediation, pull request, or development milestone.

This policy does not remove continuous internal testing, secure engineering practices, evidence recording, or Owner-governed stage gates. It changes the timing and scope of independent review: review the completed release candidate as a whole, rather than commissioning a separate independent review at each intermediate step.

## 2. Required delivery sequence

### Stage 1 — Complete implementation and integration

- Implement the approved TALORA product scope across the Android application and its supporting components.
- Complete the approved user journeys, script editor, project/script management, local persistence, import/export capabilities, and IROKA integration according to the approved product and integration specifications.
- Complete required enhancements, cross-feature integration, error handling, and usability work.
- Preserve approved product decisions, architecture contracts, data formats, and repository governance.
- Keep unrelated changes intact and avoid unapproved scope expansion.
- Record material design decisions, dependencies, migrations, and known limitations.

### Stage 2 — Continuous internal verification

Perform appropriate engineering checks throughout implementation. Depending on the feature and platform, these include:

- Unit, component, feature, integration, and end-to-end tests.
- Regression tests for defects and security controls.
- Android build, packaging, installation, startup, navigation, and lifecycle checks.
- Local data persistence, recovery, export/import, and file-integrity checks.
- Offline behavior and network-failure handling.
- Authentication, authorization, privacy, and secure-storage checks where those capabilities are in scope.
- Dependency, static-analysis, secret-scanning, and vulnerability checks.
- Supported Android version, device-size, accessibility, and performance checks.
- IROKA integration contract, timeout, failure, and data-boundary tests where applicable.

Record exact source SHAs, test commands or workflow links, environment/toolchain versions, results, failures, and unresolved risks. A failed or unavailable check must be reported honestly and must not be labelled as passed. Findings should be fixed and re-tested during development; they do not each require an independent reviewer.

### Stage 3 — Production-readiness completion

Before creating the final review candidate, complete and internally assess the production-readiness baseline, including:

- Functional completeness against the approved TALORA requirements.
- Stable script/project data model and native `.talora` format behavior.
- Reliable local-first/offline operation and data durability.
- Import/export correctness, format compatibility, and entitlement enforcement as specified.
- Security, privacy, permissions, and least-privilege behavior.
- Error handling, crash resilience, observability appropriate to the client application, and maintainability.
- Performance, accessibility, localization, and supported-device compatibility.
- Dependency and licensing/NOTICE inventory.
- Build reproducibility, versioning, signing configuration, and release documentation.
- Backup/recovery or user-facing data portability safeguards appropriate to the approved architecture.
- Documented known limitations and accepted exceptions.

Production readiness must be supported by evidence. Do not describe the application as production ready merely because implementation is complete or an APK builds. Any remaining exception must identify its impact, mitigation, owner, and disposition.

### Stage 4 — Final test APK and release-candidate evidence

After the implementation and internal verification baseline is complete:

- Build the final test APK from a designated release-candidate commit.
- Record the repository, branch, exact source SHA, build workflow/environment, Android/Gradle/JDK toolchain versions, build variant, version name/code, and artifact checksum.
- Install and test the APK on supported Android configurations.
- Run agreed smoke, functional, regression, offline, persistence, import/export, and upgrade tests as applicable.
- Preserve build logs, test results, and the APK artifact or its authoritative repository release location.
- Confirm that the APK corresponds to the exact source SHA submitted for review.

The APK is a test/review artifact until a separate release decision authorizes distribution. A successful build alone does not establish production readiness.

### Stage 5 — One consolidated independent review

Only after the completed application and final test APK are available should the project be submitted for independent technical and security review.

The review package should contain:

- Exact release-candidate source SHA and repository/branch references.
- Approved product requirements and architecture documentation.
- IROKA integration contract and implementation evidence, where applicable.
- Test plans, test results, and CI/workflow evidence.
- Dependency, licensing/NOTICE, and vulnerability reports.
- Data-storage, privacy, permissions, and security documentation.
- APK, checksum, build provenance, and device-test results.
- Known risks, accepted exceptions, and remediation history.

The independent reviewer should assess the completed application as a whole, including cross-feature behavior, data handling, offline operation, import/export, integration boundaries, and release artifact integrity. Findings must be prioritized, recorded, remediated where required, and re-tested against exact SHAs.

A material code or architecture change after the review may require targeted re-review or an updated consolidated review, depending on its impact. No independent review is required at every intermediate development step unless the Owner expressly requests it or a mandatory legal, contractual, regulatory, or safety obligation requires it.

### Stage 6 — Owner release decision and deployment

Independent review does not itself authorize merge, public release, production distribution, external service activation, or deployment.

Present the completed evidence and independent review disposition to the Owner for an explicit release decision. Deployment remains a separate stage requiring its own approval. Approval to implement, test, build an APK, or conduct independent review must not be interpreted as deployment authorization.

## 3. Governance and evidence requirements

- Preserve the approved TALORA stage gates and Owner-controlled scope.
- Use exact commit SHAs as the basis for implementation and verification claims.
- Keep frozen baselines immutable unless the Owner explicitly authorizes a change.
- Do not merge or deploy merely because a pull request is complete or automated checks pass.
- Preserve unrelated repository work.
- Distinguish internal verification from independent review.
- Use clear dispositions: **PASS**, **VERIFIED**, **PARTIAL**, **FAILED**, **MISSING**, **UNRESOLVED**, or **NOT RUN**.
- Report unavailable CI capacity, paid services, or test environments honestly and prefer feasible no-cost verification paths where possible.
- Do not claim production readiness, final APK validation, independent review, or release without corresponding evidence.

## 4. Supersession and interpretation

This policy supersedes any prior working assumption that independent review must be requested or completed for every incremental TALORA implementation step. It does not cancel approved requirements, known defects, security obligations, internal test requirements, Owner approvals, or project-specific stage gates.

Where this policy conflicts with a specific Owner-approved TALORA decision, applicable law, or mandatory external requirement, pause the affected action and resolve the conflict explicitly. The Owner retains authority over project scope and release decisions.

## 5. Application to current TALORA development

Continue the currently authorized TALORA implementation and enhancement work. Perform internal verification continuously and record evidence as features are completed. Consolidate the remaining work into one release-candidate baseline, generate and validate the final test APK, and then arrange one independent review of that completed candidate. Keep the final release/deployment decision separate and subject to explicit Owner approval.
