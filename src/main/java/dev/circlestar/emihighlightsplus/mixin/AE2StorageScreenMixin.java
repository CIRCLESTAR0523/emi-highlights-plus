package dev.circlestar.emihighlightsplus.mixin;

import appeng.client.gui.me.common.Repo;
import dev.circlestar.emihighlightsplus.client.ae2.AE2StorageScreenAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(targets = "appeng.client.gui.me.common.MEStorageScreen", remap = false)
abstract class AE2StorageScreenMixin implements AE2StorageScreenAccess {
    @Override
    @Accessor("repo")
    public abstract Repo ech$getRepo();
}
