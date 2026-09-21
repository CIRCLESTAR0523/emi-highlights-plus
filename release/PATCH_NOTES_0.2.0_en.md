# EMI Highlights Plus 0.2.0 Patch Notes

- Release type: Stable
- GitHub release date: 2026-09-21
- Previous version: 0.1.0 (source history under the old Mod ID)
- Supported Minecraft: 1.21.1
- Supported loader: NeoForge 21.1.234 or later
- Supported Java: 21

## Added

- `ADD-001` Added required-material priority ordering to ProjectE Transmutation Table/Tablet and Project Expansion's Arcane Transmutation Tablet.
- `ADD-002` Added required-material priority ordering to AE2 storage terminals while preserving search, view filters, native sorting, and pinned rows.
- `ADD-003` Added priority ordering for Refined Storage 2.0.9 Grids. It has automated coverage but no runtime Grid verification.
- `ADD-004` Added per-integration settings for ProjectE-family, AE2, and RS support, plus a required-material priority setting.

## Changed

- `CHG-001` Renamed the project to EMI Highlights Plus, changed the Mod ID to `emi_highlights_plus`, and changed the configuration filename to `emi_highlights_plus-client.toml`.
- `CHG-002` Arcane Tablet learned items are exposed to EMI according to the amount producible from current EMC. Visible output candidates are not counted as physical inventory.
- `CHG-003` Candidate ordering refreshes only when the required item set changes and preserves each integration's native order inside the priority groups.

## Fixed

- `FIX-001` Fixed an Arcane Tablet freeze caused by running exact transfer resolution across every recipe while EMI rebuilt its craftable list.

## Removed

- None.

## Compatibility and Dependencies

- `COMPAT-001` EMI 1.1.24+ is required. Sophisticated Core/Storage/Backpacks, ProjectE, Project Expansion, AE2, and Refined Storage are declared as optional dependencies.
- `COMPAT-002` Rechecked safe dedicated-server loading and startup without optional integrations.

## Configuration and Data

- `DATA-001` The old `emi_container_highlights-client.toml` is neither migrated nor deleted automatically. No world or player-data format is changed.

## Breaking Changes and Migration

- `BREAK-001` Because the Mod ID changed, the old and new JARs are treated as separate Mods. Remove the old JAR and manually copy only the settings you need.

## Known Issues

- `KNOWN-001` Refined Storage 2.0.9 integration has not been verified with an actual Grid. Set `refinedStorageEnabled=false` as a workaround if needed.

## Artifact

- File: `emi_highlights_plus-0.2.0.jar`
- Size: 58,615 bytes
- SHA-256: `F25F587BF70F599111D5295D7F1ED08446085F4B425F2BC42A45E316794ED290`
