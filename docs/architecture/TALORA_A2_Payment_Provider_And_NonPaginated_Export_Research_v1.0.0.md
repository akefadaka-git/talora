# TALORA A2 Research Memo — Non-Paginated Export Policy and Ethiopia Payment Providers

- **Document ID:** TALORA-A2-RM-003
- **Version:** 1.0.0
- **Research date:** 19 November 2026
- **Status:** A2 research evidence; ODR-11 Owner-approved 19 November 2026; payment provider not selected
- **Branch:** `docs/a1-import-export-referral-entitlements`
- **Implementation:** NOT AUTHORIZED

## 1. Executive findings

Two A2 gaps were investigated:

1. TXT and Fountain have no intrinsic, stable page count. Fountain is a plain-text screenplay markup language; a five-page limit therefore needs a TALORA-defined counting profile rather than a claim that the text file itself contains pages.
2. Ethiopia has multiple payment-gateway operators shown by the National Bank of Ethiopia (NBE) as licensed and commercialized. The public evidence supports a shortlist for merchant due diligence, but it does not establish TALORA merchant acceptance, pricing, settlement terms, recurring-payment support, foreign-currency collection, or production readiness.

## 2. ODR-11 — Approved Free limit for TXT and Fountain

### Problem

The Owner-approved Free rule is five pages per export. TXT and Fountain are plain-text outputs and do not carry intrinsic pagination. Fountain's own site describes it as a plain-text markup language; its FAQ explains it as rules for writing a screenplay in plain text.

A byte or character cap would be arbitrary, would vary with language and script, and could disadvantage Amharic/Ethiopic text. Counting newline characters would also be easy to manipulate and would not reflect screenplay/stage-play layout.

### Owner-approved rule — 19 November 2026

**Use a deterministic TALORA canonical pagination profile as the entitlement measure for every export format.**

1. Before export, paginate the selected document revision using a named, versioned TALORA default print profile for its document type.
2. The profile must fix page size, margins, font metrics, line spacing, screenplay/stage-play layout rules, and renderer version. It must not use user-adjustable settings to evade the cap.
3. Build a stable mapping from semantic document ranges (scene/block IDs and offsets) to canonical pages.
4. A Free TXT or Fountain export may include only the complete semantic content that fits within canonical pages 1–5. It must not invent page breaks or insert artificial pagination into the output.
5. If the full document exceeds five canonical pages, clearly label the export as a partial five-page-equivalent export and identify the included range. Do not silently truncate mid-block; if the next complete semantic block does not fit, stop before that block and explain the boundary.
6. Premium and separately granted full-script entitlements may export the complete document, subject to their own format and watermark rules.
7. Bind the decision to immutable revision ID, document type, pagination-profile ID, renderer version and settings digest. Recalculate if any of those inputs changes.
8. Failed/cancelled jobs do not consume allowance. Completed jobs are idempotently accounted for.
9. Amharic/Ethiopic and mixed-script golden fixtures must validate pagination fairness and determinism before release.

### Advantages

- One entitlement rule across PDF, DOCX, TXT and Fountain.
- No arbitrary character limit or language-dependent disadvantage.
- No false claim that a plain-text file has intrinsic pages.
- Clear and reproducible user-visible explanation.
- Server-authoritative enforcement and auditable export accounting.

### Trade-offs and controls

- Canonical pagination adds a renderer dependency to text export.
- A plain-text output can be shorter or longer on a recipient's device; the entitlement is based on TALORA's declared canonical profile, not the recipient's editor.
- Partial export boundaries must preserve semantic units and be clearly disclosed.
- Stage-play layout requires its own versioned profile; do not reuse screenplay assumptions without testing.
- Golden tests must cover Amharic/Ethiopic, Latin, mixed script, long character names, dialogue, stage directions and unusual Unicode sequences.

**Disposition:** APPROVED by the Owner on 19 November 2026. Concrete pagination-profile geometry, fonts/metrics and renderer version remain future engineering specifications and require deterministic verification before release.

## 3. Ethiopia payment-provider evidence

### 3.1 Regulatory registry evidence

The NBE's public “Payment Instrument Issuers/System Operators” register lists the following payment-gateway operators with current status “Commercialized”:

- Arif Pay Financial Technology S.C — POS & payment gateway operator; license NPS/PSO/003/2021.
- Chapa Financial Technology S.C — POS & payment gateway operator; license NPS/PSO/005/2022.
- StarPay Financial Service S.C — payment gateway operator; license NPS-PSO/013/2025; current status “Commercialized”.
- Santim Pay Financial Technology S.C — POS & payment gateway operator; license NPS/PSO/006/2022.
- Addis Pay Financial Technology S.C — POS & payment gateway operator; license NPS/PSO/007/2022.
- Yagout Pay Financial Technology S.C — POS & payment gateway operator; license NPS/PSO/008/2023.
- Fenanpay Solutions S.C — payment gateway operator; license NPS-PSO/009/2024.

The same register lists Ethiotelecom / Telebirr as a mobile-money payment-instrument issuer with current status “Commercialized” (license NPS PII/01/2021). It lists M-PESA Ethiopia as “Commercialized” as well.

The NBE's March 2026 Financial Stability Report identifies the National Payment System Proclamation, ONPS/02/2020, ONPS/09/2023 and its ONPS/10/2025 amendment among the current active payment-system regulatory framework. The May 2025 ONPS/10/2025 amendment is therefore a required legal-review reference for any provider due diligence; this memo is not a legal opinion.

### 3.2 Provider technical evidence

| Candidate | Public evidence reviewed | What it supports | What remains unverified |
|---|---|---|---|
| Chapa | Developer quick-start, accept-payment and webhook documentation | Merchant onboarding flow; ETB examples; checkout; payment verification; webhook signatures; idempotency guidance; documentation says live mode follows compliance approval | TALORA merchant eligibility; fees; settlement; refunds; subscription/recurring capability; international payment acceptance; contractual SLAs |
| ArifPay | Developer portal and API overview | Sandbox and production docs; checkout/C2B; direct wallet/bank flows; transaction verification; lifecycle/webhook documentation; SDKs | TALORA merchant eligibility; fees; settlement; refunds; recurring billing semantics; international cards/currency; contractual SLAs |
| Telebirr direct | Ethiotelecom developer portal C2B integration guide and NBE issuer register | Official C2B merchant integration path and payment flow; issuer listed commercialized | Merchant qualification, fees, settlement, refund/reversal API, recurring payment support, international collection |
| SantimPay | NBE register | Licensed gateway operator listed commercialized | Current merchant API, onboarding, pricing, payment methods and recurring support require direct confirmation |

### 3.3 Recommended due-diligence order

1. **Primary technical/merchant shortlist:** Chapa and ArifPay. Both appear in the NBE register as commercialized payment-gateway operators and publish developer documentation.
2. **Direct wallet alternative:** Telebirr C2B, subject to merchant onboarding and operational terms.
3. **Secondary comparison:** SantimPay and other commercialized NBE-listed operators if they provide a current API pack and competitive written terms.

This is a shortlist for evidence gathering, not a provider selection or endorsement.

### 3.4 Mandatory vendor questions

Obtain written answers for each candidate:

- Can TALORA's legal merchant entity onboard, and what incorporation, tax, bank-account and KYC documents are required?
- Can an Ethiopian merchant accept payments from Ethiopian customers and international customers? Which methods and currencies are actually enabled?
- What are the setup, transaction, refund, chargeback, settlement, withdrawal and FX fees?
- What are settlement currencies, timelines, minimums and destination-bank requirements?
- Are full and partial refunds supported by API? How are reversals, disputes and chargebacks represented?
- Are one-off fixed-term purchases for 1, 3, 6 and 12 months supported? Does the provider support tokenized recurring billing, or must TALORA use user-initiated renewal transactions?
- What are webhook authentication, retry, ordering and replay guarantees? Is transaction verification available by server API?
- What sandbox access is available without payment, deposit or paid activation?
- What production support, incident response and service availability commitments apply?
- What data-protection, PCI scope, retention and subcontractor terms apply?

### 3.5 Security and product recommendation

- Retain the provider-neutral TALORA transaction and entitlement ledger.
- Treat provider webhooks as signals; verify signature/origin and re-query the provider transaction before granting entitlement.
- Enforce idempotency for provider events, payment references, refunds and entitlement changes.
- Never grant entitlements from Android return URLs or client callbacks alone.
- Do not implement automatic recurring charges until provider capability, customer consent, cancellation, renewal notice and applicable legal requirements are validated.
- Fixed-term 1/3/6/12-month watermark-free entitlements can be modeled as one-off purchases with explicit start/end dates if a provider supports only one-time checkout.
- No production keys, live payments, provider account creation or paid sandbox activation were used or authorized.

## 4. Sources reviewed

1. National Bank of Ethiopia, Payment Instrument Issuers/System Operators register: https://nbe.gov.et/payment-instrument-issuers-system-operators/
2. National Bank of Ethiopia, Financial Stability Report, March 2026: https://nbe.gov.et/wp-content/uploads/2026/03/FInancial-Stability-Report_MARCH_2026.pdf
3. National Bank of Ethiopia, ONPS/10/2025 amendment: https://nbe.gov.et/wp-content/uploads/2025/05/ONPS102025-Oversight-of-the-Natioanl-Payment-System-Licensing-and-Authorization-of-Payment-Instrument-Issuer-Amendment-Directive.pdf
4. Chapa developer quick start: https://developer.chapa.co/dashboard/quick-start
5. Chapa accept-payments and transaction verification: https://developer.chapa.co/integrations/accept-payments
6. Chapa webhooks and HMAC verification: https://developer.chapa.co/integrations/webhooks
7. ArifPay developer portal: https://developer.arifpay.net/docs/api/overview
8. Ethiotelecom Telebirr C2B integration guide: https://developer.ethiotelecom.et/docs/H5%20C2B%20Web%20Payment%20Integration%20Quick%20Guide/Introduction
9. Fountain format overview: https://fountain.io/
10. Fountain developer resources and MIT-licensed reference implementation: https://fountain.io/developers/
11. Fountain FAQ: https://fountain.io/faq/

## 5. Evidence limitations and gate status

- Public NBE registry status is evidence of listed licensing/commercialization, not a guarantee that TALORA qualifies for onboarding or that every payment method is enabled for the intended merchant.
- Provider documentation is not a negotiated merchant contract and does not establish fees, settlement or service levels.
- No vendor has confirmed TALORA onboarding or recurring billing in writing.
- No legal opinion has been obtained.
- Fountain's public developer page describes its reference code as MIT-licensed; this does not by itself settle every trademark, fixture, sample-script or third-party dependency issue. Review the exact repository LICENSE and provenance before reuse.
- Final Draft, Fade In and Celtx proprietary-format rights remain separate per-format questions; this memo does not clear them.

## 6. Evidence reconciliation update — 19 November 2026

### 6.1 ODR-11 approval record

The Owner explicitly approved the canonical-pagination policy on 19 November 2026. The A2 Technical Decision Register is the authoritative decision record. This memo's earlier “awaiting Owner decision” wording is superseded by that approval.

### 6.2 Current provider documentation recheck

A current recheck of public provider documentation confirms the following implementation-relevant details:

- **Chapa v2:** Test and live environments are documented separately. Private keys are backend-only. The current v2 webhook guide specifies HMAC-SHA256 over the exact raw request body using the webhook secret, constant-time signature comparison, durable/idempotent event processing, and server-side transaction verification before fulfillment. The verification endpoint returns transaction status and should be checked against expected amount, currency and merchant reference. This is stronger and more precise than relying on a redirect or a client callback.
- **Chapa documentation version caution:** older merchant-handbook examples reconstruct the body with JSON serialization. The current v2 security guidance says to verify the exact raw body. An implementation must pin one current API/documentation version and must not mix incompatible webhook recipes.
- **ArifPay:** the current developer portal documents a sandbox, C2B checkout/direct flows, transaction verification, lifecycle/webhook handling and test methods. Public documentation does not establish TALORA merchant onboarding, fees, settlement, refunds, contractual service levels or recurring-billing eligibility.
- **Telebirr:** the NBE register lists Ethiotelecom/Telebirr as commercialized; its developer portal publishes a C2B integration guide. That does not establish TALORA's merchant eligibility, pricing, settlement or refund/reversal arrangements.

No provider account was created, no sandbox key was obtained or used, no transaction was initiated, and no live payment or paid activation occurred.

### 6.3 Updated NBE register interpretation

The NBE public register currently lists Arif Pay, Chapa, Santim Pay, Addis Pay, Yagout Pay, Fenanpay and StarPay as commercialized payment-gateway operators. It separately lists Telebirr and M-PESA as commercialized mobile-money issuers. Several newer entries remain in pilot phase and are not treated as production-ready candidates.

The register is regulatory status evidence only. It is not evidence of TALORA merchant acceptance, active payment rails for this merchant, price, settlement, recurring billing, or service-level guarantees.

### 6.4 A2 disposition of ODR-14

**A2 architecture decision:** retain a provider-neutral transaction and entitlement ledger; keep Chapa and ArifPay as primary due-diligence candidates, Telebirr C2B as a direct-wallet alternative, and SantimPay/other NBE-commercialized operators as comparison candidates. No provider or price is selected.

**External gate remains mandatory before payment implementation:** obtain written merchant eligibility, KYC requirements, fees, settlement, refund/reversal, recurring/one-off terms, payment-method/currency availability, sandbox access, data-protection terms and support/SLA. Confirm the applicable NBE directives and legal requirements with qualified counsel. No automatic recurring charge is allowed until provider capability, explicit customer consent, cancellation and renewal-notice rules are verified.

### 6.5 Sources added in the current recheck

- NBE current Payment Instrument Issuers/System Operators register: https://nbe.gov.et/payment-instrument-issuers-system-operators/
- Chapa v2 webhook verification: https://docs.chapa.global/docs/v2/integrations/webhooks
- Chapa v2 payment verification: https://docs.chapa.global/docs/v2/integrations/verify-payment
- Chapa v2 security guide: https://docs.chapa.global/docs/v2/security/security-guide
- Chapa test/live mode: https://docs.chapa.global/docs/v2/integrations/test-live-mode
- ArifPay API documentation: https://developer.arifpay.net/docs/api/overview
- ArifPay sandbox: https://developer.arifpay.net/sandbox
- NBE ONPS/10/2025 amendment: https://nbe.gov.et/files/onps-10-2025-oversight-of-the-natioanl-payment-system-licensing-and-authorization-of-payment-instrument-issuer-amendment-directive/

**Current A2 status:** ODR-11 is Owner-approved. ODR-14 is reconciled at the architecture/shortlist level, but provider selection and merchant/legal due diligence remain external pre-implementation gates. No provider selected; no paid activation or live transaction performed. A3 implementation, merge and deployment remain unauthorized.
