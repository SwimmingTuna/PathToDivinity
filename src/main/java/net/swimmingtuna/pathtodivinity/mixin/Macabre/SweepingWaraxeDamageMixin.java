package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.procedures.ValamonAxeRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ValamonAxeRightclickedProcedure.class, remap = false)
public class SweepingWaraxeDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 5.0F),
            remap = false
    )
    private static float modifyBleedingDamage(float original) {
        return original * 2.5F;
    }
}