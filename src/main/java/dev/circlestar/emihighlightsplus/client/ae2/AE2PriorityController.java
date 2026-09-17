package dev.circlestar.emihighlightsplus.client.ae2;

import appeng.api.stacks.AEItemKey;
import appeng.client.gui.me.common.MEStorageScreen;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import dev.emi.emi.bom.BoM;
import dev.emi.emi.runtime.EmiFavorites;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashSet;
import java.util.Set;

public final class AE2PriorityController {
    private AE2PriorityController() {
    }

    public static void initialize() {
        NeoForge.EVENT_BUS.addListener(AE2PriorityController::onTick);
    }

    private static void onTick(ClientTickEvent.Post event) {
        if (!(Minecraft.getInstance().screen instanceof MEStorageScreen<?> screen)) {
            return;
        }
        Set<AEItemKey> required = new HashSet<>();
        if (ClientConfig.ENABLED.get() && ClientConfig.AE2_ENABLED.get()
                && ClientConfig.REQUIRED_MATERIAL_PRIORITY.get() && BoM.craftingMode && BoM.tree != null) {
            for (var favorite : EmiFavorites.syntheticFavorites) {
                for (var stack : favorite.getEmiStacks()) {
                    var itemStack = stack.getItemStack();
                    if (!itemStack.isEmpty()) {
                        AEItemKey key = AEItemKey.of(itemStack);
                        if (key != null) {
                            required.add(key);
                        }
                    }
                }
            }
        }
        var repo = ((AE2StorageScreenAccess) screen).ech$getRepo();
        ((AE2PriorityAccess) repo).ech$setRequiredItems(Set.copyOf(required));
    }
}
