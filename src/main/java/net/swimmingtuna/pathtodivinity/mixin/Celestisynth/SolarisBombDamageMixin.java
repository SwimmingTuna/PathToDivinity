package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Constant;

import com.aqutheseal.celestisynth.common.entity.projectile.SolarisBomb;

@Mixin(value = SolarisBomb.class, remap = false)
public class SolarisBombDamageMixin {

    /**
     * Modifies the direct hit damage from 5.0F to 25.0F (5x multiplier)
     */
    @ModifyConstant(method = "onHitEntity", constant = @Constant(floatValue = 5.0F))
    private float increaseDirectHitDamage(float originalDamage) {
        return originalDamage * 12;
    }

    @ModifyConstant(method = "explodeFire", constant = @Constant(floatValue = 2.0F))
    private float increaseExplosionDamage(float originalDamage) {
        return originalDamage * 12;
    }
}