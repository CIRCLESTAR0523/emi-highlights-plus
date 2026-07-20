package dev.circlestar.emicontainerhighlight.client;

import dev.circlestar.emicontainerhighlight.EmiContainerHighlights;
import net.neoforged.fml.ModList;

public final class ClientBootstrap {
    private ClientBootstrap() {
    }

    public static void initialize() {
        ModList modList = ModList.get();
        if (!modList.isLoaded("emi")) {
            EmiContainerHighlights.LOGGER.warn("EMI is not loaded; client compatibility will not be registered");
            return;
        }
        if (!modList.isLoaded("sophisticatedcore")) {
            EmiContainerHighlights.LOGGER.info("Sophisticated Core is not loaded; Sophisticated adapters will not be registered");
            return;
        }

        boolean storageLoaded = modList.isLoaded("sophisticatedstorage");
        boolean backpacksLoaded = modList.isLoaded("sophisticatedbackpacks");
        if (!storageLoaded && !backpacksLoaded) {
            EmiContainerHighlights.LOGGER.info("Sophisticated Storage and Backpacks are not loaded; no adapter will be registered");
            return;
        }

        SophisticatedStorageClientCompat.initialize(storageLoaded, backpacksLoaded);
    }
}
