package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.thecelestialworkshop.celestisynth.api.item.AttackHurtTypes;
import org.thecelestialworkshop.celestisynth.common.attack.solaris.SolarisFullRoundAttack;
import org.thecelestialworkshop.celestisynth.common.entity.base.CSEffectEntity;
import org.thecelestialworkshop.celestisynth.common.registry.CSSoundEvents;
import org.thecelestialworkshop.celestisynth.common.registry.CSVisualTypes;
import org.thecelestialworkshop.celestisynth.util.ParticleUtil;
import software.bernie.geckolib.core.object.Color;

import java.util.List;

@Mixin(value = SolarisFullRoundAttack.class, remap = false)
public class SolarisFullRoundAttackMixin {

    @Inject(method = "tickAttack", at = @At("HEAD"), cancellable = true)
    public void injectTickAttack(CallbackInfo ci) {
        SolarisFullRoundAttack self = (SolarisFullRoundAttack) (Object) this;

        if (self.getTimerProgress() == 13) {
            self.player.playSound(CSSoundEvents.STEP.get());

            for (int i = 0; i < 15; ++i) {
                RandomSource rand = self.level.random;
                ParticleUtil.sendParticles(self.level, ParticleTypes.LARGE_SMOKE, self.player.getX(), self.player.getY(), self.player.getZ(), 0, (double) (-1.0F + rand.nextFloat() * 2.0F) * 0.5, 0.1, (double) (-1.0F + rand.nextFloat() * 2.0F) * 0.5);
            }
        }

        if (self.getTimerProgress() == 23) {
            self.chantMessage(self.player, "solaris1", 20, Color.ORANGE.argbInt());
        }

        if (self.getTimerProgress() > 0 && self.getTimerProgress() < 24) {
            ParticleUtil.sendParticles(self.level, ParticleTypes.FLAME, self.player.getX(), self.player.getY(), self.player.getZ(), 0, 0.0, 0.1, 0.0);
            self.player.setDeltaMovement(0.0, 0.0, 0.0);
            self.player.hurtMarked = true;
        } else if (self.getTimerProgress() > 23 && self.getTimerProgress() < 60) {
            BlockPos blockPosForAttack = self.player.blockPosition();
            int range = 4;
            List<LivingEntity> entities = self.level.getEntitiesOfClass(LivingEntity.class, new AABB(blockPosForAttack.offset(-range, -range, -range), blockPosForAttack.offset(range, range, range)));
            for (LivingEntity target : entities) {
                if (target != self.player && !self.player.isAlliedTo(target) && target.isAlive()) {
                    self.attributeDependentAttack(self.player, target, self.stack, 0.23F * 2.0F, AttackHurtTypes.RAPID_NO_KB);
                    target.setSecondsOnFire(5);
                }
            }

            if (self.getTagController().getInt("cs.directionIndex") == 0) {
                movePlayerInCircularMotion(self.player, self.getTimerProgress(), false);
            } else if (self.getTagController().getInt("cs.directionIndex") == 1) {
                movePlayerInCircularMotion(self.player, self.getTimerProgress(), true);
            }

            CSEffectEntity.createInstance(self.player,  null, CSVisualTypes.SOLARIS_BLITZ.get(), 0.0, 2.5, 0.0);
            CSEffectEntity.createInstance(self.player,  null, CSVisualTypes.SOLARIS_AIR.get());
            dashSound(self.player, 1.0 + self.player.getRandom().nextGaussian() / 2.0);
            BlockPos playerPos = self.player.blockPosition();
            double radius = 3.0;
            double particleCount = 30.0;
            double angleIncrement = 6.283185307179586 / particleCount;

            for (int i = 0; (double) i < particleCount; ++i) {
                double angle = (double) i * angleIncrement;
                double rotationX = self.level.random.nextDouble() * 360.0;
                double rotationZ = self.level.random.nextDouble() * 360.0;
                double x = (double) playerPos.getX() + radius * Math.cos(angle);
                double y = (double) playerPos.getY() + 1.5;
                double z = (double) playerPos.getZ() + radius * Math.sin(angle);
                double motionX = Math.sin(Math.toRadians(rotationX)) * Math.cos(Math.toRadians(rotationZ));
                double motionY = Math.sin(Math.toRadians(rotationZ));
                double motionZ = Math.cos(Math.toRadians(rotationX)) * Math.cos(Math.toRadians(rotationZ));
                if (!self.level.isClientSide()) {
                    ParticleUtil.sendParticles(self.level, ParticleTypes.FLAME, x + 0.5, y, z + 0.5, 0, motionX, motionY, motionZ);
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
                player.playSound((SoundEvent) CSSoundEvents.SWORD_SWING.get(), 0.2F, (float) pitch);
            } else {
                player.playSound((SoundEvent) CSSoundEvents.AIR_SWING.get(), 0.2F, (float) pitch);
            }
        } else if (player.getRandom().nextBoolean()) {
            player.playSound((SoundEvent) CSSoundEvents.SWORD_SWING_FIRE.get(), 0.2F, (float) pitch);
        } else {
            player.playSound((SoundEvent) CSSoundEvents.IMPACT_HIT.get(), 0.2F, (float) pitch);
        }
    }

    /**
     * Moves the player in a circular motion based on the timer progress
     * This matches the original implementation from the decompiled class
     */
    @Unique
    private void movePlayerInCircularMotion(Player player, int tick, boolean isRight) {
        if (player == null) return;

        double radius = 1.5;
        double forwardX = Math.sin(Math.toRadians((double) player.getYRot()));
        double forwardZ = -Math.cos(Math.toRadians((double) player.getYRot()));
        double perpendicularX = -forwardZ;
        double perpendicularZ = forwardX;
        double angle = (double) (tick - 45) / 25.0 * Math.PI * 2.0;
        double offsetX = radius * Math.cos(angle);
        double offsetZ = radius * Math.sin(angle);
        double finalX = isRight ? player.getX() + forwardX * offsetX - perpendicularX * offsetZ : player.getX() + forwardX * offsetX + perpendicularX * offsetZ;
        double finalZ = isRight ? player.getZ() + forwardZ * offsetX - perpendicularZ * offsetZ : player.getZ() + forwardZ * offsetX + perpendicularZ * offsetZ;
        player.setDeltaMovement(finalX - player.getX(), player.getDeltaMovement().y, finalZ - player.getZ());
    }
}