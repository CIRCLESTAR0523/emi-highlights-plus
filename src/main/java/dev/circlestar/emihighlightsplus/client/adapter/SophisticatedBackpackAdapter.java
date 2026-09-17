package dev.circlestar.emihighlightsplus.client.adapter;

import dev.circlestar.emihighlightsplus.config.ClientConfig;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.BackpackScreen;

public final class SophisticatedBackpackAdapter extends SophisticatedStorageAdapter {
    @Override
    public boolean supports(AbstractContainerScreen<?> screen) {
        return ClientConfig.SOPHISTICATED_BACKPACKS_ENABLED.get() && screen instanceof BackpackScreen;
    }

    @Override
    public String id() {
        return "sophisticated_backpacks";
    }
}
