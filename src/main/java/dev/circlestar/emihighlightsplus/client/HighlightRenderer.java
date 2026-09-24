package dev.circlestar.emihighlightsplus.client;

import dev.emi.emi.runtime.EmiDrawContext;
import dev.circlestar.emihighlightsplus.client.adapter.HighlightTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public final class HighlightRenderer {
    public static final int EMI_CRAFTING_MODE_COLOR = 0x7700BBFF;
    private static final float EMI_SLOT_OVERLAY_Z = 300.0F;

    public void render(GuiGraphics graphics, HighlightTarget target) {
        render(graphics, target.x(), target.y());
    }

    public void render(GuiGraphics graphics, int x, int y) {
        EmiDrawContext context = EmiDrawContext.wrap(graphics);
        context.push();
        try {
            context.matrices().translate(0.0F, 0.0F, EMI_SLOT_OVERLAY_Z);
            context.fill(
                    x - 1,
                    y - 1,
                    18,
                    18,
                    EMI_CRAFTING_MODE_COLOR
            );
        } finally {
            context.pop();
        }
    }

    public void renderDecorations(GuiGraphics graphics, HighlightTarget target) {
        renderDecorationLayer(graphics, () -> graphics.renderItemDecorations(
                Minecraft.getInstance().font,
                target.stack(),
                target.x(),
                target.y()
        ));
    }

    public void renderDecorationLayer(GuiGraphics graphics, Runnable draw) {
        EmiDrawContext context = EmiDrawContext.wrap(graphics);
        context.push();
        try {
            context.matrices().translate(0.0F, 0.0F, EMI_SLOT_OVERLAY_Z + 1.0F);
            draw.run();
        } finally {
            context.pop();
        }
    }
}
