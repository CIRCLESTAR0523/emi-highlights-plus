# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Changed

- Renamed the current development artifact from EMI Container Highlights to EMI Highlights+.
  The Mod ID is now `emi_highlights_plus`, and the Java package/resource namespace now use
  `dev.circlestar.emihighlightsplus` and `emi_highlights_plus` respectively.
- Formalized the display name as EMI Highlights Plus (abbreviated as EH+). The earlier
  EMI Highlights+ wording remains part of the development history.
- The renamed artifact uses `0.2.0`; it is not a replacement for the old Mod ID's `0.1.0`
  history. Release Type and external distribution remain subject to the publication audit.

### Added

- Development implementation of required-material-first ordering in the Arcane Transmutation Tablet,
  preserving native ordering within groups, filtering, fuel slots and lock slots.
- The same required-material-first ordering for ProjectE's own Transmutation Table and Tablet.
- Development implementation for AE2 storage terminals that preserves native filtering and sorting
  inside groups, leaves the pinned row untouched, and refreshes only when the required item set changes.
- Development implementation for Refined Storage 2.0.9 grids using their filtered repository and native
  finalized sort order. Runtime acceptance remains pending.
- Demand-only candidate replay, knowledge invalidation, and a dedicated priority configuration toggle.
- Ordering/cache unit tests, fixed-version injection contract tests, and an opt-in development-only
  class-transformation smoke test. Arcane behavior is user-verified; ProjectE's own screen still needs
  in-game acceptance.
- Page-independent EMI logical inventory support for Project Expansion's Arcane Transmutation Tablet.
- Native EMI recipe transfer through Project Expansion's existing server-side transfer implementation.
- Client configuration toggle for the Project Expansion integration.

### Changed

- Arcane Transmutation Tablet output-page slots are no longer treated as stored material sources.
- Learned items are exposed to EMI according to the amount currently producible from available EMC.
- Learned Arcane Transmutation Tablet items are resolved only when EMI queries them, avoiding full
  knowledge-set rebuilds during screen refreshes in large modpacks.

## [0.1.0] - 2026-07-20

### Added

- EMI Recipe Tree / Crafting Mode highlights for Sophisticated Storage inventory slots.
- EMI Recipe Tree / Crafting Mode highlights for Sophisticated Backpacks inventory slots.
- Adapter-based structure for adding support for other container Mods.
- Client configuration for global, Storage, Backpacks, and debug controls.
- English and Japanese Mod metadata.
- Dedicated-server-safe client initialization boundary.

### Verified

- Minecraft 1.21.1 and NeoForge 21.1.234.
- Sophisticated chests, barrels, limited barrels, and backpacks.
- Scrolling, searching, upgrade-replaced slots, GUI scaling, and Crafting Mode transitions.
- Vanilla container screens remain unaffected.
