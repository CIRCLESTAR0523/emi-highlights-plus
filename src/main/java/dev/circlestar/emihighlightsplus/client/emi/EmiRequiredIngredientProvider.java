package dev.circlestar.emihighlightsplus.client.emi;

import dev.circlestar.emihighlightsplus.EmiHighlightsPlus;
import dev.circlestar.emihighlightsplus.client.RequiredIngredientProvider;
import dev.emi.emi.api.recipe.handler.EmiRecipeHandler;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.bom.BoM;
import dev.emi.emi.registry.EmiRecipeFiller;
import dev.emi.emi.runtime.EmiFavorite;
import dev.emi.emi.runtime.EmiFavorites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class EmiRequiredIngredientProvider implements RequiredIngredientProvider {
    private static final Snapshot INACTIVE = new EmiSnapshot(Set.of(), Set.of(), false);
    private boolean handlerErrorLogged;

    @Override
    public Snapshot snapshot(AbstractContainerScreen<?> screen) {
        if (!BoM.craftingMode || BoM.tree == null) {
            return INACTIVE;
        }

        Set<EmiStack> required = new HashSet<>();
        for (EmiFavorite.Synthetic favorite : EmiFavorites.syntheticFavorites) {
            required.addAll(favorite.getEmiStacks());
        }
        if (required.isEmpty()) {
            return INACTIVE;
        }

        return new EmiSnapshot(Set.copyOf(required), collectSuppressedSlots(screen), true);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Set<Slot> collectSuppressedSlots(AbstractContainerScreen<?> screen) {
        Set<Slot> suppressed = new HashSet<>();
        try {
            List<EmiRecipeHandler<?>> handlers = (List) EmiRecipeFiller.getAllHandlers((AbstractContainerScreen) screen);
            for (EmiRecipeHandler<?> handler : handlers) {
                if (handler instanceof StandardRecipeHandler standardHandler) {
                    suppressed.addAll(standardHandler.getInputSources(screen.getMenu()));
                    suppressed.addAll(standardHandler.getCraftingSlots(screen.getMenu()));
                }
            }
        } catch (Throwable throwable) {
            if (!handlerErrorLogged) {
                handlerErrorLogged = true;
                EmiHighlightsPlus.LOGGER.warn(
                        "Failed to inspect EMI recipe handler input slots; compatibility highlights are skipped to avoid double rendering",
                        throwable
                );
            }
            suppressed.clear();
        }
        return Set.copyOf(suppressed);
    }

    private record EmiSnapshot(Set<EmiStack> required, Set<Slot> suppressed, boolean active) implements Snapshot {
        @Override
        public boolean isActive() {
            return active;
        }

        @Override
        public boolean isRequired(ItemStack stack) {
            return !stack.isEmpty() && required.contains(EmiStack.of(stack));
        }

        @Override
        public boolean wasSuppressedByEmi(Slot slot) {
            return suppressed.contains(slot);
        }

        @Override
        public int requiredStackCount() {
            return required.size();
        }

        @Override
        public int suppressedSlotCount() {
            return suppressed.size();
        }
    }
}
