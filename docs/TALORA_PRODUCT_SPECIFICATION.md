# TALORA Product Specification — Professional Writing & Production Studio

**Document status:** Draft for Owner review  
**Specification stage:** A1 — product scope and requirements  
**Implementation authorization:** Not granted  
**Target product:** TALORA Android application  
**Repository:** `akefadaka-git/talora`  
**Decision baseline:** Owner decisions recorded 2026-11-19

## 1. Product vision

TALORA is an Ethiopia-first, Android-first professional writing and production studio for screenwriters, playwrights, directors, and production teams. It combines a reliable offline writing environment, professional script formatting, story and character development, production preparation, directing tools, and governed IROKA intelligence.

The product must support Amharic and Ethiopic-script writing as a first-class workflow, not as a later localization add-on. The architecture should support film, television, stage, and other script-based production workflows without forcing all formats into a single screenplay template.

## 2. Owner decisions — binding for this specification

| ID | Decision | Specification |
|---|---|---|
| DEC-001 | Font strategy: **Both** | Bundle Noto Sans Ethiopic and Noto Serif Ethiopic in the Android application. Also define compatible Latin font families and a clear fallback strategy. |
| DEC-002 | Subscription structure: **Four levels** | Specify four entitlement levels: Free, Creator, Professional, and Studio. Feature allocation is proposed below; exact commercial terms remain TBD. |
| DEC-003 | Authorization: **Specification first** | Complete and review the product specification and technical reconciliation before implementation. No application-code changes, merge, or deployment are authorized by this decision. |

## 3. Goals and non-goals

### 3.1 Goals

- Deliver professional, semantically structured screenplay and stage-play authoring.
- Make Ethiopic/Amharic typography readable, consistent, searchable, editable, and exportable offline.
- Provide connected character, scene, story, revision, and project-management workspaces.
- Support director annotations and production planning without silently altering the writer's canonical script.
- Unlock capabilities by verified account entitlements while preserving user ownership and access to existing projects.
- Integrate IROKA through explicit, testable capability boundaries and user-controlled AI actions.
- Preserve portable, editable projects in the native `.talora` format.

### 3.2 Non-goals for the first implementation baseline

- Replacing a full enterprise production-management suite in one release.
- Building a proprietary font family when licensed fonts satisfy coverage and quality requirements.
- Making network access mandatory for basic writing, opening projects, or editing local documents.
- Treating a subscription or referral status as part of a project archive.
- Allowing AI to overwrite canonical screenplay content or make irreversible project changes without user approval.

## 4. Users and roles

- **Writer / playwright:** creates and edits scripts, scenes, characters, revisions, and exports.
- **Director:** reads an approved script and adds separately attributed interpretation, blocking, shot, sound, and rehearsal notes.
- **Producer / production manager:** manages breakdowns, cast/crew, locations, props, schedules, and production documents.
- **Studio administrator:** manages organization membership, roles, seats, shared projects, and subscription entitlements.
- **Collaborator / reviewer:** receives scoped read, comment, or edit access.
- **Owner of the project:** controls sharing, approvals, canonical versions, and export/production release actions.

Role permissions must be project- and organization-scoped. A subscription tier does not automatically grant project access or override role permissions.

## 5. Functional requirements

### 5.1 Ethiopic typography and language

- **FR-001:** Bundle Noto Sans Ethiopic and Noto Serif Ethiopic in the APK so the core Amharic/Ethiopic writing experience works without downloading fonts.
- **FR-002:** Use Noto Sans Ethiopic as the default interface and editing font; offer Noto Serif Ethiopic for reading, print-oriented views, and user-selected script styles.
- **FR-003:** Include compatible Latin sans-serif and serif families and define fallback behavior for mixed Ethiopic, Latin, numerals, punctuation, and unsupported glyphs.
- **FR-004:** Preserve Unicode code points and original source text exactly. Rendering, search normalization, spell-checking, and text transformations must not silently normalize or rewrite canonical content.
- **FR-005:** Verify Ethiopic syllabic glyph coverage, punctuation, numerals, combining marks where applicable, mixed-script line wrapping, cursor movement, selection, copy/paste, search, accessibility, and PDF output.
- **FR-006:** Provide font size, line spacing, paragraph spacing, page view, and theme controls without changing the semantic script content.
- **FR-007:** Include font licensing notices and retain the required license texts in the repository and app distribution.
- **FR-008:** Make the core Ethiopic font capability available in every tier, including Free. Font availability must not be subscription-gated.

### 5.2 Structured script editor and professional formatting

- **FR-010:** Represent script content as typed semantic elements rather than relying solely on visual indentation in a plain text field.
- **FR-011:** Support at minimum: title page, scene heading, action, character cue, dialogue, parenthetical, transition, shot, voice-over/off-screen dialogue, dual dialogue, act/sequence heading, stage direction, and revision marker.
- **FR-012:** Provide configurable screenplay and stage-play templates with professional default margins, alignment, spacing, capitalization, pagination, and page-break rules.
- **FR-013:** Support keyboard-first element transitions, predictable cursor behavior, undo/redo, selection, and accessible editing controls.
- **FR-014:** Support scene continuation, dialogue continuation, page numbering, title pages, scene numbering, and production-page conventions where selected by the project template.
- **FR-015:** Allow users to customize styles while preserving semantic element types and a reset-to-template option.
- **FR-016:** Keep screen layout, print layout, and exported layout consistent within documented rendering tolerances.
- **FR-017:** Do not destructively reformat imported scripts. Provide preview, mapping report, and user-confirmed conversion where source formats cannot map one-to-one.

### 5.3 Character management and character map

- **FR-020:** Provide character records with canonical name, aliases, biography, description, age/range where supplied, motivations, goals, conflicts, arc, and user-managed reference assets.
- **FR-021:** Generate a character relationship map from explicitly recorded relationships, with editable relationship labels and a list-based accessible alternative.
- **FR-022:** Show character appearances by scene, dialogue presence, and continuity references.
- **FR-023:** Support cast/actor assignment as production metadata, separate from fictional character identity.
- **FR-024:** Provide character continuity checks and surface possible inconsistencies as review suggestions, never silent edits.

### 5.4 Scene and story management

- **FR-030:** Provide a scene navigator with scene cards, outline/list views, search, filtering, and drag-to-reorder where safe.
- **FR-031:** Store scene number, heading, act/sequence, location, time of day, estimated duration, synopsis, status, characters, and linked notes.
- **FR-032:** Support beat sheets, story arcs, sequences, acts, plot threads, and scene-to-beat relationships.
- **FR-033:** Show scene-level revision history and support safe comparison and restoration.
- **FR-034:** Preserve stable scene identities when scenes are reordered or renumbered.
- **FR-035:** Support film and stage-play project types with extensible templates rather than hard-coding all scene rules to film.

### 5.5 Project, script, and revision management

- **FR-040:** Support multiple projects, scripts/episodes/acts within a project, project metadata, linked assets, and project-level search.
- **FR-041:** Preserve editable source, metadata, revision history, and linked assets in the native `.talora` archive.
- **FR-042:** Maintain immutable revision snapshots with author, timestamp, change note, and comparison/restore workflows.
- **FR-043:** Support autosave, explicit save status, crash recovery, and clear conflict handling.
- **FR-044:** Provide import/export compatibility with documented formats (including validated Fountain, Final Draft FDX, DOCX, RTF, TXT, and PDF workflows); publish a truthful compatibility matrix and test each format with fixtures.
- **FR-045:** Keep user-owned project data readable and exportable even if an entitlement expires or an account is unavailable.

### 5.6 Director's workspace

- **FR-050:** Provide a director workspace with scene interpretation, intention, tone, emotional beats, actor objectives, performance notes, and rehearsal observations.
- **FR-051:** Support blocking, movement, staging, camera/shot notes, lighting, sound/music, props, set, and continuity annotations as structured, searchable records.
- **FR-052:** Maintain separate layers for (1) canonical screenplay, (2) writer revisions, (3) director notes, (4) production annotations, and (5) approved production script.
- **FR-053:** Attribute each annotation to its author and timestamp; preserve edit history and support review/approval status.
- **FR-054:** Never silently merge director or production notes into the writer's canonical script.
- **FR-055:** Allow export of selected director/production notes as separate reports or as explicitly enabled annotations.

### 5.7 Production management

- **FR-060:** Provide script breakdown tagging for cast, extras, locations, props, wardrobe, makeup, set, vehicles, equipment, special effects, sound, and other configurable departments.
- **FR-061:** Generate scene breakdown sheets and production element reports.
- **FR-062:** Support cast/crew directory, role assignment, contact and availability metadata, subject to privacy controls.
- **FR-063:** Support location records, location constraints, schedule grouping, and scene-location relationships.
- **FR-064:** Support shooting/rehearsal schedules, production calendar, call sheets, daily production reports, and change notices.
- **FR-065:** Support budget/resource planning as structured estimates and actuals; financial functionality must be clearly distinguished from accounting software.
- **FR-066:** Provide approval and version control for released production documents.

### 5.8 IROKA intelligence

- **FR-070:** Integrate IROKA only through a versioned, documented capability contract.
- **FR-071:** Offer opt-in assistance for brainstorming, outline analysis, character/scene continuity, formatting suggestions, translation/localization assistance, and production preparation as capabilities become verified.
- **FR-072:** Display AI-generated suggestions separately from canonical content and require user acceptance before insertion or replacement.
- **FR-073:** Provide source/context visibility, error handling, user controls, and clear indication when IROKA is unavailable.
- **FR-074:** Enforce tier limits through entitlements, but never represent unimplemented or unverified IROKA capabilities as available.

### 5.9 Collaboration and organizations

- **FR-080:** Support project invitations and role-based access for writers, directors, producers, reviewers, and organization administrators.
- **FR-081:** Support comments, review requests, version comparison, and explicit approval workflows.
- **FR-082:** Detect concurrent edits and prevent silent last-write-wins data loss.
- **FR-083:** Support organization workspaces, seat assignment, project ownership, and auditable membership changes.
- **FR-084:** Offline edits must sync safely when connectivity returns; unresolved conflicts must be surfaced for human resolution.

### 5.10 Subscription and entitlement management

- **FR-090:** Implement four subscription levels: **Free**, **Creator**, **Professional**, and **Studio**.
- **FR-091:** Represent feature access through centralized capability keys and entitlement policies, not scattered UI-only tier checks.
- **FR-092:** Resolve entitlements from a verified account/subscription authority; the client must not be able to grant itself paid capabilities by editing local preferences or project files.
- **FR-093:** Support activation, renewal, expiry, grace period, restore/refresh, upgrade, downgrade, cancellation, and organization/team licensing.
- **FR-094:** Cache a signed, time-bounded entitlement snapshot for offline use, with defined expiry and refresh behavior.
- **FR-095:** On expiry or loss of connectivity, preserve access to user-owned projects, local editing, reading, and baseline export of owned data. Never hold user documents hostage.
- **FR-096:** Show locked features and their required tier clearly, with a non-blocking upgrade path.
- **FR-097:** Keep entitlement and referral state outside the `.talora` archive. Importing a project must never confer a subscription or premium entitlement.
- **FR-098:** Keep font availability and the ability to open/read/export user-owned project data outside paid gates.
- **FR-099:** Record entitlement changes and organization seat assignments in an auditable event history.
- **FR-100:** Define pricing, billing provider, taxes, refund policy, and payment availability only after commercial and regional feasibility review; none are approved by this specification.

## 6. Proposed four-level feature allocation

This is the proposed product packaging baseline, not a pricing approval. Capability allocation must be finalized during A1 review and validated against support and infrastructure costs.

| Capability | Free | Creator | Professional | Studio |
|---|---:|---:|---:|---:|
| Bundled Noto Sans + Noto Serif Ethiopic | Included | Included | Included | Included |
| Basic writing, local projects, autosave | Included | Included | Included | Included |
| Basic screenplay/play script templates | Included | Included | Included | Included |
| Basic character and scene records | Limited | Full | Full | Full |
| Advanced formatting and style controls | Basic | Full | Full | Full |
| Character relationship map and story boards | Limited | Full | Full | Full |
| Revision compare and advanced export | Limited | Full | Full | Full |
| Director's workspace and annotation layers | — | — | Full | Full |
| Script breakdown and production reports | — | — | Limited | Full |
| Schedules, call sheets, production workspace | — | — | Limited | Full |
| Collaboration and organization workspaces | — | — | Limited | Full |
| IROKA assistance | Limited quota, if available | Standard quota | Advanced quota | Studio/team quota |
| Organization administration and seats | — | — | — | Full |

**Packaging rules:**
- The Free tier remains useful for real writing, including Amharic writing, rather than functioning only as a demo.
- All tiers can open, read, and retain their own project files.
- Tier quotas, limits, offline allowances, and exact capability keys are TBD.
- A capability may be shown as “coming soon” only if clearly distinguished from a functioning locked feature.
- The previous concept of a one-time ad-free purchase is not silently discarded; its compatibility with a four-level subscription model is an open commercial decision (see TBD-004).

## 7. Non-functional requirements

- **NFR-001 Offline-first:** core authoring, local project access, autosave, and bundled Ethiopic fonts work without network access.
- **NFR-002 Unicode integrity:** preserve source text and support Ethiopic/Latin mixed-script rendering, editing, search, import, and export.
- **NFR-003 Reliability:** protect against process termination, storage-provider interruption, and failed imports/exports through transactional writes and recovery.
- **NFR-004 Performance:** editing and navigation remain responsive on supported low- and mid-range Android devices; exact budgets are to be established by profiling.
- **NFR-005 Accessibility:** support scalable text, screen readers, semantic controls, contrast, keyboard navigation where available, and non-graph alternatives for character maps.
- **NFR-006 Security:** protect account/session data, local sensitive metadata, shared projects, and entitlement snapshots; minimize collection of cast/crew personal information.
- **NFR-007 Privacy:** provide clear consent and retention rules for cloud sync, collaboration, AI processing, and production contacts.
- **NFR-008 Portability:** maintain documented, versioned import/export contracts and preserve canonical source text.
- **NFR-009 Auditability:** retain author, timestamp, version, and approval provenance for shared, director, production, and entitlement changes.
- **NFR-010 Maintainability:** use modular domain boundaries for editor, typography, project data, production, collaboration, IROKA, and entitlements.

## 8. External interface requirements

- **IF-001 Android system interfaces:** Storage Access Framework for opening/saving/importing/exporting user files.
- **IF-002 Font assets:** licensed, bundled font resources with license notices and reproducible asset provenance.
- **IF-003 IROKA:** versioned API/capability contract; unavailable capabilities fail gracefully.
- **IF-004 Account and entitlement service:** authenticated subscription state, signed offline entitlement snapshots, refresh/revocation semantics, and auditable changes.
- **IF-005 Export interfaces:** documented format adapters and deterministic rendering fixtures.
- **IF-006 Collaboration/sync:** authenticated, project-scoped synchronization with explicit conflict handling.

## 9. Data integrity and governance rules

1. The canonical script is distinct from its rendered pages, exports, director annotations, and production reports.
2. Semantic element IDs and scene IDs remain stable across reorder and formatting operations.
3. Revision snapshots are immutable; restore creates a new revision rather than rewriting history.
4. AI suggestions, director notes, and production notes cannot silently alter canonical script content.
5. Entitlements govern feature access, not ownership of user-created content.
6. The native archive carries project data only; it is not an entitlement token.
7. Shared-project permissions and subscription tier checks are independent authorization dimensions.
8. All irreversible release, approval, or canonical-version changes require an explicit authorized user action.

## 10. Technical design proposal (RFC — preliminary)

### 10.1 As-is baseline

The inspected development branch contains an Android/Kotlin foundation with offline local persistence, autosave, revision snapshots, asset handling, and a native `.talora` ZIP/JSON archive specification. The archive specification explicitly separates entitlement enforcement from project-file contents. The exact-head automated test status and full feature behavior still require independent verification. The current repository's main branch is minimal; the Android implementation work is on a separate development branch/PR and is not assumed merged.

### 10.2 Target architecture

- **Typography module:** bundled Ethiopic and Latin font resources, typography tokens, fallback configuration, and rendering/export verification.
- **Structured document engine:** typed script nodes, stable IDs, document operations, template/style registry, and pagination/rendering adapters.
- **Project domain:** projects, scripts, scenes, characters, beats, assets, revisions, and archive adapters.
- **Production domain:** breakdown elements, cast/crew, locations, schedules, call sheets, reports, and release approvals.
- **Director annotation domain:** independently versioned and attributed annotation layers.
- **Entitlement domain:** capability registry, tier policy, verified account state, signed offline cache, and grace/expiry handling.
- **IROKA adapter:** versioned contracts, capability discovery, consent boundaries, and suggestion review/acceptance.
- **Collaboration domain:** project roles, sync protocol, conflict resolution, and audit events.

### 10.3 Design choices and rationale

- **Bundled fonts over download-only fonts:** predictable offline behavior and consistent Ethiopic rendering.
- **Semantic document model over visual-only formatting:** reliable editing, pagination, import/export, accessibility, and future production tooling.
- **Separate annotation layers over direct script mutation:** preserves writer authority and traceability.
- **Central capability-based entitlements over scattered tier conditionals:** makes tier changes auditable, testable, and maintainable.
- **Project data independent of entitlement state:** prevents loss of access to user-owned creative work.
- **Incremental domain delivery:** reduces risk and permits independent acceptance gates.

### 10.4 Migration and compatibility

- Preserve the existing `.talora` v1 source content exactly.
- Add structured nodes and metadata through a versioned schema migration; retain a reversible/raw-source representation during migration.
- Do not reinterpret or rewrite existing plain-text projects without user-visible preview and consent.
- Keep old archive readers safe: unknown schema versions fail closed, and migrations must be explicit.
- Keep entitlement state outside archives and local project records.

### 10.5 Test strategy

- Unit tests for Ethiopic glyph coverage, font fallback, semantic element transitions, stable IDs, tier policies, and expiry/grace calculations.
- Golden rendering tests for Amharic, mixed Ethiopic/Latin, punctuation, page breaks, and PDF font embedding.
- Import/export fixture tests for every claimed format, including round-trip fidelity and malformed-file rejection.
- Database migration, archive round-trip, recovery, and revision-integrity tests.
- Entitlement security tests for offline expiry, tampering, revoked subscriptions, account switching, and project-file import.
- Role/permission tests for writer, director, producer, reviewer, and organization administrator.
- End-to-end tests for offline writing, director annotation, production breakdown, and subscription changes.
- Accessibility tests for editor navigation and non-visual access to relationship maps.

## 11. Risks and mitigations

| Risk | Mitigation |
|---|---|
| Ethiopic shaping or PDF output differs across devices | Bundle fonts; establish golden fixtures and test actual rendered output and embedded fonts. |
| Existing plain-text projects do not map cleanly to semantic nodes | Preserve raw source; provide conversion preview and rollback. |
| Scope becomes too large for one release | Deliver in gated milestones; keep production and collaboration domains modular. |
| Subscription checks block legitimate offline users | Signed time-bounded cache, grace policy, and permanent access to owned data. |
| Director notes overwrite writer intent | Separate annotation layers and approval workflow. |
| Import/export claims exceed real compatibility | Publish tested format matrix and regression fixtures. |
| IROKA integration is assumed complete before verification | Capability contract, explicit status, and acceptance tests per capability. |
| Commercial model conflicts with earlier one-time-purchase idea | Resolve monetization policy before billing implementation. |

## 12. Acceptance criteria for specification closure

A1 specification can be marked ready for Owner closure only when:
- All product domains and user roles are covered.
- Four tiers and the always-available font/data-ownership rules are represented consistently.
- Each requirement has a stable ID and can be mapped to tests.
- Existing implementation is reconciled against the target requirements without claiming unverified features.
- Subscription packaging, monetization model, and unresolved commercial choices are explicitly recorded.
- A2 read-only reconciliation scope and evidence checklist are approved.
- No implementation, merge, or deployment is inferred from specification approval.

## 13. TBD and decisions required

- **TBD-001:** Confirm exact feature quotas and capability keys for Free, Creator, Professional, and Studio.
- **TBD-002:** Confirm whether the four tier names are final or whether only the number of tiers is approved.
- **TBD-003:** Define supported Android versions/devices and measurable performance budgets.
- **TBD-004:** Reconcile the earlier free ad-supported + one-time ad-free purchase concept with the newly selected four-level subscription structure.
- **TBD-005:** Decide whether subscription billing is Google Play, an alternative regional payment provider, or a provider-agnostic first release; assess availability and legal/commercial constraints before selection.
- **TBD-006:** Define entitlement grace duration, offline cache lifetime, and revocation behavior.
- **TBD-007:** Confirm exact script formats and compatibility levels (read, edit, round-trip, export) for screenplay and stage-play workflows.
- **TBD-008:** Confirm whether Studio collaboration is cloud-hosted, self-hosted, or hybrid.
- **TBD-009:** Define IROKA capability availability, quotas, data handling, and supported offline behavior.
- **TBD-010:** Confirm production management depth for the first Studio release.
- **TBD-011:** Complete font license review and verify exact font file versions/weights before bundling.
- **TBD-012:** Define data retention, consent, and privacy controls for cast/crew records and AI processing.

## 14. Approval record

| Gate | Status |
|---|---|
| Owner font decision — both Noto Sans Ethiopic and Noto Serif Ethiopic | **APPROVED FOR SPECIFICATION** |
| Owner subscription decision — four levels | **APPROVED FOR SPECIFICATION** |
| Proposed labels Free / Creator / Professional / Studio | **PROPOSED; confirm names** |
| Owner authorization — specification first | **APPROVED** |
| A1 specification closure | **PENDING REVIEW** |
| A2 read-only technical reconciliation | **NEXT; not yet started** |
| A3 implementation | **NOT AUTHORIZED** |
| A4 verification | **NOT AUTHORIZED** |
| A5 deployment | **NOT AUTHORIZED** |
