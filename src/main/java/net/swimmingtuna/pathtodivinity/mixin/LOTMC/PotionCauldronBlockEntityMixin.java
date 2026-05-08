package net.swimmingtuna.pathtodivinity.mixin.LOTMC;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.swimmingtuna.lotm.blocks.stations.potion_cauldron.block.PotionCauldronBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionCauldronBlockEntity.class)
public class PotionCauldronBlockEntityMixin {

    @Unique
    private int lotm$tickCounter = 0;

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void onTick(Level level, BlockPos pos, BlockState state, CallbackInfo ci) {
        lotm$tickCounter++;

        //if (lotm$tickCounter >= 20) {
        //    lotm$tickCounter = 0;
        //    ((PotionCauldronBlockEntity)(Object)this).debugPrintRecipes();
        //}
    }
}