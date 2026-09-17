package dev.circlestar.emihighlightsplus.client.emi;

import dev.circlestar.emihighlightsplus.EmiHighlightsPlus;
import dev.circlestar.emihighlightsplus.client.projectexpansion.ProjectExpansionEmiCompat;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import net.neoforged.fml.ModList;

@EmiEntrypoint
public final class EmiHighlightsPlusPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        ModList modList = ModList.get();
        if (!ClientConfig.ENABLED.get() || !ClientConfig.PROJECT_EXPANSION_ENABLED.get()) {
            return;
        }
        if (!modList.isLoaded("projecte") || !modList.isLoaded("projectexpansion")) {
            return;
        }

        ProjectExpansionEmiCompat.register(registry);
        EmiHighlightsPlus.LOGGER.info("Registered Project Expansion Arcane Transmutation Tablet EMI inventory compatibility");
    }
}
