package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import net.mcreator.terramity.procedures.GuidingMoonlightProjectileProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = GuidingMoonlightProjectileProcedure.class, remap = false)
public class GuidingMoonlightDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V",
            constant = @Constant(floatValue = 15.0F)
    )
    private static float modifyDamage(float damage) {
        return damage * 1.6F;
    }
}