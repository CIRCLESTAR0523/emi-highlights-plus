package dev.circlestar.emihighlightsplus.mixin;

import com.refinedmods.refinedstorage.api.resource.repository.ResourceRepositoryImpl;
import com.refinedmods.refinedstorage.api.resource.repository.SortingDirection;
import com.refinedmods.refinedstorage.common.grid.view.ItemGridResource;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import dev.circlestar.emihighlightsplus.client.priority.StablePriorityOrder;
import dev.circlestar.emihighlightsplus.client.refinedstorage.RefinedStoragePriorityAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.Set;

@Pseudo
@Mixin(targets = "com.refinedmods.refinedstorage.api.resource.repository.ResourceRepositoryImpl", remap = false)
abstract class RefinedStorageRepositoryPriorityMixin implements RefinedStoragePriorityAccess {
    @Shadow private Comparator<Object> sort;
    @Unique private Set<ItemResource> ech$required = Set.of();
    @Unique private boolean ech$controlled;
    @Unique private boolean ech$wrapped;

    @Override
    public void ech$setRequiredItems(Set<ItemResource> required) {
        boolean changed = !ech$required.equals(required);
        ech$required = required;
        ech$controlled = true;
        ech$wrapCurrentSort();
        if (changed) {
            ((ResourceRepositoryImpl<?>) (Object) this).sort();
        }
    }

    @Inject(method = "setSort", at = @At("TAIL"), remap = false)
    private void ech$wrapChangedSort(Comparator<Object> comparator, SortingDirection direction, CallbackInfo ci) {
        ech$wrapped = false;
        if (ech$controlled) {
            ech$wrapCurrentSort();
        }
    }

    @Unique
    private void ech$wrapCurrentSort() {
        if (!ech$wrapped) {
            sort = StablePriorityOrder.comparator(this::ech$isRequired, sort);
            ech$wrapped = true;
        }
    }

    @Unique
    private boolean ech$isRequired(Object candidate) {
        return candidate instanceof ItemGridResource item
                && ech$required.contains(item.getItemResource());
    }
}
