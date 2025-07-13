package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.Astrape;
import com.github.L_Ender.cataclysm.items.Ceraunus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Ceraunus.class, remap = false)
public class CeraunusMixin { //ATTRIBUTE

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 15.0)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 30.0;
    }


    @ModifyArg(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Player_Ceraunus_Entity;setBaseDamage(D)V"),
            index = 0
    )
    private double multiplyThrownDamage(double damage) {
        return damage * 3.0;
    }
}
