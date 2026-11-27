# TALORA A2 Commercial Decision Addendum — Advertising and Rewarded Premium Access

- **Document ID:** TALORA-A2-CDA-002
- **Version:** 1.0.0
- **Decision date:** 27 November 2026
- **Repository:** `akefadaka-git/talora`
- **Base A2 register:** `TALORA_A2_Technical_Decision_Register_Native_Format_Conversion_Entitlements_v1.0.0.md`
- **Assessment stage:** A2 — commercial policy reconciliation
- **Status:** OWNER-APPROVED DIRECTION; detailed operational parameters remain OPEN
- **Implementation:** NOT AUTHORIZED
- **Merge / deployment:** NOT AUTHORIZED

## 1. Owner decision

The Owner approved the following commercial direction on 27 November 2026:

1. Free users must receive meaningful, useful access to TALORA and be able to engage deeply with the product without paying.
2. Premium capabilities should be discoverable through clear, progressive value explanation and optional upgrade prompts.
3. If a user does not purchase premium access, TALORA may earn revenue by showing advertisements.
4. A user who voluntarily watches/completes an eligible rewarded advertisement receives **temporary access to selected capabilities from the lower premium tier only**.
5. Rewarded advertising does not grant the full premium tier, a permanent entitlement, ownership of a premium feature, or an unconditional watermark-free/export license.

This decision is a commercial-policy direction. It does not authorize an advertising SDK, tracking, account profiling, network permission, payment integration, subscription implementation, or any other A3 work.

## 2. Monetization model

TALORA's commercial model is composed of distinct mechanisms:

| Mechanism | Policy disposition | Boundary |
|---|---|---|
| Free experience | Owner-approved direction | Must remain meaningfully useful without payment; core offline writing and access to user-owned projects must not depend on ad availability |
| Non-rewarded advertising | Owner-approved revenue direction | May monetize non-purchasing users; must not interrupt typing, editing, autosave, project creation, or an active export |
| Rewarded advertising | Owner-approved direction | Opt-in exchange for temporary access to selected lower-premium-tier capabilities only |
| Paid tier access | Four-level model previously approved for specification | Feature packaging and commercial lifecycle remain governed by the existing A2 register and its approved addenda |
| Project/referral/export entitlements | Preserve prior Owner-approved decisions | Remain separate entitlement dimensions unless explicitly amended by a later Owner decision |

## 3. Rewarded-ad access contract

The following policy is binding at the direction level:

- Rewarded access is voluntary and requires an affirmative user action.
- A successfully completed and verified reward event may issue a time-limited entitlement grant.
- The grant may unlock only capabilities explicitly designated as eligible from the **lower premium tier**.
- The grant must not unlock Professional/Studio or the full premium capability set by implication.
- The grant must have a server-verifiable start, expiry, eligible capability list, grant identifier and auditable provenance before production use.
- Replaying, duplicating or retrying an ad callback must not create duplicate grants or extend access improperly.
- If an ad is unavailable, fails, is skipped, or cannot be verified, no reward is issued; ordinary Free-tier writing and project access remain available.
- The app must disclose the eligible capability, reward duration and expiry before the user opts in.
- Expiry removes only the temporary capability grant. It must not delete, lock, corrupt or reduce access to user-created content.
- A rewarded grant must not override a refund, chargeback, account restriction, project ownership boundary, or separately scoped project license.
- The user must not be forced to watch an advertisement to save, reopen, edit, or export content already permitted by the Free tier.

## 4. Non-disruptive advertising requirements

- No interstitial or rewarded-ad interruption while the user is typing, editing, saving, importing, exporting, or recovering a project.
- No ad may obscure unsaved work or block access to the editor.
- Ad failure, offline state, consent refusal, or ad inventory shortage must fail open to the ordinary Free experience.
- Ads and upgrade prompts must be clearly distinguishable from editorial content and must not use deceptive controls or coercive countdowns.
- Any personalized advertising, tracking, analytics sharing or third-party SDK data transfer requires separate privacy, consent, data-flow, legal and security review before implementation.
- No ad provider or SDK is selected by this decision.

## 5. Decisions intentionally left open

The Owner approved the direction, not the numerical or provider-specific operating parameters. These remain OPEN for A2 disposition or a later explicit Owner decision:

1. Exact duration of one rewarded-access grant.
2. Eligible lower-premium capability keys and exclusions.
3. Maximum rewarded grants per user per day/week and any cooldown.
4. Whether multiple completed rewards may stack; default recommendation is no stacking beyond a clearly disclosed cap.
5. Ad network/provider, mediation, inventory, geographic availability and revenue settlement.
6. Whether rewarded ads are available offline (they cannot be assumed available offline).
7. Age/consent controls, personalized versus contextual ads, tracking restrictions and privacy disclosures.
8. Frequency caps and precise placement rules for non-rewarded ads.
9. Abuse/fraud thresholds, callback verification and revocation handling.
10. Any customer-facing prices, subscription durations or paid-tier feature quotas not already explicitly approved.

No provider, price, duration, cap or eligible capability list may be invented from this decision.

## 6. Relationship to existing entitlements

This addendum does not cancel or silently rewrite:

- Free five-page export allowance.
- Premium full-project export capability while the relevant entitlement is active.
- Ten qualified referrals for one full-script watermarked PDF export.
- One-time watermark-free PDF license for one selected project and its revisions.
- Fixed-term watermark-free PDF entitlements for one, three, six or twelve months.
- The rule that watermark-free rights and feature-tier access are separate dimensions.

The four-level feature model, advertising access and project/export entitlements must be represented as separate, composable policy dimensions. A temporary rewarded grant does not count as a paid purchase, referral reward, project license or subscription term.

## 7. A2 status impact

| Area | Status after this decision |
|---|---|
| High-level free + advertising monetization direction | **VERIFIED-CLOSED FOR POLICY DIRECTION** |
| Rewarded-ad access limited to temporary lower-premium capabilities | **VERIFIED-CLOSED FOR POLICY DIRECTION** |
| Four-tier feature packaging and prior entitlement mapping | **PARTIAL — reconcile without removing prior rights** |
| Reward duration, capability list, frequency caps and stacking | **OPEN** |
| Provider/SDK, privacy, consent and data-flow review | **MISSING / EXTERNAL GATE** |
| Ad implementation and paid-service activation | **NOT AUTHORIZED** |
| Overall TALORA A2 | **PARTIAL / NOT CLOSED** |

## 8. Governance

This addendum supplements, and does not rewrite, the original A1 records or the existing A2 technical decision register. It records the Owner's commercial direction only. No source code, dependency, database, workflow, production configuration, main branch, or existing approval record is modified by this policy text.

- A1: **VERIFIED-CLOSED**.
- A2: **PARTIAL / IN PROGRESS — NOT CLOSED** pending remaining FDX, dependency/licensing, technical, Amharic evidence and external/legal reconciliation.
- A3: **NOT AUTHORIZED**.
- A4: **NOT STARTED / NOT AUTHORIZED**.
- Merge: **NOT AUTHORIZED**.
- A5: **NOT AUTHORIZED**.
