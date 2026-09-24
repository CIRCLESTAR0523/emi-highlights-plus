package dev.circlestar.emihighlightsplus.client.decoration;

import dev.circlestar.emihighlightsplus.client.RequiredIngredientProvider;
import dev.circlestar.emihighlightsplus.client.adapter.HighlightTarget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import java.util.stream.Stream;

public interface HighlightDecorationAdapter {
    boolean supports(AbstractContainerScreen<?> screen);

    default Stream<HighlightTarget> getTargets(
            AbstractContainerScreen<?> screen,
            RequiredIngredientProvider.Snapshot snapshot
    ) {
        return NativeOverlayTargets.collect(screen, snapshot);
    }

    void renderDecorations(
            AbstractContainerScreen<?> screen,
            GuiGraphics graphics,
            HighlightTarget target
    );
}
