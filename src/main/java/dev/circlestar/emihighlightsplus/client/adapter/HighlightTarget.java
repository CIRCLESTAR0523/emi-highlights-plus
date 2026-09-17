package dev.circlestar.emihighlightsplus.client.adapter;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public record HighlightTarget(Slot slot, ItemStack stack, int x, int y) {
}
