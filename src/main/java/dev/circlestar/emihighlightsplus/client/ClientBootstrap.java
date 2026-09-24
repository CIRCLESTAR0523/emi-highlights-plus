package dev.circlestar.emihighlightsplus.client;

import dev.circlestar.emihighlightsplus.EmiHighlightsPlus;
import dev.circlestar.emihighlightsplus.client.decoration.AE2HighlightDecorationAdapter;
import dev.circlestar.emihighlightsplus.client.decoration.ArcaneHighlightDecorationAdapter;
import dev.circlestar.emihighlightsplus.client.decoration.DecorationAdapterRegistry;
import dev.circlestar.emihighlightsplus.client.decoration.NativeOverlayDecorationHandler;
import dev.circlestar.emihighlightsplus.client.decoration.ProjectEHighlightDecorationAdapter;
import dev.circlestar.emihighlightsplus.client.emi.EmiRequiredIngredientProvider;
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
        EmiRequiredIngredientProvider ingredientProvider = new EmiRequiredIngredientProvider();
        HighlightRenderer highlightRenderer = new HighlightRenderer();
        DecorationAdapterRegistry decorationAdapters = new DecorationAdapterRegistry();
        if (modList.isLoaded("projecte")) {
            dev.circlestar.emihighlightsplus.client.projectexpansion.ProjectETransmutationPriorityController.initialize();
            decorationAdapters.register(new ProjectEHighlightDecorationAdapter(highlightRenderer));
            if (modList.isLoaded("projectexpansion")) {
                dev.circlestar.emihighlightsplus.client.projectexpansion.ArcanePriorityController.initialize();
                decorationAdapters.register(new ArcaneHighlightDecorationAdapter(highlightRenderer));
            }
        }
        if (modList.isLoaded("ae2")) {
            dev.circlestar.emihighlightsplus.client.ae2.AE2PriorityController.initialize();
            decorationAdapters.register(new AE2HighlightDecorationAdapter(highlightRenderer));
        }
        if (modList.isLoaded("refinedstorage")) {
            dev.circlestar.emihighlightsplus.client.refinedstorage.RefinedStoragePriorityController.initialize();
        }
        if (!decorationAdapters.isEmpty()) {
            NativeOverlayDecorationHandler.initialize(ingredientProvider, decorationAdapters);
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

        SophisticatedStorageClientCompat.initialize(
                storageLoaded,
                backpacksLoaded,
                ingredientProvider,
                highlightRenderer
        );
    }
}
