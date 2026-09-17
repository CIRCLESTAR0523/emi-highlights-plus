package dev.circlestar.emihighlightsplus.mixin;

import appeng.api.config.SortDir;
import appeng.api.config.SortOrder;
import appeng.api.stacks.AEItemKey;
import appeng.client.gui.me.common.Repo;
import appeng.client.gui.widgets.IScrollSource;
import appeng.client.gui.widgets.Scrollbar;
import appeng.menu.me.common.GridInventoryEntry;
import dev.circlestar.emihighlightsplus.client.ae2.AE2PriorityAccess;
import dev.circlestar.emihighlightsplus.client.priority.StablePriorityOrder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.Set;

@Pseudo
@Mixin(targets = "appeng.client.gui.me.common.Repo", remap = false)
abstract class AE2RepoPriorityMixin implements AE2PriorityAccess {
    @Shadow @Final private IScrollSource src;
    @Unique private Set<AEItemKey> ech$required = Set.of();

    @Override
    public void ech$setRequiredItems(Set<AEItemKey> required) {
        if (ech$required.equals(required)) {
            return;
        }
        ech$required = required;
        if (src instanceof Scrollbar scrollbar) {
            scrollbar.setCurrentScroll(0);
        }
        ((Repo) (Object) this).updateView();
    }

    @Inject(method = "getComparator", at = @At("RETURN"), cancellable = true, remap = false)
    @SuppressWarnings("unchecked")
    private void ech$prioritizeRequired(SortOrder order, SortDir direction,
            CallbackInfoReturnable<Comparator<? super GridInventoryEntry>> cir) {
        if (!ech$required.isEmpty()) {
            Comparator<GridInventoryEntry> original =
                    (Comparator<GridInventoryEntry>) cir.getReturnValue();
            cir.setReturnValue(StablePriorityOrder.comparator(this::ech$isRequired, original));
        }
    }

    @Unique
    private boolean ech$isRequired(GridInventoryEntry entry) {
        return entry.getWhat() instanceof AEItemKey itemKey && ech$required.contains(itemKey);
    }
}
