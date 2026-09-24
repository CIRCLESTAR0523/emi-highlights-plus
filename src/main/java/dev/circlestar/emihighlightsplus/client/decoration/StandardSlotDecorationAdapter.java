package dev.circlestar.emihighlightsplus.client.decoration;

import dev.circlestar.emihighlightsplus.client.HighlightRenderer;
import dev.circlestar.emihighlightsplus.client.adapter.HighlightTarget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

abstract class StandardSlotDecorationAdapter implements HighlightDecorationAdapter {
    private final HighlightRenderer renderer;

    protected StandardSlotDecorationAdapter(HighlightRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void renderDecorations(
            AbstractContainerScreen<?> screen,
            GuiGraphics graphics,
            HighlightTarget target
    ) {
        renderer.renderDecorations(graphics, target);
    }
}
