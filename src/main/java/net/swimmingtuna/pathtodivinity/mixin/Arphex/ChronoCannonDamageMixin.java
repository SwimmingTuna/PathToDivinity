package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.procedures.ChronoCannonOnPlayerStoppedUsingProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ChronoCannonOnPlayerStoppedUsingProcedure.class, remap = false)
public class ChronoCannonDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 5.0F)
    )
    private static float modifyDamage(float damage) {
        return damage * 40.0F;
    }
}
