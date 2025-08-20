package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import net.mcreator.terramity.procedures.AdvancedAutomaticRifleRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = AdvancedAutomaticRifleRightclickedProcedure.class, remap = false)
public class AdvancedAutomaticRifleDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(doubleValue = 0.45D)
    )
    private static double modifyDamage(double damage) {
        return damage * 0.7D;
    }
}
