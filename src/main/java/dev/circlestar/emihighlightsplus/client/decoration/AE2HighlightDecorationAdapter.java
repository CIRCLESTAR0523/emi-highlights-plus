package dev.circlestar.emihighlightsplus.client.decoration;

import appeng.api.stacks.AmountFormat;
import appeng.client.gui.me.common.MEStorageScreen;
import appeng.client.gui.me.common.RepoSlot;
import appeng.client.gui.me.common.StackSizeRenderer;
import appeng.core.AEConfig;
import dev.circlestar.emihighlightsplus.client.HighlightRenderer;
import dev.circlestar.emihighlightsplus.client.adapter.HighlightTarget;
import dev.circlestar.emihighlightsplus.client.ae2.AE2StorageScreenAccess;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public final class AE2HighlightDecorationAdapter implements HighlightDecorationAdapter {
    private final HighlightRenderer renderer;

    public AE2HighlightDecorationAdapter(HighlightRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public boolean supports(AbstractContainerScreen<?> screen) {
        return ClientConfig.AE2_ENABLED.get() && screen instanceof MEStorageScreen<?>;
    }

    @Override
    public void renderDecorations(
            AbstractContainerScreen<?> screen,
            GuiGraphics graphics,
            HighlightTarget target
    ) {
        if (!(target.slot() instanceof RepoSlot repoSlot) || repoSlot.getEntry() == null) {
            renderer.renderDecorations(graphics, target);
            return;
        }
        var entry = repoSlot.getEntry();
        long amount = entry.getStoredAmount();
        boolean craftable = entry.isCraftable();
        boolean large = AEConfig.instance().isUseLargeFonts();
        boolean craftableOnly = ((AE2StorageScreenAccess) screen).ech$isViewOnlyCraftable();
        renderer.renderDecorationLayer(graphics, () -> {
            if (craftable && (craftableOnly || amount <= 0)) {
                StackSizeRenderer.renderSizeLabel(
                        graphics, Minecraft.getInstance().font, target.x(), target.y(), "+"
                );
                return;
            }
            AmountFormat format = large ? AmountFormat.SLOT_LARGE_FONT : AmountFormat.SLOT;
            String amountText = entry.getWhat().formatAmount(amount, format);
            StackSizeRenderer.renderSizeLabel(
                    graphics, Minecraft.getInstance().font, target.x(), target.y(), amountText, large
            );
            if (craftable) {
                StackSizeRenderer.renderSizeLabel(
                        graphics, Minecraft.getInstance().font,
                        target.x() - 11, target.y() - 11, "+", false
                );
            }
        });
    }
}
