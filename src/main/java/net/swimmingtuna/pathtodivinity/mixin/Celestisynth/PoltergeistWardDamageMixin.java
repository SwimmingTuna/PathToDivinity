package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.common.entity.skillcast.SkillCastPoltergeistWard;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = SkillCastPoltergeistWard.class, remap = false)
public class PoltergeistWardDamageMixin {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean redirectHurtDamage(LivingEntity target, DamageSource damageSource, float damage) {
        return target.hurt(damageSource, 12.0F);
    }
}