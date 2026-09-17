package dev.circlestar.emihighlightsplus.mixin;

import dev.circlestar.emihighlightsplus.client.projectexpansion.ArcanePrioritySession;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "moze_intel.projecte.network.packets.to_client.knowledge.KnowledgeSyncPKT", remap = false)
abstract class KnowledgePriorityInvalidationMixin {
    @Inject(method = "handle", at = @At("HEAD"), remap = false)
    private void ech$invalidateKnowledge(IPayloadContext context, CallbackInfo ci) {
        ArcanePrioritySession.invalidateKnowledge();
    }
}
