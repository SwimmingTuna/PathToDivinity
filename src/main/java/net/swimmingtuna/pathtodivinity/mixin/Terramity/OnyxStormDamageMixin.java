package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import net.mcreator.terramity.procedures.OnyxStormRightclickedProcedure;
import net.mcreator.terramity.procedures.SuppressedAdvancedPistolRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = OnyxStormRightclickedProcedure.class, remap = false)
public class OnyxStormDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(doubleValue = 20.0D)
    )
    private static double modifyDamage(double damage) {
        return damage * 0.6D;
    }
}
