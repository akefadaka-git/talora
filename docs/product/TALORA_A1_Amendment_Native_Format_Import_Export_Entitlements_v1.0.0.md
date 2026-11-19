# TALORA A1 Product Specification Amendment — Native Format, Import/Export and Entitlements

- **Document ID:** TALORA-A1-AMD-002
- **Version:** 1.0.0
- **Date:** 19 November 2026
- **Status:** Owner-approved product requirements; A1 amendment
- **Parent baseline:** TALORA A1 product definition, branch `docs/a1-product-definition`, baseline commit `599bcb4a71bd7ca7e1b5945c8034f29ecff4bc06`
- **Implementation status:** Not authorized by this amendment

## 1. Purpose and scope

This amendment adds a native TALORA project file, broad document interchange, tiered export entitlements, referral rewards, and watermark-removal purchases/subscriptions to the TALORA product definition. It supplements rather than replaces the original A1 specification. Existing product, privacy, security, localization, offline-first and governance requirements remain in force.

## 2. Owner-approved decisions

The following decisions are approved and binding for product planning:

1. Native file extension: `.talora`.
2. Free export limit: five pages per export.
3. Premium format access: all formats that TALORA officially supports for import and export.
4. Watermark-free one-time license: one selected project, including subsequent revisions of that project.
5. Watermark-free subscription terms: one, three, six and twelve months; longer terms may be evaluated later.
6. Referral qualification: verified account, activation and anti-fraud checks.
7. Ten verified referrals: one full-script export entitlement.

These decisions do not authorize implementation, repository merge, release, payment activation or deployment.

## 3. Native TALORA format

TALORA shall define a versioned native project package using the `.talora` extension. It must preserve editable project structure rather than flattening a project into a print document.

The format specification shall define:
- A manifest with format identifier, schema version, project identifier, creation/update metadata and compatibility requirements.
- Structured script content, including scenes, action, dialogue, character cues, transitions and supported screenplay/stage-play constructs.
- Document layout and formatting metadata.
- Character, location and project reference data.
- Revision identifiers and revision metadata, with explicit rules for what history is included in an exported package.
- References to embedded or linked media and production assets.
- Export profile metadata where appropriate.
- Integrity metadata and safe extraction/validation rules.

A ZIP-based container with a documented manifest and structured content is the preferred technical direction, subject to A2 design review. The format must support forward compatibility, explicit migrations, corruption detection, bounded extraction and safe handling of untrusted files. A `.talora` package must not execute embedded code.

TALORA must preserve the original project independently of any export operation. Exporting to another format must not destructively transform the source project.

## 4. Import compatibility

Premium users are entitled to import all formats that TALORA officially supports. The product objective is broad compatibility with screenplay, stage-play and general document-writing applications.

Candidate formats for A2 compatibility assessment include:
- TALORA `.talora`
- Fountain `.fountain`
- Final Draft `.fdx`
- PDF
- DOCX
- RTF
- TXT
- HTML and other documented structured interchange formats
- Other vendor formats only where a reliable, lawful specification or official interchange route is available.

The list above is a candidate support roadmap, not a claim that every format is already implemented or guaranteed.

Importers must preserve structure where source formats expose it. When source content is ambiguous or flattened (especially PDF), the importer must identify uncertain scene, dialogue, character and formatting reconstruction and provide a review step rather than silently claiming perfect conversion.

## 5. Export compatibility

Premium users are entitled to full-project export in every format officially supported by TALORA. Candidate export targets include:
- Native `.talora`
- Fountain
- Final Draft `.fdx`, subject to compatibility validation
- PDF
- DOCX
- RTF
- TXT
- HTML and additional validated formats.

Each format requires a documented capability level, compatibility tests, version policy and known-limitations record. Unsupported or lossy conversions must be disclosed before export. TALORA must not claim universal compatibility with every third-party writing application without verified format support.

## 6. Free and Premium export rules

### 6.1 Free entitlement

- A Free user may export up to five rendered pages per export operation.
- Page count is calculated after applying the selected export layout and pagination profile.
- The server-side entitlement service is authoritative; Android client-side checks alone are insufficient.
- A Free user may earn a one-time full-script export entitlement through the referral milestone in section 7.
- The referral-earned full-script export is a single-use export credit and is watermarked by default. It is not a watermark-free license.
- A five-page allowance and a one-time full-script reward must not be combined or multiplied through repeated requests to bypass entitlements.

### 6.2 Premium format entitlement

Premium unlocks all formats officially supported by TALORA for import and export, including full-project export without the Free five-page cap while Premium is active. Premium format access does not, by itself, remove the TALORA watermark from PDF exports; watermark removal is governed separately by section 8 unless a future approved plan explicitly bundles it.

### 6.3 Page-count and export integrity

A2 must define page-count semantics for each paginated output, including PDF and DOCX. Non-paginated formats such as TXT and Fountain require a separately specified policy; the entitlement system must not invent a page count for them. Export jobs must bind to a project revision and export settings snapshot to prevent inconsistent or manipulated counting.

## 7. Referral programme

Each eligible user may have a unique referral/promo code. A referral becomes qualified only after all of the following:
1. The invited person creates a distinct account and verifies it using the supported verification process.
2. The invited person completes the defined activation event.
3. The referral passes anti-fraud checks, including duplicate-account and self-referral controls.
4. The backend records the qualifying event idempotently.

**Owner-approved milestone:** Ten verified referrals grant one full-script export entitlement.

The entitlement is one-time, single-use, associated with the referring user's account, and redeemable for one complete script export. It is watermarked by default and does not grant Premium format access or watermark-free export.

The referral service must maintain auditable states such as pending, qualified, rejected, rewarded, redeemed and reversed. Reversals must be governed, explainable and auditable. The same invitee must not generate duplicate rewards.

Other referral rewards (Premium days, storage, AI credits, templates or collaboration benefits) are not approved in this amendment. They may be proposed separately after unit-economics and fraud analysis.

## 8. Watermark-free PDF entitlement

Watermark-free PDF export is a distinct entitlement from Premium format access and from referral-earned full-script export.

### 8.1 One-time project license

A verified one-time payment grants watermark-free PDF export for one selected project, including subsequent revisions of that same project. The license:
- Is bound to a stable project identity and owner/tenant scope.
- Applies to future eligible PDF exports of that project, including revised drafts.
- Does not transfer to unrelated projects.
- Does not expire solely because the user exports multiple revisions.
- Does not remove watermarks from unrelated projects or grant Premium import/export format access.

A2 must define project ownership transfer, account deletion, organization ownership, project duplication/forking and refund/reversal behavior.

### 8.2 Subscription terms

Watermark-free export subscriptions shall offer:
- One month
- Three months
- Six months
- Twelve months

Longer terms may be evaluated later and are not required for the initial release.

The subscription entitlement applies during its verified active period according to the final approved plan rules. Expiration must not invalidate files already exported or modify the user's source project. Subscription status must be verified server-side; client-side payment success is not sufficient.

### 8.3 Watermark behavior

Watermarks must be applied during export and must not be permanently inserted into the source project. The watermark must not obscure script content or impair readability. Watermark-free entitlement must be checked for the selected project and export operation.

## 9. Entitlement and payment architecture

The backend shall be the authoritative source for:
- Free page allowances.
- Premium format-access status.
- Referral qualification and rewards.
- Full-script export credits and redemption.
- Project-bound watermark-free licenses.
- Subscription periods, renewals, expiration, cancellation, refunds and reversals.

Every entitlement mutation must be authenticated, authorized, idempotent and auditable. Payment entitlements must only be granted after verified payment-provider confirmation. No automatic top-up or unapproved spending is permitted. Prices, taxes, currency conversion and payment-provider availability remain A2 decisions; no price is specified by this amendment.

## 10. Privacy, security and ownership

- Users retain ownership of their original scripts and creative work.
- TALORA receives no ownership rights merely because a user imports, exports, refers another user or purchases an entitlement.
- Unpublished creative content must not be used for advertising targeting or model training without explicit informed consent.
- Imported files are untrusted input. Parsers must be sandboxed and protected against malformed documents, archive bombs, embedded active content and resource exhaustion.
- Referral data must be minimized and used only for attribution, reward integrity, abuse prevention and required accounting.
- Export and payment audit records must not unnecessarily retain full script contents.
- Offline editing remains available; entitlement-sensitive exports must follow a secure offline policy with server reconciliation and bounded cached authorization.

## 11. Accessibility, localization and document fidelity

Import/export must preserve Amharic, Ethiopic Unicode, mixed-script text, punctuation and supported right-to-left content where applicable. It must use explicit locale and calendar metadata and must not destructively normalize user text. Fonts, glyph coverage, line wrapping, pagination and exported-document accessibility require validation. Unsupported glyphs or lossy conversions must be disclosed.

## 12. A1 acceptance criteria

The amended product definition is considered specified when:
- The native `.talora` package requirements are documented and versioned.
- Import/export compatibility is explicitly capability-based rather than an unqualified universal promise.
- Free five-page export and Premium supported-format access are distinct entitlements.
- Ten qualified referrals grant exactly one watermarked full-script export credit.
- Watermark-free one-time licenses are bound to one selected project and its revisions.
- One-, three-, six- and twelve-month watermark-free subscription terms are specified.
- Payment, referral and export entitlement decisions are server-authoritative and auditable.
- Privacy, file security, localization and offline constraints are carried into the A2 assessment.

## 13. Governance

This amendment records Owner-approved A1 product requirements. It does not close A2, approve detailed technical architecture, authorize A3 implementation, authorize third-party format licensing, approve prices or payment providers, or authorize merge/deployment. Those decisions remain separately gated.
