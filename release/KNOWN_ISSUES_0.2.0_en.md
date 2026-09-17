# EMI Highlights Plus 0.2.0 Known Issues

## Refined Storage Grid runtime verification

- Status: automated coverage complete; runtime verification pending.
- Impact: display, search, sorting, and item interaction have not been verified in an actual Refined Storage 2.0.9 Grid.
- Workaround: set `refinedStorageEnabled=false` in `config/emi_highlights_plus-client.toml` if the integration causes a problem.
- Report at: https://github.com/CIRCLESTAR0523/emi-highlights-plus/issues

## Migration from the old Mod ID

- The old `emi_container_highlights-client.toml` is not migrated automatically.
- Remove the old JAR. Installing both JARs loads them as separate Mods.
- This Mod does not migrate or alter world or player data.
