# IROKA Integration Specification

## Role

IROKA is the integrated intelligence backbone for TALORA. It supplies creative reasoning and assistance across writing, story development, continuity, visualization and production workflows.

TALORA must remain useful for core authoring when intelligence services are offline or unavailable.

## Proposed capability domains

- **Story Architect:** concept, plot, structure, theme, outline and treatment assistance.
- **Script Doctor:** structure, pacing, dialogue, clarity and dramatic analysis.
- **Character Intelligence:** motivation, arc, relationships and voice consistency.
- **Continuity Guardian:** timeline, character, location, prop, costume and scene consistency.
- **Cinematic Intelligence:** shot suggestions, visual transitions and camera-language assistance.
- **Stage Director Intelligence:** blocking, movement, cues and stage transitions.
- **Production Intelligence:** breakdown, scheduling assistance and production requirement extraction.
- **Language Intelligence:** translation, localization and dialogue adaptation.
- **Research Assistant:** contextual and historical research with source transparency where available.

## Integration contract requirements

A2 must identify the actual IROKA service interfaces, authentication, supported deployment modes, model/runtime constraints, data handling, latency, resource requirements, licensing and versioning. The capabilities above are product requirements, not claims that corresponding services already exist.

Use versioned capability contracts with:
- Request and response schemas.
- Project and document context references.
- User authorization context.
- Explicit operation intent.
- Provenance for generated suggestions.
- Error, timeout and retry semantics.
- Usage and resource accounting.
- Audit metadata.

## Authorial control

- AI output is presented as a proposal.
- No silent mutation of canonical script content.
- User explicitly accepts, edits or rejects proposed changes.
- Accepted changes become traceable revision events.
- Bulk transformations require clear scope and confirmation.
- Original content remains recoverable.

## Context and privacy

Only the minimum context required for a task should be sent to an inference service. Private project content must not be used for model training without separate, informed, explicit authorization. Users and organizations need clear controls over local, remote and hybrid processing.

## Governance and safety

- Enforce project permissions before context retrieval or action.
- Separate suggestions from privileged operations.
- Apply policy checks to tool execution.
- Prevent cross-project context leakage.
- Keep auditable records of consequential AI actions.
- Provide user-visible explanations for material edits.
- Treat imported scripts and attachments as untrusted input.

## Resilience

Core writing and local document operations must not depend on network access. Intelligence requests should support cancellation, timeout, retry controls and clear unavailable states. Local inference is optional and depends on validated device capability.
