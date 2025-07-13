package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.entity.skillcast.SkillCastKeresSmash;
import com.aqutheseal.celestisynth.common.registry.CSMobEffects;
import com.aqutheseal.celestisynth.common.registry.CSParticleTypes;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Mixin(value = SkillCastKeresSmash.class, remap = false)
public class SkillCastKeresSmashAttackMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void injectTick(CallbackInfo ci) {
        SkillCastKeresSmash self = (SkillCastKeresSmash)(Object)this;

        UUID ownerUuid = self.getOwnerUUID();
        Player player = ownerUuid == null ? null : self.level().getPlayerByUUID(ownerUuid);
        if (player != null) {
            if (self.tickCount == 1) {
                CSEffectEntity.createInstance(player, (Entity)null, (CSVisualType)CSVisualTypes.KERES_PULSE.get(), 0.0, 0.35, 0.0);
                self.doSmashAttack(player, 3.0, 0.0, 2.5F);
            }

            if (self.tickCount == 6) {
                CSEffectEntity.createInstance(player, (Entity)null, (CSVisualType)CSVisualTypes.KERES_PULSE_1.get(), 0.0, -0.35, 0.0);
                self.doSmashAttack(player, 5.0, 1.5, 0.8F * 2.5F);
            }

            if (self.tickCount == 11) {
                CSEffectEntity.createInstance(player, (Entity)null, (CSVisualType)CSVisualTypes.KERES_PULSE_2.get(), 0.0, -1.45, 0.0);
                self.remove(Entity.RemovalReason.DISCARDED);
                self.doSmashAttack(player, 7.0, 3.0, 0.6F * 2.5F);
            }
        }

        ci.cancel();
    }

    @Inject(method = "doSmashAttack", at = @At("HEAD"), cancellable = true)
    public void injectDoSmashAttack(Player owner, double radius, double out, float multiplier, CallbackInfo ci) {
        SkillCastKeresSmash self = (SkillCastKeresSmash)(Object)this;

        self.playSound((SoundEvent)CSSoundEvents.STEP.get(), 0.4F, 0.5F);
        Predicate<LivingEntity> filter = (targetx) -> {
            return targetx != owner && self.distanceToSqr(targetx) >= out;
        };
        self.shakeScreensForNearbyPlayers(owner, self.level(), radius, 5, 5, 0.015F);

        for(int i = 0; i < 360; i += 2) {
            ParticleUtil.sendParticle(self.level(), (SimpleParticleType)CSParticleTypes.KERES_OMEN.get(), self.getX() + (double)Mth.sin((float)i) * radius, self.getY(), self.getZ() + (double)Mth.cos((float)i) * radius, 0.0, 0.5, 0.0);
        }

        List<LivingEntity> targets = self.level().getEntitiesOfClass(LivingEntity.class, (new AABB(-radius, 0.0, -radius, radius, 4.0, radius)).move(self.position())).stream().filter(filter).toList();

        for (LivingEntity target : targets) {
            target.addEffect(new MobEffectInstance((MobEffect) CSMobEffects.CURSEBANE.get(), 100, 1));
            self.initiateAbilityAttack(owner, target, (self.damage * multiplier) * 0.4f, AttackHurtTypes.RAPID);
            owner.heal(self.damage * multiplier / 8.0F);
        }

        ci.cancel();
    }
}