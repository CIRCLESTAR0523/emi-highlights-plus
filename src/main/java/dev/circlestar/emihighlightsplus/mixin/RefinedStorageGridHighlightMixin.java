package dev.circlestar.emihighlightsplus.mixin;

import com.refinedmods.refinedstorage.common.api.grid.view.GridResource;
import com.refinedmods.refinedstorage.common.grid.screen.AbstractGridScreen;
import com.refinedmods.refinedstorage.common.grid.view.ItemGridResource;
import dev.circlestar.emihighlightsplus.client.HighlightRenderer;
import dev.circlestar.emihighlightsplus.client.refinedstorage.RefinedStoragePriorityAccess;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.refinedmods.refinedstorage.common.grid.screen.AbstractGridScreen", remap = false)
abstract class RefinedStorageGridHighlightMixin {
    private static final HighlightRenderer ECH$HIGHLIGHT_RENDERER = new HighlightRenderer();

    @Shadow
    private void renderAmount(GuiGraphics graphics, int x, int y, GridResource resource) {
        throw new AssertionError();
    }

    @Inject(
            method = "renderResourceWithAmount",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/refinedmods/refinedstorage/common/api/grid/view/GridResource;render(Lnet/minecraft/client/gui/GuiGraphics;II)V",
                    shift = At.Shift.AFTER
            ),
            remap = false
    )
    private void ech$renderRequiredBackground(
            GuiGraphics graphics,
            int x,
            int y,
            GridResource resource,
            CallbackInfo ci
    ) {
        if (!(resource instanceof ItemGridResource item)) {
            return;
        }
        AbstractGridScreen<?> screen = (AbstractGridScreen<?>) (Object) this;
        RefinedStoragePriorityAccess priority =
                (RefinedStoragePriorityAccess) screen.getMenu().getRepository();
        if (priority.ech$isRequiredItem(item.getItemResource())) {
            ECH$HIGHLIGHT_RENDERER.render(graphics, x, y);
        }
    }

    @Redirect(
            method = "renderResourceWithAmount",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/refinedmods/refinedstorage/common/grid/screen/AbstractGridScreen;renderAmount(Lnet/minecraft/client/gui/GuiGraphics;IILcom/refinedmods/refinedstorage/common/api/grid/view/GridResource;)V"
            ),
            remap = false
    )
    private void ech$renderAmountAboveHighlight(
            AbstractGridScreen<?> screen,
            GuiGraphics graphics,
            int x,
            int y,
            GridResource resource
    ) {
        if (resource instanceof ItemGridResource item
                && ((RefinedStoragePriorityAccess) screen.getMenu().getRepository())
                .ech$isRequiredItem(item.getItemResource())) {
            ECH$HIGHLIGHT_RENDERER.renderDecorationLayer(
                    graphics,
                    () -> renderAmount(graphics, x, y, resource)
            );
        } else {
            renderAmount(graphics, x, y, resource);
        }
    }
}
