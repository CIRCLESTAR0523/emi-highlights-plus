package dev.circlestar.emihighlightsplus.client.decoration;

import dev.circlestar.emihighlightsplus.client.RequiredIngredientProvider;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class NativeOverlayDecorationHandler {
    private final RequiredIngredientProvider ingredientProvider;
    private final DecorationAdapterRegistry adapters;

    private NativeOverlayDecorationHandler(
            RequiredIngredientProvider ingredientProvider,
            DecorationAdapterRegistry adapters
    ) {
        this.ingredientProvider = ingredientProvider;
        this.adapters = adapters;
    }

    public static void initialize(
            RequiredIngredientProvider ingredientProvider,
            DecorationAdapterRegistry adapters
    ) {
        NativeOverlayDecorationHandler handler = new NativeOverlayDecorationHandler(ingredientProvider, adapters);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, handler::onContainerForeground);
    }

    private void onContainerForeground(ContainerScreenEvent.Render.Foreground event) {
        if (!ClientConfig.ENABLED.get()) {
            return;
        }
        AbstractContainerScreen<?> screen = event.getContainerScreen();
        HighlightDecorationAdapter adapter = adapters.find(screen).orElse(null);
        if (adapter == null) {
            return;
        }
        RequiredIngredientProvider.Snapshot snapshot = ingredientProvider.snapshot(screen);
        if (!snapshot.isActive()) {
            return;
        }
        adapter.getTargets(screen, snapshot).forEach(target ->
                adapter.renderDecorations(screen, event.getGuiGraphics(), target));
    }
}
