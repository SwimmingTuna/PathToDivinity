package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.procedures.JudgementHandProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = JudgementHandProcedure.class, remap = false)
public class JudgementHandDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 4.0F)
    )
    private static float modifyDamage(float damage) {
        return damage * 18.0F;
    }
}
