package dev.circlestar.emihighlightsplus.client.projectexpansion;

import dev.circlestar.emihighlightsplus.config.ClientConfig;
import dev.emi.emi.bom.BoM;
import dev.emi.emi.runtime.EmiFavorites;
import moze_intel.projecte.api.ItemInfo;
import moze_intel.projecte.gameObjs.container.TransmutationContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashSet;
import java.util.Set;

/** Required-material ordering for ProjectE's own Transmutation Table and Tablet. */
public final class ProjectETransmutationPriorityController {
    private ProjectETransmutationPriorityController() {
    }

    public static void initialize() {
        ArcanePrioritySession.register(inventory -> {
            var player = Minecraft.getInstance().player;
            return player != null
                    && player.containerMenu instanceof TransmutationContainer menu
                    && menu.transmutationInventory == inventory;
        });
        NeoForge.EVENT_BUS.addListener(ProjectETransmutationPriorityController::onTick);
    }

    private static void onTick(ClientTickEvent.Post event) {
        if (!(Minecraft.getInstance().screen instanceof AbstractContainerScreen<?> screen)
                || !(screen.getMenu() instanceof TransmutationContainer menu)) {
            return;
        }
        Set<ItemInfo> required = new HashSet<>();
        if (ClientConfig.ENABLED.get() && ClientConfig.PROJECT_E_ENABLED.get()
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
