package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.The_Immolator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = The_Immolator.class, remap = false)
public class TheImmolatorMixin {

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 6.5)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 28.0;
    }


}