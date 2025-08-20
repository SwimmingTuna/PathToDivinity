package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.procedures.GrossbowRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GrossbowRightclickedProcedure.class, remap = false)
public class GrossbowDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 4.0F)
    )
    private static float modifyGrossbowDamage(float damage) {
        return damage * 1.5F;
    }
}