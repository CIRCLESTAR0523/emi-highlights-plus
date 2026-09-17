package dev.circlestar.emihighlightsplus.client;

import dev.circlestar.emihighlightsplus.EmiHighlightsPlus;
import dev.circlestar.emihighlightsplus.client.adapter.AdapterRegistry;
import dev.circlestar.emihighlightsplus.client.adapter.SophisticatedBackpackAdapter;
import dev.circlestar.emihighlightsplus.client.adapter.SophisticatedStorageAdapter;
import dev.circlestar.emihighlightsplus.client.emi.EmiRequiredIngredientProvider;
import net.neoforged.neoforge.common.NeoForge;

final class SophisticatedStorageClientCompat {
    private SophisticatedStorageClientCompat() {
    }

    static void initialize(boolean storageLoaded, boolean backpacksLoaded) {
        AdapterRegistry adapters = new AdapterRegistry();
        if (storageLoaded) {
            adapters.register(new SophisticatedStorageAdapter());
            EmiHighlightsPlus.LOGGER.info("Registered Sophisticated Storage highlight adapter");
        }
        if (backpacksLoaded) {
            adapters.register(new SophisticatedBackpackAdapter());
            EmiHighlightsPlus.LOGGER.info("Registered Sophisticated Backpacks highlight adapter");
        }

        ClientHighlightHandler handler = new ClientHighlightHandler(
                new EmiRequiredIngredientProvider(),
                adapters,
                new HighlightRenderer()
        );
        NeoForge.EVENT_BUS.addListener(handler::onContainerForeground);
    }
}
