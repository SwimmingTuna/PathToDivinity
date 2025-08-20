package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.thecelestialworkshop.celestisynth.api.item.AttackHurtTypes;
import org.thecelestialworkshop.celestisynth.common.attack.solaris.SolarisSoulDashAttack;
import org.thecelestialworkshop.celestisynth.common.entity.base.CSEffectEntity;
import org.thecelestialworkshop.celestisynth.common.entity.helper.CSVisualType;
import org.thecelestialworkshop.celestisynth.common.registry.CSSoundEvents;
import org.thecelestialworkshop.celestisynth.common.registry.CSVisualTypes;
import org.thecelestialworkshop.celestisynth.util.ParticleUtil;
import software.bernie.geckolib.core.object.Color;

import java.util.List;

@Mixin(value = SolarisSoulDashAttack.class, remap = false)
public class SolarisSoulDashAttackMixin {

    @Inject(method = "tickAttack", at = @At("HEAD"), cancellable = true)
    public void injectTickAttack(CallbackInfo ci) {
        SolarisSoulDashAttack self = (SolarisSoulDashAttack)(Object)this;

        RandomSource rand = self.level.random;
        if (self.getTimerProgress() == 13) {
            self.player.playSound((SoundEvent) CSSoundEvents.STEP.get());

            for(int i = 0; i < 15; ++i) {
                ParticleUtil.sendParticles(self.level, ParticleTypes.LARGE_SMOKE, self.player.getX(), self.player.getY(), self.player.getZ(), 0, (double)(-1.0F + rand.nextFloat() * 2.0F) * 0.5, 0.1, (double)(-1.0F + rand.nextFloat() * 2.0F) * 0.5);
            }
        }

        if (self.getTimerProgress() == 23) {
            self.chantMessage(self.player, "solaris3", 20, Color.CYAN.argbInt());
        }

        if (self.getTimerProgress() > 0 && self.getTimerProgress() < 24) {
            ParticleUtil.sendParticles(self.level, ParticleTypes.SOUL_FIRE_FLAME, self.player.getX(), self.player.getY(), self.player.getZ(), 0, (double)(-1.0F + rand.nextFloat() * 2.0F), 0.1, (double)(-1.0F + rand.nextFloat() * 2.0F));
            self.player.setDeltaMovement(0.0, 0.0, 0.0);
            self.player.hurtMarked = true;
        } else if (self.getTimerProgress() > 23 && self.getTimerProgress() < 60) {
            BlockPos blockPosForAttack = self.player.blockPosition();
            int range = 7;
            List<LivingEntity> entities = self.level.getEntitiesOfClass(LivingEntity.class, new AABB(blockPosForAttack.offset(-range, -range, -range), blockPosForAttack.offset(range, range, range)));
            for (LivingEntity target : entities) {
                if (target != self.player && !self.player.isAlliedTo(target) && target.isAlive()) {
                    // Increased damage from 0.18F to 0.18F * 6.0F = 1.08F
                    self.attributeDependentAttack(self.player, target, self.stack, 0.18F * 2.2F, AttackHurtTypes.RAPID_NO_KB);
                    target.setSecondsOnFire(5);
                }
            }

            movePlayerInStraightMotion(self.player, (float)self.getTagController().getInt("cs.headRotLock"));
            CSEffectEntity.createInstance(self.player, (Entity)null, (CSVisualType) CSVisualTypes.SOLARIS_BLITZ_SOUL.get(), 0.0, 2.5, 0.0);
            CSEffectEntity.createInstance(self.player, (Entity)null, (CSVisualType)CSVisualTypes.SOLARIS_AIR_LARGE.get());
            dashSound(self.player, 0.5 + self.player.getRandom().nextGaussian() / 2.0);
            BlockPos playerPos = self.player.blockPosition();
            double radius = 3.0;
            double particleCount = 50.0;
            double angleIncrement = 6.283185307179586 / particleCount;

            for(int i = 0; (double)i < particleCount; ++i) {
                double angle = (double)i * angleIncrement;
                double rotationX = self.level.random.nextDouble() * 360.0;
                double rotationZ = self.level.random.nextDouble() * 360.0;
                double x = (double)playerPos.getX() + radius * Math.cos(angle);
                double y = (double)playerPos.getY() + 1.5;
                double z = (double)playerPos.getZ() + radius * Math.sin(angle);
                double motionX = Math.sin(Math.toRadians(rotationX)) * Math.cos(Math.toRadians(rotationZ));
                double motionY = Math.sin(Math.toRadians(rotationZ));
                double motionZ = Math.cos(Math.toRadians(rotationX)) * Math.cos(Math.toRadians(rotationZ));
                if (!self.level.isClientSide()) {
                    ParticleUtil.sendParticles((ServerLevel)self.level, ParticleTypes.SOUL_FIRE_FLAME, x + 0.5, y, z + 0.5, 0, motionX, motionY, motionZ);
                }
            }
        }
        ci.cancel();
    }

    /**
     * Plays a dash sound effect with the specified pitch
     */
    @Unique
    private void dashSound(Player player, double pitch) {
        if (player.getRandom().nextBoolean()) {
            if (player.getRandom().nextBoolean()) {
                player.playSound((SoundEvent)CSSoundEvents.SWORD_SWING.get(), 0.2F, (float)pitch);
            } else {
                player.playSound((SoundEvent)CSSoundEvents.AIR_SWING.get(), 0.2F, (float)pitch);
            }
        } else if (player.getRandom().nextBoolean()) {
            player.playSound((SoundEvent)CSSoundEvents.SWORD_SWING_FIRE.get(), 0.2F, (float)pitch);
        } else {
            player.playSound((SoundEvent)CSSoundEvents.IMPACT_HIT.get(), 0.2F, (float)pitch);
        }
    }

    /**
     * Moves the player in a straight line based on the locked rotation
     */
    @Unique
    private void movePlayerInStraightMotion(Player player, float yRot) {
        if (player == null) return;

        double speed = 1.5;
        double lookX = -Math.sin(Math.toRadians((double)yRot));
        double lookZ = Math.cos(Math.toRadians((double)yRot));
        double motionX = lookX * speed;
        double motionZ = lookZ * speed;
        player.setDeltaMovement(motionX, player.getDeltaMovement().y, motionZ);
    }
}