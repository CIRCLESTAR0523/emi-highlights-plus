package dev.circlestar.emihighlightsplus.mixin;

import dev.circlestar.emihighlightsplus.EmiHighlightsPlus;
import dev.circlestar.emihighlightsplus.client.priority.PriorityCandidateCache;
import dev.circlestar.emihighlightsplus.client.projectexpansion.ArcanePriorityAccess;
import dev.circlestar.emihighlightsplus.client.projectexpansion.ArcanePrioritySession;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import moze_intel.projecte.api.ItemInfo;
import moze_intel.projecte.api.proxy.IEMCProxy;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Pseudo
@Mixin(targets = "moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory", remap = false)
abstract class TransmutationPriorityMixin implements ArcanePriorityAccess {
    @Shadow @Final private IItemHandlerModifiable inputLocks;
    @Shadow @Final public IItemHandlerModifiable outputs;
    @Shadow private int searchPage;
    @Shadow public abstract long getAvailableEmcAsLong();
    @Shadow public abstract void updateClientTargets(boolean checkForEmcChange);

    @Unique private final PriorityCandidateCache<ItemInfo> ech$cache = new PriorityCandidateCache<>();
    @Unique private Set<ItemInfo> ech$required = Set.of();
    @Unique private boolean ech$replay;
    @Unique private boolean ech$knowledgeDirty;
    @Unique private long ech$seenRevision = Long.MIN_VALUE;
    @Unique private long ech$lastDebugNanos;

    @Unique
    private List<Object> ech$context() {
        ItemStack lock = inputLocks.getStackInSlot(8);
        return List.of(getAvailableEmcAsLong(), lock.isEmpty() ? "empty" : ItemInfo.fromStack(lock),
                ArcanePrioritySession.knowledgeRevision());
    }

    @Override
    public void ech$updatePriority(Set<ItemInfo> required) {
        if (!ArcanePrioritySession.matches(this)) {
            return;
        }
        boolean changed = !ech$required.equals(required);
        boolean synchronizedKnowledge = ech$seenRevision != ArcanePrioritySession.knowledgeRevision();
        if (!changed && !ech$knowledgeDirty && !synchronizedKnowledge) {
            return;
        }
        boolean wasActive = !ech$required.isEmpty();
        ech$required = required;
        ech$seenRevision = ArcanePrioritySession.knowledgeRevision();
        if (!changed && !wasActive) {
            ech$knowledgeDirty = false;
            return;
        }
        // Show the promoted items once on a semantic demand change, not on quantity updates.
        if (changed) {
            searchPage = 0;
        }
        ech$replay = !ech$knowledgeDirty && ech$cache.canReplay(ech$context());
        long started = System.nanoTime();
        try {
            updateClientTargets(false);
        } finally {
            if (ClientConfig.DEBUG.get() && started - ech$lastDebugNanos >= 1_000_000_000L) {
                EmiHighlightsPlus.LOGGER.info("Transmutation priority refresh: cached={}, required={}, durationMicros={}",
                        ech$replay, required.size(), (System.nanoTime() - started) / 1_000);
                ech$lastDebugNanos = started;
            }
            ech$replay = false;
        }
    }

    @Redirect(method = "updateClientTargets(J)V", at = @At(value = "INVOKE",
            target = "Ljava/util/stream/Stream;toList()Ljava/util/List;"), remap = false)
    private List<ItemInfo> ech$prioritizeCandidates(Stream<ItemInfo> stream) {
        if (!ArcanePrioritySession.matches(this)) {
            return stream.toList();
        }
        try (stream) {
            List<ItemInfo> result = ech$cache.resolve(ech$context(), ech$replay, ech$required, stream::toList);
            ech$knowledgeDirty = false;
            ech$seenRevision = ArcanePrioritySession.knowledgeRevision();
            return result;
        }
    }

    @Inject(method = {"itemLearned", "itemUnlearned"}, at = @At("HEAD"), remap = false)
    private void ech$invalidateChangedKnowledge(ItemInfo item, CallbackInfo ci) {
        ech$cache.invalidate();
        ech$knowledgeDirty = true;
    }

    @Inject(method = "getMaxDisplayedEmc", at = @At("HEAD"), cancellable = true, remap = false)
    private void ech$checkAllVisibleCosts(CallbackInfoReturnable<Long> cir) {
        if (ArcanePrioritySession.matches(this) && !ech$required.isEmpty()) {
            // Priority ordering means the first slot is no longer necessarily the most expensive.
            long max = 0;
            for (int slot = 0; slot < outputs.getSlots(); slot++) {
                max = Math.max(max, IEMCProxy.INSTANCE.getValue(outputs.getStackInSlot(slot)));
            }
            cir.setReturnValue(max);
        }
    }
}
