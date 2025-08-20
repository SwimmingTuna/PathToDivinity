package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.procedures.HeartProjProjectileHitsLivingEntityProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = HeartProjProjectileHitsLivingEntityProcedure.class, remap = false)
public class HeartProjDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/entity/Entity;)V",
            constant = @Constant(intValue = 1)
    )
    private static int modifyHarmAmplifier(int amplifier) {
        return 0;
    }
}