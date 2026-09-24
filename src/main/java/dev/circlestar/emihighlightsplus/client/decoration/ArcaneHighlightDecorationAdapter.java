package dev.circlestar.emihighlightsplus.client.decoration;

import cool.furry.mc.neoforge.projectexpansion.gui.container.ContainerArcaneTransmutationTablet;
import dev.circlestar.emihighlightsplus.client.HighlightRenderer;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public final class ArcaneHighlightDecorationAdapter extends StandardSlotDecorationAdapter {
    public ArcaneHighlightDecorationAdapter(HighlightRenderer renderer) {
        super(renderer);
    }

    @Override
    public boolean supports(AbstractContainerScreen<?> screen) {
        return ClientConfig.PROJECT_EXPANSION_ENABLED.get()
                && screen.getMenu() instanceof ContainerArcaneTransmutationTablet;
    }
}
