package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.infernal_forge;
import com.github.L_Ender.cataclysm.items.void_forge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = void_forge.class, remap = false)
public class VoidForgeMixin { //MODIFY

    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 8))
    private static int modifyAttackDamage(int original) {
        return 18;
    }

    @ModifyArg(
            method = "spawnFangs",
            at = @At(value = "NEW", target = "com/github/L_Ender/cataclysm/entity/projectile/Void_Rune_Entity"),
            index = 6
    )
    private float multiplyVoidRuneDamage(float damage) {
        return damage * 2.0F;
    }

}
