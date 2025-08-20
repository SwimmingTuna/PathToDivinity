package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.procedures.GutWhipRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GutWhipRightclickedProcedure.class, remap = false)
public class GutWhipDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 1.0F)
    )
    private static float modifyGutWhipDamage(float damage) {
        return damage * 8.0F;
    }
}