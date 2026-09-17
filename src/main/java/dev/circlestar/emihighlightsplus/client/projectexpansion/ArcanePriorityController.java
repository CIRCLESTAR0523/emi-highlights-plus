package dev.circlestar.emihighlightsplus.client.projectexpansion;

import cool.furry.mc.neoforge.projectexpansion.gui.container.ContainerArcaneTransmutationTablet;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import dev.emi.emi.bom.BoM;
import dev.emi.emi.runtime.EmiFavorites;
import moze_intel.projecte.api.ItemInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashSet;
import java.util.Set;

public final class ArcanePriorityController {
    private ArcanePriorityController() {
    }

    public static void initialize() {
        ArcanePrioritySession.register(inventory -> {
            var player = Minecraft.getInstance().player;
            return player != null
                    && player.containerMenu instanceof ContainerArcaneTransmutationTablet menu
                    && menu.transmutationInventory == inventory;
        });
        NeoForge.EVENT_BUS.addListener(ArcanePriorityController::onTick);
    }

    private static void onTick(ClientTickEvent.Post event) {
        if (!(Minecraft.getInstance().screen instanceof AbstractContainerScreen<?> screen)
                || !(screen.getMenu() instanceof ContainerArcaneTransmutationTablet menu)) {
            return;
        }
        // Client tick, not rendering or click handling. Only the small EMI demand set is inspected.
        Set<ItemInfo> required = new HashSet<>();
        if (ClientConfig.ENABLED.get() && ClientConfig.PROJECT_EXPANSION_ENABLED.get()
                && ClientConfig.REQUIRED_MATERIAL_PRIORITY.get() && BoM.craftingMode && BoM.tree != null) {
            for (var favorite : EmiFavorites.syntheticFavorites) {
                for (var stack : favorite.getEmiStacks()) {
                    var item = stack.getItemStack();
                    if (!item.isEmpty()) {
                        required.add(ItemInfo.fromStack(item));
                    }
                }
            }
        }
        ((ArcanePriorityAccess) menu.transmutationInventory).ech$updatePriority(Set.copyOf(required));
    }
}
