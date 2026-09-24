package dev.circlestar.emihighlightsplus.client.refinedstorage;

import com.refinedmods.refinedstorage.common.support.resource.ItemResource;

import java.util.Set;

public interface RefinedStoragePriorityAccess {
    void ech$setRequiredItems(Set<ItemResource> required);

    boolean ech$isRequiredItem(ItemResource resource);
}
