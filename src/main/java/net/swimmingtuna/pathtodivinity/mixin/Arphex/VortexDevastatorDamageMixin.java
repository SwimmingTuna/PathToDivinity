package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.procedures.VortexDevastatorEntitySwingsItemProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = VortexDevastatorEntitySwingsItemProcedure.class, remap = false)
public class VortexDevastatorDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 5.0F)
    )
    private static float modifyDamage(float damage) {
        return damage * 10.0F;
    }
}
