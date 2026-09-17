package dev.circlestar.emihighlightsplus.client.projectexpansion;

import cool.furry.mc.neoforge.projectexpansion.gui.container.ContainerArcaneTransmutationTablet;
import cool.furry.mc.neoforge.projectexpansion.registries.MenuTypes;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.handler.EmiRecipeHandler;
import dev.emi.emi.registry.EmiRecipeFiller;
import net.minecraft.world.inventory.MenuType;

import java.util.List;

public final class ProjectExpansionEmiCompat {
    private ProjectExpansionEmiCompat() {
    }

    public static void register(EmiRegistry registry) {
        MenuType<ContainerArcaneTransmutationTablet> menuType = MenuTypes.ARCANE_TRANSMUTATION_TABLET.get();
        ArcaneTransmutationTabletInventoryHandler handler = new ArcaneTransmutationTabletInventoryHandler();
        registry.addRecipeHandler(menuType, handler);
        prioritizeInventoryHandler(menuType, handler);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void prioritizeInventoryHandler(MenuType<?> menuType, EmiRecipeHandler<?> handler) {
        List handlers = EmiRecipeFiller.handlers.get(menuType);
        if (handlers != null && handlers.remove(handler)) {
            handlers.add(0, handler);
        }
    }
}
