package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.Cursed_bow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = Cursed_bow.class)
public class CursedBowMixin {

    // Modify the base damage for homing arrows (Phantom_Arrow_Entity)
    @ModifyArg(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Phantom_Arrow_Entity;setBaseDamage(D)V"),
            index = 0
    )
    private double triplePhantomArrowDamage(double damage) {
        return damage * 3.0;
    }

    @ModifyArg(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;setBaseDamage(D)V"),
            index = 0
    )
    private double tripleRegularArrowDamage(double damage) {
        return damage * 3.0;
    }
}