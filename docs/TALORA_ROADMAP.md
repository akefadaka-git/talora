# TALORA Next-Stage Roadmap

**Status:** Proposed roadmap for Owner review  
**Governing specification:** [TALORA Product Specification](TALORA_PRODUCT_SPECIFICATION.md)  
**Current stage:** A1 — specification  
**Implementation status:** Not authorized  
**Roadmap rule:** Each stage has its own entry criteria, evidence, acceptance gate, and explicit Owner authorization. No stage is automatically approved by completion of the prior stage.

## Owner-approved direction

- Bundle **both Noto Sans Ethiopic and Noto Serif Ethiopic** in the app; make the core Ethiopic font capability available to every tier.
- Use a **four-level subscription model**: Free, Creator, Professional, Studio (tier names remain proposed pending confirmation).
- Complete the product specification first. Reconcile the existing repository and implementation read-only before any implementation begins.

## Roadmap overview

| Stage | Outcome | Scope | Exit evidence | Status |
|---|---|---|---|---|
| A1 | Approved product specification | Product scope, user roles, typography, script editor, character/scene, director, production, IROKA, collaboration, subscriptions, NFRs, TBDs | Owner-approved specification and decision register | **IN PROGRESS — document prepared** |
| A2 | Verified as-is baseline and reconciliation | Read-only repository, PR, branch, architecture, test, dependency, and compatibility audit | Requirement-to-code matrix; gaps, risks, dependencies, and exact SHA evidence | **NEXT — not started** |
| A3 | Incremental implementation | Only approved work packages, each separately scoped | PRs, exact SHAs, tests, review evidence, no unauthorized merge | **NOT AUTHORIZED** |
| A4 | Independent verification | Acceptance, regression, accessibility, security, performance, and release-candidate tests | Test evidence, defect register, verified closure report | **NOT AUTHORIZED** |
| A5 | Deployment/release | Release packaging, signing, distribution, monitoring, rollback | Separate Owner deployment approval and release evidence | **SEPARATE GATE — NOT AUTHORIZED** |

## A1 — Product specification and decision freeze

**Deliverable:** `docs/TALORA_PRODUCT_SPECIFICATION.md`

1. Record the two-font Ethiopic strategy and its licensing/offline requirements.
2. Define the four subscription levels and data-ownership/entitlement invariants.
3. Specify structured screenplay and stage-play elements and pagination.
4. Specify character profiles/maps, scene navigation, story/beat management, and revision history.
5. Specify director annotation layers and production-management workflows.
6. Specify IROKA capability boundaries, collaboration, privacy, accessibility, and test expectations.
7. Resolve or explicitly carry forward commercial and technical TBDs.
8. Obtain Owner review and close A1 before A2 begins.

**A1 exit gate:** Owner confirms the product scope, four-tier direction, font strategy, tier names or their provisional status, and the remaining TBD disposition.

## A2 — Read-only technical reconciliation

No code changes, branch rewrites, merges, or deployments during A2.

1. Establish the authoritative repository branch and exact commit SHAs.
2. Reconcile the main branch, development branch, open PRs, and their base/head relationships.
3. Map every specification requirement ID to existing files, tests, docs, and verified behavior.
4. Verify current Android architecture, persistence, autosave, revision/asset handling, and `.talora` v1 archive behavior.
5. Reconcile documentation inconsistencies around PDF and FDX support; distinguish code presence from verified interoperability.
6. Audit current font resources and determine whether either Ethiopic family is already bundled, licensed, and correctly wired.
7. Inspect script editor structure and determine whether content is plain text, semantic elements, or hybrid.
8. Inspect character, scene, story, director, production, collaboration, and entitlement implementation; mark each PASS / PARTIAL / MISSING / UNKNOWN / NOT RUN.
9. Review dependency/security posture, test workflows, exact-head CI evidence, and device coverage.
10. Produce an implementation dependency graph and prioritized gap register.

**A2 exit gate:** A read-only evidence report, exact requirement traceability, risk/dependency register, and proposed A3 work packages. No implementation begins until the Owner separately authorizes A3.

## A3 — Implementation work packages (future; authorization required)

Recommended sequence, subject to A2 findings and Owner approval:

### A3.1 — Ethiopic typography foundation
- Bundle Noto Sans Ethiopic and Noto Serif Ethiopic with license notices.
- Define font families, weights, fallbacks, typography tokens, and user controls.
- Verify offline rendering, cursor behavior, selection, copy/paste, search, accessibility, and PDF embedding.
- Keep fonts available to Free, Creator, Professional, and Studio.

### A3.2 — Structured script document model
- Define typed screenplay and stage-play elements with stable IDs.
- Implement element transitions, keyboard behavior, style/template rules, undo/redo, and source-preserving conversion.
- Add migration compatibility for existing text-based projects.

### A3.3 — Professional editor and pagination
- Implement title pages, scene headings, action, character cues, dialogue, parentheticals, transitions, stage directions, shots, dual dialogue, page numbering, continuation, and scene numbering.
- Add print layout and export fidelity tests.

### A3.4 — Character and scene intelligence
- Implement character profiles, aliases, relationships, character map, cast links, scene navigator, scene metadata, and continuity suggestions.
- Preserve list-based accessible alternatives for visual maps.

### A3.5 — Story development and revision tools
- Implement acts, sequences, beats, plot threads, outline/board views, revision comparison, and stable scene identity.
- Ensure restore creates a new revision and never rewrites historical snapshots.

### A3.6 — Director's workspace
- Implement separate writer, director, production, and approved-script layers.
- Add blocking, performance, camera, lighting, sound, prop, set, continuity, and rehearsal notes.
- Add authorship, timestamps, review, and approval.

### A3.7 — Production management
- Implement breakdown tags, cast/crew, locations, props/wardrobe/equipment, schedules, call sheets, daily reports, and production document exports.
- Gate higher-complexity production and team features by verified entitlement and role.

### A3.8 — Entitlement and subscription foundation
- Implement centralized capability keys and four-tier policies.
- Add verified subscription state, signed offline cache, grace/expiry, restore, upgrade/downgrade, organization seats, and audit history.
- Preserve user-owned data and basic project access independently of subscription.
- Do not implement billing until provider, legal, regional, and commercial decisions are approved.

### A3.9 — IROKA integration
- Reconcile the versioned IROKA contract and verify each capability.
- Add opt-in suggestion workflows with user approval and no silent canonical edits.
- Enforce capability quotas through entitlement policy.

### A3.10 — Collaboration and studio workspaces
- Implement project-scoped roles, comments, review, approvals, sync, conflict resolution, and organization membership.
- Add privacy controls and auditability.

## A4 — Verification and release-candidate acceptance (future; authorization required)

- Ethiopic glyph/rendering golden tests across supported devices and font scales.
- Amharic and mixed Ethiopic/Latin script editor E2E tests.
- Professional pagination and PDF embedded-font verification.
- Script-format import/export fixtures and round-trip fidelity reports.
- Native archive round-trip, migration, revision, asset, and recovery tests.
- Character/scene relationship integrity and accessibility checks.
- Director-layer isolation and production release approval tests.
- Entitlement tamper, offline expiry, grace, revocation, account-switch, and data-retention tests.
- Role/organization isolation, sync conflict, and audit-log tests.
- IROKA contract and capability tests with explicit unavailable/failure paths.
- Performance, battery, storage, privacy, and security review.
- Produce a defect register and exact commit/workflow evidence; no unsupported PASS claims.

## A5 — Deployment and distribution (separate Owner approval)

Only after A4 closure:
- Approve release scope, version, signing, distribution channel, and release notes.
- Verify package identity, signing, permissions, privacy disclosures, and store/distribution requirements.
- Prepare rollback and incident response.
- Deploy only after explicit A5 authorization; A4 completion does not authorize deployment.

## Cross-stage governance and reporting

Every stage report must include:
- Stage and scope; exact branch and SHA(s).
- Changed files and PR links, if any.
- Workflow/test run IDs and artifact references, if available.
- PASS / VERIFIED-CLOSED / PARTIAL / MISSING / UNRESOLVED / UNKNOWN / NOT RUN labels.
- Risks, dependencies, unresolved decisions, and stage-specific completion percentage.
- Overall readiness and one immediate next step.

**Protected constraints:** no unauthorized code changes; no merge or deployment without separate approval; preserve unrelated work; do not claim a feature is complete based solely on its presence in source code; keep A5 separate.

## Immediate next stage

**A1 Owner review and closure.** Once A1 is explicitly closed, begin A2 as a strictly read-only repository and implementation reconciliation. A3 remains unauthorized until the A2 evidence report and a separate Owner approval.
