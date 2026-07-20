package dev.circlestar.emicontainerhighlight.client.adapter;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class AdapterRegistry {
    private final List<ContainerHighlightAdapter> adapters = new ArrayList<>();

    public void register(ContainerHighlightAdapter adapter) {
        adapters.add(adapter);
    }

    public Optional<ContainerHighlightAdapter> find(AbstractContainerScreen<?> screen) {
        return adapters.stream().filter(adapter -> adapter.supports(screen)).findFirst();
    }
}
