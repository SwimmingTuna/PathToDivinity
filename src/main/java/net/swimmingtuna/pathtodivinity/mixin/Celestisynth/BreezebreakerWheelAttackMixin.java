package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.api.item.CSWeaponUtil;
import com.aqutheseal.celestisynth.common.attack.breezebreaker.BreezebreakerWheelAttack;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = BreezebreakerWheelAttack.class, remap = false)
public class BreezebreakerWheelAttackMixin {

    @Inject(method = "tickAttack", at = @At("HEAD"), cancellable = true)
    public void injectTickAttack(CallbackInfo ci) {
        BreezebreakerWheelAttack self = (BreezebreakerWheelAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();

        if (self.getTimerProgress() == 10) {
            double range = 7.5;
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(range, range, 3.0).move(0.0, 1.0, 0.0));

            for (Entity entityBatch : entities) {
                if (entityBatch instanceof LivingEntity target) {
                    if (target != player && target.isAlive() && !player.isAlliedTo(target)) {
                        self.attributeDependentAttack(player, target, self.getStack(), 13.0F, AttackHurtTypes.REGULAR);
                        target.addEffect(CSWeaponUtil.nonVisiblePotionEffect(MobEffects.WEAKNESS, 40, 1));
                        self.sendExpandingParticles(level, ParticleTypes.POOF, target.blockPosition().above(), 45, 0.0F);
                    }
                }
            }

            CSEffectEntity.createInstance(player, null, CSVisualTypes.BREEZEBREAKER_WHEEL.get(), 0.0, -1.0, 0.0);
            CSEffectEntity.createInstance(player, null, CSVisualTypes.BREEZEBREAKER_WHEEL_IMPACT.get(), self.calculateXLook(player) * 3.0, 1.5 + self.calculateYLook(player) * 3.0, self.calculateZLook(player) * 3.0);
            player.playSound(CSSoundEvents.FIRE_SHOOT.get(), 1.0F, 1.0F);
            player.playSound(CSSoundEvents.AIR_SWING.get(), 1.0F, 1.0F);
            player.playSound(CSSoundEvents.WIND_STRIKE.get());
            self.sendExpandingParticles(level, ParticleTypes.END_ROD, player.blockPosition().above(), 75, 0.0F);
        }

        ci.cancel();
    }
}