package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.The_Annihilator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = The_Annihilator.class, remap = false)
public class TheAnnihilatorMixin {

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 6.5)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 20.0;
    }

    @ModifyConstant(
            method = "yall(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)V",
            constant = @Constant(floatValue = 2.0F)
    )
    private float modifyAreaDamage(float damage) {
        return damage * 4.0F;
    }
}