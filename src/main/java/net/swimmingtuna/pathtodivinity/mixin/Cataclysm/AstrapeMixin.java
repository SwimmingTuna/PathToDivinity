package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.Astrape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Astrape.class, remap = true)
public class AstrapeMixin {

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 9.5)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 25.0;
    }

    @ModifyArg(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Lightning_Spear_Entity;<init>(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/Level;F)V"),
            index = 3,
            remap = true
    )
    private float multiplyLightningDamage(float damage) {
        return damage * 7.0F;
    }

    @ModifyArg(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Lightning_Spear_Entity;setAreaDamage(F)V"),
            index = 0,
            remap = true
    )
    private float multiplyAreaDamage(float areaDamage) {
        return areaDamage * 7.0F;
    }
}