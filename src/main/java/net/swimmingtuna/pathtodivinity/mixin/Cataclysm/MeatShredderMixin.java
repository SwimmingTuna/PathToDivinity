package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.Meat_Shredder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Meat_Shredder.class, remap = true)
public class MeatShredderMixin { //ATTRIBUTE

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 7.5)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 8.0;
    }

    @ModifyConstant(
            method = "onUseTick",
            constant = @Constant(floatValue = 8.5F)
    )
    private float modifyUseDamage(float divisor) {
        return divisor / 0.6F;
    }
}