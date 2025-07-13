package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.procedures.StaffOfVitalityRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = StaffOfVitalityRightclickedProcedure.class, remap = false)
public class StaffOfVitalityCooldownMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(intValue = 300)
    )
    private static int modifyCooldown(int ticks) {
        return 60;
    }
}