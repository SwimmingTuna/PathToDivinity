package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.infernal_forge;
import com.github.L_Ender.cataclysm.items.void_forge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = void_forge.class, remap = false)
public class VoidForgeMixin {

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Tier;Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(intValue = 8)
    )
    private static int modifyAttackDamage(int original) {
        return 18;
    }

    @ModifyArg(
            method = "spawnFangs",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Void_Rune_Entity;<init>(Lnet/minecraft/world/level/Level;DDDFIFLnet/minecraft/world/entity/LivingEntity;)V"),
            index = 6,
            remap = false
    )
    private float multiplyVoidRuneDamage(float damage) {
        return damage * 2.0F;
    }
}