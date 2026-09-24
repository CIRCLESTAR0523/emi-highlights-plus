# EMI Highlights Plus 0.2.1 Patch Notes

- Release type: Stable patch
- Publication date: 2026-09-24
- Previous version: 0.2.0
- Supported Minecraft: 1.21.1
- Supported loader: NeoForge 21.1.234 or later
- Supported Java: 21

## Added

- `ADD-001` Added EMI Crafting Mode's cyan required-item overlay to Refined Storage Grid and Wireless Grid resources.

## Changed

- `CHG-001` Standardized required-item layering across supported screens so stack counts and terminal symbols render above the translucent overlay.
- `CHG-002` Reused the same required-ingredient snapshot and highlight renderer across compatible native container screens.

## Fixed

- `FIX-001` Prevented supported native container overlays from tinting or obscuring stack counts.

## Removed

- None.

## Compatibility and Dependencies

- EMI 1.1.24+ remains required.
- Sophisticated Core/Storage/Backpacks, ProjectE, Project Expansion, AE2, and Refined Storage remain optional.
- Verified integration contracts target AE2 19.2.17 and Refined Storage 2.0.9.

## Configuration and Data

- No configuration format changes.
- No world, player-data, networking, or server-transaction changes.

## Breaking Changes and Migration

- None. Remove the 0.2.0 JAR and install 0.2.1; do not keep both versions together.

## Verification

- Clean build and 22 automated tests passed.
- Runtime layering passed in RS Grid/Wireless Grid, AE2 Terminal/Wireless Terminal, ProjectE/Project Expansion, and Sophisticated Storage/Backpacks screens.

## Artifact

- File: `emi_highlights_plus-0.2.1.jar`
- Size: 73,215 bytes
- SHA-256: `C2F9D7567712890CBE045E363B1B0FB9DB7A8095AE0C4D267C7E0219ED473E61`
