# Ethiopia-First Localization Specification

## Market direction

TALORA launches with Ethiopian creators as the primary audience while retaining international product and document compatibility.

## Language priorities

Proposed localization sequence:
1. Amharic.
2. English.
3. Afaan Oromo.
4. Tigrinya.
5. Maale.

The exact launch sequence and quality gates remain subject to Owner approval and qualified language review. Additional Ethiopian and international languages should be supported through the localization framework.

## Ethiopic and multilingual requirements

- Unicode-safe storage and editing.
- Correct Ethiopic glyph shaping and font fallback.
- Cursor movement, selection, deletion and copy/paste tested with Ethiopic text.
- Mixed Ethiopic and Latin text support.
- Search and indexing tested across supported scripts.
- Font licensing reviewed before bundling.
- No destructive transliteration or normalization.
- Language-aware spellchecking and writing assistance where validated.
- Export fonts and glyph coverage tested.

## Calendar, dates and numbers

- Gregorian calendar support.
- Ethiopian calendar support where useful in project and production workflows.
- Explicit calendar identity in stored date values.
- Locale-aware display without changing canonical timestamps.
- Clear handling of time zones and production locations.
- ETB as an initial local commercial display currency, subject to payment and legal review.

## Local operating conditions

- Useful offline writing.
- Resumable synchronization on intermittent networks.
- Efficient media transfer and optional quality reduction.
- Low-memory and battery-aware Android behavior.
- Clear storage management and backup controls.
- Accessible onboarding and help.

## Cultural and linguistic integrity

Translation and adaptation must preserve character voice, dramatic intent and cultural context. AI-generated cultural claims must be reviewable and should not be presented as authoritative without evidence. Local language reviewers should validate user-facing strings and sample documents.

## Acceptance tests

Test representative Amharic, Afaan Oromo, Tigrinya, Maale and mixed-language scripts through edit, save, search, sync, preview, pagination and export. Record font, shaping, line-break and conversion defects separately.
