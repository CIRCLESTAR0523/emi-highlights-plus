package dev.circlestar.emihighlightsplus.client;

import dev.circlestar.emihighlightsplus.EmiHighlightsPlus;
import net.neoforged.fml.ModList;

public final class ClientBootstrap {
    private ClientBootstrap() {
    }

    public static void initialize() {
        ModList modList = ModList.get();
        if (!modList.isLoaded("emi")) {
            EmiHighlightsPlus.LOGGER.warn("EMI is not loaded; client compatibility will not be registered");
            return;
        }
        if (modList.isLoaded("projecte")) {
            dev.circlestar.emihighlightsplus.client.projectexpansion.ProjectETransmutationPriorityController.initialize();
            if (modList.isLoaded("projectexpansion")) {
                dev.circlestar.emihighlightsplus.client.projectexpansion.ArcanePriorityController.initialize();
            }
        }
        if (modList.isLoaded("ae2")) {
            dev.circlestar.emihighlightsplus.client.ae2.AE2PriorityController.initialize();
        }
        if (modList.isLoaded("refinedstorage")) {
            dev.circlestar.emihighlightsplus.client.refinedstorage.RefinedStoragePriorityController.initialize();
        }
        if (!modList.isLoaded("sophisticatedcore")) {
            EmiHighlightsPlus.LOGGER.info("Sophisticated Core is not loaded; Sophisticated adapters will not be registered");
            return;
        }

        boolean storageLoaded = modList.isLoaded("sophisticatedstorage");
        boolean backpacksLoaded = modList.isLoaded("sophisticatedbackpacks");
        if (!storageLoaded && !backpacksLoaded) {
            EmiHighlightsPlus.LOGGER.info("Sophisticated Storage and Backpacks are not loaded; no adapter will be registered");
            return;
        }

        SophisticatedStorageClientCompat.initialize(storageLoaded, backpacksLoaded);
    }
}
