package dev.circlestar.emicontainerhighlight.client;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public interface RequiredIngredientProvider {
    Snapshot snapshot(AbstractContainerScreen<?> screen);

    interface Snapshot {
        boolean isActive();

        boolean isRequired(ItemStack stack);

        boolean wasSuppressedByEmi(Slot slot);

        int requiredStackCount();

        int suppressedSlotCount();
    }
}
