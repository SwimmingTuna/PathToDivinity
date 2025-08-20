package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.procedures.ValamonSickleRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ValamonSickleRightclickedProcedure.class, remap = false)
public class ValamonSickleDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 3.0F)
    )
    private static float modifyBoneWaveDamage(float damage) {
        return damage * 2.5F;
    }
}
