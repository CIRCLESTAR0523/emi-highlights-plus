package dev.circlestar.emihighlightsplus.client.projectexpansion;

import cool.furry.mc.neoforge.projectexpansion.gui.container.ContainerArcaneTransmutationTablet;
import cool.furry.mc.neoforge.projectexpansion.gui.container.slots.PXCraftingSlot;
import cool.furry.mc.neoforge.projectexpansion.gui.container.slots.PXResultSlot;
import cool.furry.mc.neoforge.projectexpansion.net.packets.to_server.PacketArcaneTransmutationTabletRecipeTransfer;
import cool.furry.mc.neoforge.projectexpansion.util.Util;
import dev.emi.emi.api.recipe.EmiPlayerInventory;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.recipe.handler.EmiCraftContext;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import moze_intel.projecte.api.ItemInfo;
import moze_intel.projecte.api.proxy.IEMCProxy;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Supplies the tablet's logical inventory to EMI without treating its paged output slots as stored items.
 * Recipe transfer uses Project Expansion's own packet and server-side transfer implementation.
 */
public final class ArcaneTransmutationTabletInventoryHandler
        implements StandardRecipeHandler<ContainerArcaneTransmutationTablet> {
    private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);
    private ContainerArcaneTransmutationTablet cachedMenu;
    private int cachedStateId = Integer.MIN_VALUE;
    private BigInteger cachedAvailableEmc;
    private int cachedKnowledgeSize = -1;
    private boolean cachedFullKnowledge;
    private EmiPlayerInventory cachedInventory;

    @Override
    public List<Slot> getInputSources(ContainerArcaneTransmutationTablet menu) {
        return menu.slots.stream()
                .filter(slot -> slot.container instanceof Inventory || slot instanceof PXCraftingSlot)
                .toList();
    }

    @Override
    public List<Slot> getCraftingSlots(ContainerArcaneTransmutationTablet menu) {
        return menu.slots.stream()
                .filter(PXCraftingSlot.class::isInstance)
                .toList();
    }

    @Override
    public Slot getOutputSlot(ContainerArcaneTransmutationTablet menu) {
        return menu.slots.stream()
                .filter(PXResultSlot.class::isInstance)
                .findFirst()
                .orElse(null);
    }

    @Override
    public EmiPlayerInventory getInventory(AbstractContainerScreen<ContainerArcaneTransmutationTablet> screen) {
        ContainerArcaneTransmutationTablet menu = screen.getMenu();
        BigInteger availableEmc = menu.transmutationInventory.getAvailableEmc();
        int stateId = menu.getStateId();
        int knowledgeSize = menu.getProvider().getKnowledge().size();
        boolean fullKnowledge = menu.getProvider().hasFullKnowledge();
        if (menu == cachedMenu
                && stateId == cachedStateId
                && availableEmc.equals(cachedAvailableEmc)
                && knowledgeSize == cachedKnowledgeSize
                && fullKnowledge == cachedFullKnowledge
                && cachedInventory != null) {
            return cachedInventory;
        }

        List<EmiStack> inventory = new ArrayList<>();

        for (Slot slot : getInputSources(menu)) {
            if (slot.mayPickup(menu.getPlayer())) {
                inventory.add(EmiStack.of(slot.getItem()));
            }
        }

        EmiPlayerInventory playerInventory = new EmiPlayerInventory(inventory);
        playerInventory.inventory = new EmcBackedInventoryMap(playerInventory.inventory, menu, availableEmc);
        cachedMenu = menu;
        cachedStateId = stateId;
        cachedAvailableEmc = availableEmc;
        cachedKnowledgeSize = knowledgeSize;
        cachedFullKnowledge = fullKnowledge;
        cachedInventory = playerInventory;
        return playerInventory;
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        if (recipe.getCategory() != VanillaEmiRecipeCategories.CRAFTING || recipe.getInputs().size() > 9) {
            return false;
        }
        return !(recipe instanceof EmiCraftingRecipe craftingRecipe) || craftingRecipe.canFit(3, 3);
    }

    @Override
    public boolean canCraft(
            EmiRecipe recipe,
            EmiCraftContext<ContainerArcaneTransmutationTablet> context
    ) {
        // EMI evaluates this for every recipe while rebuilding its craftable list. Running the
        // packet-oriented resolver here multiplies tag alternatives by every inventory slot and
        // blocks the render thread in large packs. Keep the bulk check on EMI's indexed inventory;
        // resolve the exact Project Expansion transfer only after the user invokes craft().
        return supportsRecipe(recipe) && context.getInventory().canCraft(recipe);
    }

    @Override
    public boolean craft(
            EmiRecipe recipe,
            EmiCraftContext<ContainerArcaneTransmutationTablet> context
    ) {
        if (!supportsRecipe(recipe)) {
            return false;
        }

        List<List<ItemStack>> transferRecipe = prepareTransfer(context.getScreenHandler(), recipe);
        if (transferRecipe == null) {
            return false;
        }

        PacketDistributor.sendToServer(new PacketArcaneTransmutationTabletRecipeTransfer(
                transferRecipe,
                false
        ));
        return true;
    }

    /**
     * Resolves one concrete candidate per crafting slot before sending Project Expansion's packet.
     * Project Expansion's transfer-all mode repeats its server-side recipe transfer 64 times. EMI
     * Shift-transfer therefore deliberately places one craft only: this keeps the physical-item
     * priority while avoiding a large synchronous batch of transmutation and recipe recalculation.
     */
    private List<List<ItemStack>> prepareTransfer(ContainerArcaneTransmutationTablet menu, EmiRecipe recipe) {
        List<ItemStack> availableItems = new ArrayList<>();
        Inventory playerInventory = menu.getPlayer().getInventory();
        for (int index = 0; index < playerInventory.getContainerSize(); index++) {
            availableItems.add(playerInventory.getItem(index).copy());
        }
        for (Slot craftingSlot : getCraftingSlots(menu)) {
            availableItems.add(craftingSlot.getItem().copy());
        }

        BigInteger remainingEmc = menu.getProvider().getEmc();
        List<List<ItemStack>> transferRecipe = new ArrayList<>(9);
        for (EmiIngredient ingredient : recipe.getInputs()) {
            if (ingredient.isEmpty()) {
                transferRecipe.add(List.of(ItemStack.EMPTY));
                continue;
            }

            ItemStack selected = takeFromInventory(menu, availableItems, ingredient);
            if (!selected.isEmpty()) {
                transferRecipe.add(List.of(selected));
                continue;
            }

            long lowestEmc = Long.MAX_VALUE;
            ItemStack lowestEmcStack = ItemStack.EMPTY;
            for (EmiStack alternative : ingredient.getEmiStacks()) {
                ItemStack stack = Util.cleanStack(alternative.getItemStack());
                if (stack.isEmpty() || !menu.getProvider().hasKnowledge(stack)) {
                    continue;
                }
                long value = IEMCProxy.INSTANCE.getValue(stack);
                if (value > 0 && value < lowestEmc) {
                    lowestEmc = value;
                    lowestEmcStack = stack;
                }
            }
            if (lowestEmcStack.isEmpty()
                    || lowestEmc <= 0
                    || remainingEmc.compareTo(BigInteger.valueOf(lowestEmc)) < 0) {
                return null;
            }
            remainingEmc = remainingEmc.subtract(BigInteger.valueOf(lowestEmc));
            transferRecipe.add(List.of(lowestEmcStack));
        }
        while (transferRecipe.size() < 9) {
            transferRecipe.add(List.of(ItemStack.EMPTY));
        }
        return transferRecipe;
    }

    private ItemStack takeFromInventory(
            ContainerArcaneTransmutationTablet menu,
            List<ItemStack> availableItems,
            EmiIngredient ingredient
    ) {
        for (EmiStack emiAlternative : ingredient.getEmiStacks()) {
            ItemStack alternative = Util.cleanStack(emiAlternative.getItemStack());
            if (alternative.isEmpty()) {
                continue;
            }
            for (ItemStack available : availableItems) {
                if (!available.isEmpty() && Util.areStacksEqual(
                        menu.getPlayer().registryAccess(),
                        alternative,
                        Util.cleanStack(available)
                )) {
                    available.shrink(1);
                    return alternative;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * Resolves learned transmutation items only when EMI asks for a specific stack. Iterating every
     * learned item here makes large modpacks rebuild thousands of EMI stacks whenever the screen
     * refreshes, even though only a small number of stacks are visible or queried at a time.
     */
    private static final class EmcBackedInventoryMap extends AbstractMap<EmiStack, EmiStack> {
        private final Map<EmiStack, EmiStack> physicalInventory;
        private final ContainerArcaneTransmutationTablet menu;
        private final BigInteger availableEmc;
        private final Map<EmiStack, EmiStack> resolvedInventory = new HashMap<>();
        private final Set<EmiStack> unavailable = new HashSet<>();

        private EmcBackedInventoryMap(
                Map<EmiStack, EmiStack> physicalInventory,
                ContainerArcaneTransmutationTablet menu,
                BigInteger availableEmc
        ) {
            this.physicalInventory = physicalInventory;
            this.menu = menu;
            this.availableEmc = availableEmc;
        }

        @Override
        public EmiStack get(Object key) {
            EmiStack physical = physicalInventory.get(key);
            if (!(key instanceof EmiStack requested)) {
                return physical;
            }
            EmiStack resolved = resolvedInventory.get(requested);
            if (resolved != null) {
                return resolved;
            }
            if (unavailable.contains(requested)) {
                return physical;
            }

            ItemStack stack = Util.cleanStack(requested.getItemStack());
            if (stack.isEmpty()) {
                unavailable.add(requested);
                return null;
            }

            ItemInfo info = ItemInfo.fromStack(stack);
            if (!menu.getProvider().hasKnowledge(info)) {
                unavailable.add(requested);
                return physical;
            }

            long value = IEMCProxy.INSTANCE.getValue(info);
            if (value <= 0 || availableEmc.signum() <= 0) {
                unavailable.add(requested);
                return physical;
            }

            BigInteger producible = availableEmc.divide(BigInteger.valueOf(value));
            if (producible.signum() <= 0) {
                unavailable.add(requested);
                return physical;
            }
            long physicalAmount = physical == null ? 0 : physical.getAmount();
            long virtualAmount = producible.min(LONG_MAX).longValue();
            long totalAmount = physicalAmount > Long.MAX_VALUE - virtualAmount
                    ? Long.MAX_VALUE
                    : physicalAmount + virtualAmount;
            resolved = EmiStack.of(stack, totalAmount);
            resolvedInventory.put(requested, resolved);
            return resolved;
        }

        @Override
        public boolean containsKey(Object key) {
            return get(key) != null;
        }

        @Override
        public Set<Entry<EmiStack, EmiStack>> entrySet() {
            // Learned items are intentionally query-only so EMI never enumerates the full knowledge set.
            return physicalInventory.entrySet();
        }
    }
}
