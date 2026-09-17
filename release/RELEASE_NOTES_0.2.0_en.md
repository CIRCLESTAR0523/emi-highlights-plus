# EMI Highlights Plus 0.2.0 Release Notes

- Release type: Stable
- Release date: Not published
- Previous version: 0.1.0 (source history under the old Mod ID)

## Overview

EMI Highlights Plus brings EMI Recipe Tree and Crafting Mode material cues to supported storage and transmutation screens. It keeps the original Sophisticated Storage and Backpacks highlights and adds required-material priority ordering for ProjectE-family screens and AE2 terminals.

## Highlights

- Renamed the project to EMI Highlights Plus and changed the Mod ID to `emi_highlights_plus`.
- Added required-material priority ordering for ProjectE Transmutation Table/Tablet, Project Expansion's Arcane Transmutation Tablet, and AE2 storage terminals.
- Added Refined Storage 2.0.9 integration; runtime testing with an actual Grid is still pending.
- Reduced Arcane Tablet material-query overhead and stopped visible output candidates from being counted as physical inventory.

## Supported Environment

- Minecraft: 1.21.1
- Loader: NeoForge 21.1.234 or later
- Java: 21
- Side: client functionality; verified to load safely on a dedicated server
- Tested: Sophisticated Storage/Backpacks, ProjectE, Project Expansion, and AE2. Refined Storage has automated coverage only

## Dependencies

- Required: EMI 1.1.24 or later
- Optional: Sophisticated Core 1.4.60+, Sophisticated Storage 1.5.63+, Sophisticated Backpacks 3.25.64+, ProjectE 1.1.0+, Project Expansion 1.21.1-1.0.6+, AE2 19.2.17+, and Refined Storage 2.0.9+

## Who Should Update

This release is for players who want EMI material highlighting or priority ordering in the supported screens. Users of the old Mod ID must follow the migration steps below.

## Installation and Update

1. Close Minecraft.
2. Remove any old `emi_container_highlights-*.jar`, then place `emi_highlights_plus-0.2.0.jar` in `mods`. Do not install both JARs together.
3. Old configuration is not migrated automatically. Copy only the settings you need into the new `config/emi_highlights_plus-client.toml`.

## Breaking Changes and Migration

The Mod ID and configuration filename have changed. The Mod does not alter worlds or player data. Remove the old JAR and manually copy any settings you still need.

## Known Issues

Refined Storage 2.0.9 integration has automated coverage but has not been tested with an actual Grid. If it causes a problem, disable `refinedStorageEnabled` and report it through GitHub Issues.

## Download and Support

- Download: Not published
- Issues and support: https://github.com/CIRCLESTAR0523/emi-highlights-plus/issues
- Artifact: `emi_highlights_plus-0.2.0.jar`
- SHA-256: `F25F587BF70F599111D5295D7F1ED08446085F4B425F2BC42A45E316794ED290`

## Acknowledgements

Compatibility is built around the public APIs and behavior of EMI and the optional integration Mods. Generative AI assisted development and documentation; the author reviewed the results.
