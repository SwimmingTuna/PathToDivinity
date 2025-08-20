package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.Soul_Render;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = Soul_Render.class, remap = true)
public class SoulRenderMixin {

    @ModifyVariable(
            method = "releaseUsing(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)V",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/capabilities/RenderRushCapability$IRenderRushCapability;setdamage(F)V"),
            ordinal = 0
    )
    private float modifyRushDamage(float damage) {
        return damage * 5.0F;
    }
}