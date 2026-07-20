package dev.circlestar.emicontainerhighlight.client.adapter;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import java.util.stream.Stream;

public interface ContainerHighlightAdapter {
    boolean supports(AbstractContainerScreen<?> screen);

    Stream<HighlightTarget> getVisibleTargets(AbstractContainerScreen<?> screen);

    String id();
}
