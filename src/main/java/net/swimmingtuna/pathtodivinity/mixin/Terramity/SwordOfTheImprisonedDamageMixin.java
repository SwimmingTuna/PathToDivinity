package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import net.mcreator.terramity.procedures.ImprisonedProjectileProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = ImprisonedProjectileProcedure.class, remap = false)
public class SwordOfTheImprisonedDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V",
            constant = @Constant(floatValue = 22.0F)
    )
    private static float modifyDamage(float damage) {
        return damage * 5.0F;
    }
}