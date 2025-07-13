package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.procedures.TormentedWrathOnPlayerStoppedUsingProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = TormentedWrathOnPlayerStoppedUsingProcedure.class, remap = false)
public class TormentedWrathDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 6.0F)
    )
    private static float modifyDamage(float damage) {
        return damage * 30.0F;
    }
}
