package dev.circlestar.emihighlightsplus.client.decoration;

import dev.circlestar.emihighlightsplus.client.HighlightRenderer;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import moze_intel.projecte.gameObjs.container.TransmutationContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public final class ProjectEHighlightDecorationAdapter extends StandardSlotDecorationAdapter {
    public ProjectEHighlightDecorationAdapter(HighlightRenderer renderer) {
        super(renderer);
    }

    @Override
    public boolean supports(AbstractContainerScreen<?> screen) {
        return ClientConfig.PROJECT_E_ENABLED.get()
                && screen.getMenu() instanceof TransmutationContainer;
    }
}
