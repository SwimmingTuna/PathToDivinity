package net.swimmingtuna.pathtodivinity.mixin.BornInChaos;

import net.mcreator.borninchaosv.procedures.SoulStratificationKazhdyiTikVoVriemiaEffiektaProcedure;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SoulStratificationKazhdyiTikVoVriemiaEffiektaProcedure.class)
public class SoulStratificationEffectMixin {

    @Redirect(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private static boolean redirectDamage(Entity entity, DamageSource damageSource, float originalDamage) {
        if (entity instanceof LivingEntity livingEntity) {
            float maxHealth = livingEntity.getMaxHealth();
            float maxDamage = maxHealth * 0.04f;
            float finalDamage = Math.min(originalDamage, maxDamage);
            return entity.hurt(damageSource, finalDamage);
        }
        return entity.hurt(damageSource, originalDamage);
    }
}
