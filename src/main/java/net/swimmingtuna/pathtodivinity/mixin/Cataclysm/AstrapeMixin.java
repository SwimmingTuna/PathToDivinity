package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.Astrape;
import com.github.L_Ender.cataclysm.items.Gauntlet_of_Guard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Astrape.class, remap = false)
public class AstrapeMixin { //ATTRIBUTE

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 9.5)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 25.0;
    }

    @ModifyArg(
            method = "releaseUsing",
            at = @At(value = "NEW", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Lightning_Spear_Entity;"),
            index = 3
    )
    private float multiplyLightningDamage(float damage) {
        return damage * 7.0F;
    }

    @ModifyArg(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Lightning_Spear_Entity;setAreaDamage(F)V"),
            index = 0
    )
    private float multiplyAreaDamage(float areaDamage) {
        return areaDamage * 7.0F;
    }
}
