package dev.circlestar.emihighlightsplus.client.refinedstorage;

import com.refinedmods.refinedstorage.common.grid.screen.AbstractGridScreen;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import dev.emi.emi.bom.BoM;
import dev.emi.emi.runtime.EmiFavorites;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashSet;
import java.util.Set;

public final class RefinedStoragePriorityController {
    private RefinedStoragePriorityController() {
    }

    public static void initialize() {
        NeoForge.EVENT_BUS.addListener(RefinedStoragePriorityController::onTick);
    }

    private static void onTick(ClientTickEvent.Post event) {
        if (!(Minecraft.getInstance().screen instanceof AbstractGridScreen<?> screen)) {
            return;
        }
        Set<ItemResource> required = new HashSet<>();
        if (ClientConfig.ENABLED.get() && ClientConfig.REFINED_STORAGE_ENABLED.get()
                && ClientConfig.REQUIRED_MATERIAL_PRIORITY.get() && BoM.craftingMode && BoM.tree != null) {
            for (var favorite : EmiFavorites.syntheticFavorites) {
                for (var stack : favorite.getEmiStacks()) {
                    var itemStack = stack.getItemStack();
                    if (!itemStack.isEmpty()) {
                        required.add(ItemResource.ofItemStack(itemStack));
                    }
                }
            }
        }
        var repository = screen.getMenu().getRepository();
        ((RefinedStoragePriorityAccess) repository).ech$setRequiredItems(Set.copyOf(required));
    }
}
