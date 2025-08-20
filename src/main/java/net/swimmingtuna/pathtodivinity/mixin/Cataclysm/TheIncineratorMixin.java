package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.The_Incinerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = The_Incinerator.class, remap = true)
public class TheIncineratorMixin {

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 13.0)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 12.0;
    }
}