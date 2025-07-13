package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.procedures.VoidseekerHandTickProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = VoidseekerHandTickProcedure.class, remap = false)
public class VoidseekerArrowDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 50.0F)
    )
    private static float modifyArrowDamage50(float damage) {
        return damage * 3.0F;
    }

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 45.0F)
    )
    private static float modifyArrowDamage45(float damage) {
        return damage * 3.0F;
    }
}