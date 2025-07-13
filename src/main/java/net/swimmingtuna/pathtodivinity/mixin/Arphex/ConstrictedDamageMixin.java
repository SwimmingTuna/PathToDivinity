package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.procedures.ConstrictedOnEffectActiveTickProcedure;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ConstrictedOnEffectActiveTickProcedure.class)
public class ConstrictedDamageMixin {

    @Redirect(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z")
    )
    private static boolean redirectHurt(Entity entity, net.minecraft.world.damagesource.DamageSource damageSource, float damage) {
        float modifiedDamage = damage;
        if (entity instanceof net.minecraft.world.entity.player.Player) {
            modifiedDamage = damage * 3.0f;
        }
        else if (entity instanceof net.minecraft.world.entity.LivingEntity) {
            modifiedDamage = damage * 5.0f;
        }

        return entity.hurt(damageSource, modifiedDamage);
    }
}
