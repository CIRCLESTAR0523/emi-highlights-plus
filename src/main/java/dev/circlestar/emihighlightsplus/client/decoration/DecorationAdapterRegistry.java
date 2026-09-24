package dev.circlestar.emihighlightsplus.client.decoration;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class DecorationAdapterRegistry {
    private final List<HighlightDecorationAdapter> adapters = new ArrayList<>();

    public void register(HighlightDecorationAdapter adapter) {
        adapters.add(adapter);
    }

    public Optional<HighlightDecorationAdapter> find(AbstractContainerScreen<?> screen) {
        return adapters.stream().filter(adapter -> adapter.supports(screen)).findFirst();
    }

    public boolean isEmpty() {
        return adapters.isEmpty();
    }
}
