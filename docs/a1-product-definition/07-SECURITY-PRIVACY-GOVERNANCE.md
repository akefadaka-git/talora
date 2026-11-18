# Security, Privacy and Product Governance

## Creative ownership

Users retain ownership of original creative works and project materials they provide. TALORA's terms must clearly distinguish user content, application intellectual property, third-party materials and AI-generated output. Legal terms require qualified review before release.

## Confidentiality

Unpublished scripts, treatments, story concepts and production documents are confidential by default. Access is restricted to the user and explicitly authorized collaborators and services.

## Data minimization

- Collect only data necessary for product operation.
- Keep advertising data separate from creative content.
- Do not sell private creative works.
- Do not use private creative content for model training without explicit informed opt-in.
- Explain when content leaves the device and which service receives it.

## Security controls

- TLS for network communication.
- Secure credential and token storage.
- Android Keystore integration where supported.
- Encryption of sensitive local data and protected backups.
- Server-side authorization and project-level access checks.
- Role-based permissions.
- Rate limiting and abuse protection.
- Secret scanning and dependency vulnerability monitoring.
- Auditable administrative actions.
- Backup, recovery and incident response procedures.

## AI action governance

- Treat imported files and script text as untrusted input.
- Enforce authorization before retrieving project context.
- Never allow prompt content to override system authorization.
- Require confirmation for material document changes and consequential actions.
- Preserve original and accepted versions.
- Prevent cross-project context leakage.
- Record provenance and operation history.

## User controls

- Export project and account data.
- Review collaborator access.
- Revoke access.
- Delete projects and accounts under a documented retention policy.
- Manage cloud sync and AI processing preferences.
- Recover purchases and supported account access.

## Public repository safeguards

- Never commit secrets, signing keys, credentials or private user scripts.
- Use synthetic test fixtures.
- Keep production configuration outside source control.
- Review dependency licenses.
- Add a clear proprietary rights notice.
- Public source visibility does not grant a reuse license.

## Compliance review

A2/A4 and pre-launch review must assess applicable Ethiopian privacy, consumer, advertising, tax, electronic transaction and intellectual-property requirements, as well as relevant international obligations for expansion. This document is a product control specification, not a legal opinion.
