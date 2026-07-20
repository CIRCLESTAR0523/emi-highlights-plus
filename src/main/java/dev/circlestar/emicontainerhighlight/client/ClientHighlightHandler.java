package dev.circlestar.emicontainerhighlight.client;

import dev.circlestar.emicontainerhighlight.EmiContainerHighlights;
import dev.circlestar.emicontainerhighlight.client.adapter.AdapterRegistry;
import dev.circlestar.emicontainerhighlight.client.adapter.ContainerHighlightAdapter;
import dev.circlestar.emicontainerhighlight.client.adapter.HighlightTarget;
import dev.circlestar.emicontainerhighlight.config.ClientConfig;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public final class ClientHighlightHandler {
    private static final long DEBUG_INTERVAL_NANOS = TimeUnit.SECONDS.toNanos(5);

    private final RequiredIngredientProvider ingredientProvider;
    private final AdapterRegistry adapters;
    private final HighlightRenderer renderer;
    private long lastDebugLog;

    public ClientHighlightHandler(
            RequiredIngredientProvider ingredientProvider,
            AdapterRegistry adapters,
            HighlightRenderer renderer
    ) {
        this.ingredientProvider = ingredientProvider;
        this.adapters = adapters;
        this.renderer = renderer;
    }

    public void onContainerForeground(ContainerScreenEvent.Render.Foreground event) {
        if (!ClientConfig.ENABLED.get()) {
            return;
        }

        AbstractContainerScreen<?> screen = event.getContainerScreen();
        ContainerHighlightAdapter adapter = adapters.find(screen).orElse(null);
        if (adapter == null) {
            return;
        }

        RequiredIngredientProvider.Snapshot snapshot = ingredientProvider.snapshot(screen);
        if (!snapshot.isActive()) {
            return;
        }

        List<HighlightTarget> visibleTargets = adapter.getVisibleTargets(screen).toList();
        int highlighted = 0;
        for (HighlightTarget target : visibleTargets) {
            if (snapshot.wasSuppressedByEmi(target.slot()) && snapshot.isRequired(target.stack())) {
                renderer.render(event.getGuiGraphics(), target);
                highlighted++;
            }
        }

        logDebug(screen, adapter, snapshot, visibleTargets.size(), highlighted);
    }

    private void logDebug(
            AbstractContainerScreen<?> screen,
            ContainerHighlightAdapter adapter,
            RequiredIngredientProvider.Snapshot snapshot,
            int visibleTargets,
            int highlighted
    ) {
        if (!ClientConfig.DEBUG.get()) {
            return;
        }
        long now = System.nanoTime();
        if (now - lastDebugLog < DEBUG_INTERVAL_NANOS) {
            return;
        }
        lastDebugLog = now;
        EmiContainerHighlights.LOGGER.info(
                "Highlight debug: screen={}, adapter={}, visibleStorageSlots={}, requiredStacks={}, emiSuppressedSlots={}, highlighted={}",
                screen.getClass().getName(),
                adapter.id(),
                visibleTargets,
                snapshot.requiredStackCount(),
                snapshot.suppressedSlotCount(),
                highlighted
        );
    }
}
