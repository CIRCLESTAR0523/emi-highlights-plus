package dev.circlestar.emicontainerhighlight.client;

import dev.circlestar.emicontainerhighlight.EmiContainerHighlights;
import dev.circlestar.emicontainerhighlight.client.adapter.AdapterRegistry;
import dev.circlestar.emicontainerhighlight.client.adapter.SophisticatedBackpackAdapter;
import dev.circlestar.emicontainerhighlight.client.adapter.SophisticatedStorageAdapter;
import dev.circlestar.emicontainerhighlight.client.emi.EmiRequiredIngredientProvider;
import net.neoforged.neoforge.common.NeoForge;

final class SophisticatedStorageClientCompat {
    private SophisticatedStorageClientCompat() {
    }

    static void initialize(boolean storageLoaded, boolean backpacksLoaded) {
        AdapterRegistry adapters = new AdapterRegistry();
        if (storageLoaded) {
            adapters.register(new SophisticatedStorageAdapter());
            EmiContainerHighlights.LOGGER.info("Registered Sophisticated Storage highlight adapter");
        }
        if (backpacksLoaded) {
            adapters.register(new SophisticatedBackpackAdapter());
            EmiContainerHighlights.LOGGER.info("Registered Sophisticated Backpacks highlight adapter");
        }

        ClientHighlightHandler handler = new ClientHighlightHandler(
                new EmiRequiredIngredientProvider(),
                adapters,
                new HighlightRenderer()
        );
        NeoForge.EVENT_BUS.addListener(handler::onContainerForeground);
    }
}
