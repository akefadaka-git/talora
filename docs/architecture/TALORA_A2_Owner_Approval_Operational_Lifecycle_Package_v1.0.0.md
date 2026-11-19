# TALORA A2 Owner Approval — Operational Lifecycle Package

- **Document ID:** TALORA-A2-OWNER-APPROVAL-003
- **Version:** 1.0.0
- **Decision date:** 19 November 2026
- **Repository:** `akefadaka-git/talora`
- **Branch:** `docs/a1-import-export-referral-entitlements`
- **Authority:** Explicit Owner approval in the TALORA project conversation
- **Scope:** Approval of TDR §21.2 items 1–8, exactly as proposed
- **Implementation status:** NOT IMPLEMENTED
- **Stage effect:** A2 lifecycle policy decision recorded; this does not authorize A3 implementation, merge, A4 verification, or A5 deployment.

## 1. Owner decision

The Owner answered **“Yes”** on 19 November 2026 to the explicit question asking whether to approve the operational lifecycle package in TALORA A2 Technical Decision Register §21.2 as written.

**Decision: APPROVED AS WRITTEN.**

This approval covers all eight operational policy items in §21.2:

1. Calendar-month subscription terms, server-verified settlement start, end-exclusive expiry, no automatic renewal or top-up in v1, user-initiated renewal, and the stated early/late renewal rules.
2. The distinct payment state machine, server-side transaction verification, validation of signature/merchant reference/amount/currency/mode/provider status, and idempotent event handling.
3. The entitlement state machine; no entitlement on cancelled pending checkout; revocation after verified full refund or lost chargeback; restoration of only unused time after a won chargeback; and partial refunds disabled in v1 unless separately approved under a deterministic rule and supported by the provider.
4. The requirement for counsel-reviewed, plain-language Amharic/English refund and complaint terms before publication, while preserving applicable statutory customer rights and distinguishing provider processing charges from TALORA's customer refund obligations.
5. Dispute/chargeback handling: freeze new watermark-free exports within the disputed scope while preserving editing, source files and completed exports; restore remaining time if the merchant prevails; revoke future use if the chargeback is lost; preserve audit evidence and provide an appeal/contact path.
6. Export-job binding to the accepted user/tenant/project/revision/format/watermark/pagination/renderer/idempotency context; expiry alone does not invalidate an accepted job; verified refund, lost chargeback or fraud revocation may stop a cancellable job and block download; completed exports cannot be recalled; failed/cancelled jobs do not consume rewards and completed jobs consume once.
7. Organization-owned projects and licenses remain with the organization tenant when a member leaves; personal licenses do not transfer automatically; provide user-controlled export and explicit authenticated transfer/relicense where lawful before account deletion; forks/duplicates receive new project UUIDs; apply deletion/anonymization and minimum lawful transaction/tax/dispute retention.
8. Referral lifecycle and proposed activation: a verified distinct account plus creation and save of the invitee's first project; no upload, script sharing, IROKA use or payment requirement; server-side attribution/idempotency; proportionate fraud signals and manual review for shared-device/household ambiguity; reason codes, independent appeal reviewer, no invasive fingerprinting or creative-content inspection; good standing excludes accounts finally suspended/terminated for substantiated fraud or abuse, not mere inactivity.

## 2. Conditions and interpretation retained from §21.2

Approval adopts the proposal together with its expressly stated safeguards and dependencies. It does not waive them:

- The exact refund rights, consumer disclosures, digital-service commencement/consent, tax invoice and complaint terms must be confirmed by Ethiopian counsel before publication or commerce activation.
- Provider-specific dispute triggers, timing, evidence protocol, refund behavior and operational capabilities must be confirmed in writing with the selected provider and counsel before integration or live use.
- No partial-refund path is enabled in v1 unless a separate deterministic adjustment policy is explicitly approved and provider support is verified.
- Exact legal retention periods, deletion/anonymization boundaries and recovery rights require counsel review before production implementation.
- Fraud thresholds, review service levels, appeal deadlines, evidence retention and operational thresholds must be concretized in an implementation specification consistent with the approved principles before implementation. This approval does not invent numerical thresholds or authorize invasive tracking.
- Provider selection, merchant onboarding, payment credentials, prices, tax treatment, data-residency architecture, and IROKA production processing remain separate external/legal decisions and gates.

Where §21.2 says a detail requires counsel/provider confirmation, that qualification remains binding; Owner approval does not convert unknown external facts into verified facts.

## 3. Relationship to previously approved entitlements

This approval does not alter or replace the previously approved product entitlements:

- Free export: up to five canonical pages per export.
- Premium: full-project export in supported formats while active.
- Referral reward: one lifetime reward for the first ten qualified referrals, consisting of one full-script watermarked PDF export, with no expiry while the account remains in good standing.
- One-time watermark-free license: PDF only, bound to one project and its revisions.
- Watermark-free subscription terms: one, three, six and twelve calendar months, PDF only in the initial scope.
- Premium software purchase, project watermark-free license and time-limited watermark-free subscription remain separate entitlements.

## 4. Supersession and A2 status correction

This approval supersedes the statements in TDR §§21.2 and 21.6, and the A2 closure assessment §§10.2, 11.2, 11.3 and 11.7, that the detailed operational lifecycle package is awaiting Owner approval.

The corrected disposition is:

| Area | Status after this decision | Meaning |
|---|---|---|
| Detailed entitlement/payment/refund/chargeback/export-job lifecycle | **VERIFIED-CLOSED FOR A2 POLICY** | Owner approved TDR §21.2 items 1–8 as written; no implementation or external compliance is implied |
| Referral activation and governance principles | **VERIFIED-CLOSED FOR A2 POLICY** | Activation and anti-fraud/appeal principles approved; numerical thresholds and operational SLAs still require specification |
| Legal/provider-dependent refund, dispute and retention details | **PARTIAL / EXTERNAL LEGAL-PROVIDER GATE** | Counsel/provider confirmation remains mandatory |
| Implementation specification and tests | **NOT RUN / NOT IMPLEMENTED** | A3/A4 work remains separately gated |
| Overall A2 | **PARTIAL / NOT CLOSED** | Dependency/fixture licensing, payment merchant readiness, Ethiopia legal/privacy/data-residency evidence, Amharic corpus/reviewer/test evidence and IROKA production integration remain unresolved |

This is a policy decision record, not an A2 closure certificate. It does not mark any external gate as passed.

## 5. Stage-gate and authority boundary

- A1: VERIFIED-CLOSED; historical baseline remains unchanged.
- A2: PARTIAL / NOT CLOSED; lifecycle policy decision is now recorded.
- A3: No new TALORA implementation authorization is granted by this decision.
- A4: NOT STARTED by this decision.
- Merge/PR approval: NOT AUTHORIZED.
- A5 deployment: NOT AUTHORIZED.

The separately authorized bounded IROKA development/test slice retains its own scope and restrictions; this TALORA lifecycle approval does not expand it.

## 6. Next step

Refresh the TALORA A2 consolidated assessment to reflect this approval, then continue read-only reconciliation of the remaining A2 evidence gates. Do not begin implementation, merge, A4 verification or deployment without their respective explicit approvals.
