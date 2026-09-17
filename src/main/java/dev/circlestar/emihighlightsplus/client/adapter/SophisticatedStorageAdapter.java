package dev.circlestar.emihighlightsplus.client.adapter;

import dev.circlestar.emihighlightsplus.EmiHighlightsPlus;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedstorage.client.gui.StorageScreen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SophisticatedStorageAdapter implements ContainerHighlightAdapter {
    private static final Method IS_STORAGE_SLOT_RENDER_REPLACED = findRenderReplacementMethod();
    private boolean reflectionErrorLogged;

    @Override
    public boolean supports(AbstractContainerScreen<?> screen) {
        return ClientConfig.SOPHISTICATED_STORAGE_ENABLED.get() && screen instanceof StorageScreen;
    }

    @Override
    public Stream<HighlightTarget> getVisibleTargets(AbstractContainerScreen<?> screen) {
        if (!supports(screen) || !(screen instanceof StorageScreenBase<?> storageScreen)) {
            return Stream.empty();
        }

        StorageContainerMenuBase<?> menu = storageScreen.getMenu();
        return IntStream.range(0, menu.getNumberOfStorageInventorySlots())
                .mapToObj(menu::getSlot)
                .filter(slot -> isVisibleStorageSlot(storageScreen, menu, slot))
                .map(slot -> new HighlightTarget(slot, slot.getItem(), slot.x, slot.y));
    }

    @Override
    public String id() {
        return "sophisticated_storage";
    }

    private boolean isVisibleStorageSlot(
            StorageScreenBase<?> screen,
            StorageContainerMenuBase<?> menu,
            Slot slot
    ) {
        if (!menu.isStorageInventorySlot(slot.index)
                || !slot.isActive()
                || slot.getItem().isEmpty()
                || menu.isInaccessibleSlot(slot.index)
                || slot.x == StorageScreenBase.DISABLED_SLOT_X_POS
                || slot.x < 0
                || slot.y < 0
                || !screen.getStackFilter().test(slot.getItem())
                || isRenderReplaced(screen, slot.index)) {
            return false;
        }

        int absoluteX = screen.getLeftX() + slot.x;
        int absoluteY = screen.getTopY() + slot.y;
        return absoluteX + 16 >= 0
                && absoluteX < screen.width
                && absoluteY + 16 >= 0
                && absoluteY < screen.height;
    }

    private boolean isRenderReplaced(StorageScreenBase<?> screen, int slotIndex) {
        if (IS_STORAGE_SLOT_RENDER_REPLACED == null) {
            return false;
        }
        try {
            return (boolean) IS_STORAGE_SLOT_RENDER_REPLACED.invoke(screen, slotIndex);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            if (!reflectionErrorLogged) {
                reflectionErrorLogged = true;
                EmiHighlightsPlus.LOGGER.warn(
                        "Unable to query Sophisticated Storage slot render replacement state",
                        exception
                );
            }
            return false;
        }
    }

    private static Method findRenderReplacementMethod() {
        try {
            Method method = StorageScreenBase.class.getDeclaredMethod("isStorageSlotRenderReplaced", int.class);
            if (!method.trySetAccessible()) {
                EmiHighlightsPlus.LOGGER.warn(
                        "Sophisticated Storage render replacement method is not accessible; upgrade replacement filtering is unavailable"
                );
                return null;
            }
            return method;
        } catch (NoSuchMethodException exception) {
            EmiHighlightsPlus.LOGGER.warn(
                    "Sophisticated Storage render replacement method was not found; upgrade replacement filtering is unavailable",
                    exception
            );
            return null;
        }
    }
}
