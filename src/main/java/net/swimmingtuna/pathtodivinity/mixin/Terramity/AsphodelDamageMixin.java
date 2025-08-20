package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import net.mcreator.terramity.procedures.AsphodelRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = AsphodelRightclickedProcedure.class, remap = false)
public class AsphodelDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(doubleValue = 0.8D)
    )
    private static double modifyDamage(double damage) {
        return damage * 2.5D;
    }
}
