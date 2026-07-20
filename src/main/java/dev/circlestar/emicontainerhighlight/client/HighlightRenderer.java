package dev.circlestar.emicontainerhighlight.client;

import dev.emi.emi.runtime.EmiDrawContext;
import dev.circlestar.emicontainerhighlight.client.adapter.HighlightTarget;
import net.minecraft.client.gui.GuiGraphics;

public final class HighlightRenderer {
    public static final int EMI_CRAFTING_MODE_COLOR = 0x7700BBFF;
    private static final float EMI_SLOT_OVERLAY_Z = 300.0F;

    public void render(GuiGraphics graphics, HighlightTarget target) {
        EmiDrawContext context = EmiDrawContext.wrap(graphics);
        context.push();
        try {
            context.matrices().translate(0.0F, 0.0F, EMI_SLOT_OVERLAY_Z);
            context.fill(
                    target.x() - 1,
                    target.y() - 1,
                    18,
                    18,
                    EMI_CRAFTING_MODE_COLOR
            );
        } finally {
            context.pop();
        }
    }
}
