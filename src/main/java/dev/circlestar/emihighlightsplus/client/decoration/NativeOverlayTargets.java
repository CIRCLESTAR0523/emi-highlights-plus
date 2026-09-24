package dev.circlestar.emihighlightsplus.client.decoration;

import dev.circlestar.emihighlightsplus.client.RequiredIngredientProvider;
import dev.circlestar.emihighlightsplus.client.adapter.HighlightTarget;
import dev.emi.emi.screen.EmiScreenManager;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;

import java.util.stream.Stream;

final class NativeOverlayTargets {
    private NativeOverlayTargets() {
    }

    static Stream<HighlightTarget> collect(
            AbstractContainerScreen<?> screen,
            RequiredIngredientProvider.Snapshot snapshot
    ) {
        if (EmiScreenManager.search != null && EmiScreenManager.search.highlight) {
            return Stream.empty();
        }
        return screen.getMenu().slots.stream()
                .filter(slot -> slot.isActive()
                        && !(slot.container instanceof Inventory)
                        && !snapshot.wasSuppressedByEmi(slot)
                        && snapshot.isRequired(slot.getItem()))
                .map(slot -> new HighlightTarget(slot, slot.getItem(), slot.x, slot.y));
    }
}
