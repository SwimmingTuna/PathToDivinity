package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.common.entity.projectile.FrostboundShard;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FrostboundShard.class, remap = false)
public class FrostboundShardMixin {

    @Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean redirectHurtDamage(LivingEntity target, DamageSource damageSource, float damage) {
        return target.hurt(damageSource, 13.0F);
    }
}