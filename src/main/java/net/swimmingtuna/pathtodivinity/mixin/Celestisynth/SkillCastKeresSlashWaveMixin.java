package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.common.entity.skillcast.SkillCastKeresSlashWave;
import com.aqutheseal.celestisynth.common.entity.projectile.KeresSlash;
import com.aqutheseal.celestisynth.common.entity.projectile.KeresShadow;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSParticleTypes;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = SkillCastKeresSlashWave.class, remap = false)
public class SkillCastKeresSlashWaveMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void injectTick(CallbackInfo ci) {
        SkillCastKeresSlashWave self = (SkillCastKeresSlashWave)(Object)this;

        Player player = self.getOwnerUUID() == null ? null : self.level().getPlayerByUUID(self.getOwnerUUID());
        if (player == null) {
            self.remove(Entity.RemovalReason.DISCARDED);
        } else {
            self.moveTo(player.position());
            if (self.lifespan > 0) {
                player.playSound((SoundEvent)CSSoundEvents.SLASH_WATER.get(), 0.1F, (float)(1.5 + player.getRandom().nextDouble() * 0.5));
            }

            for(int i = 0; (double)i < 22.5; ++i) {
                Vec3 particleDir = Vec3.ZERO.add((double)Mth.sin((float)i), (double)Mth.cos((float)i), 0.0).scale(0.9);
                Vec3 rotated = particleDir.xRot(-player.getXRot() * 0.017453292F).yRot(-player.getYRot() * 0.017453292F);
                ParticleUtil.sendParticle(self.level(), (SimpleParticleType)CSParticleTypes.KERES_OMEN.get(), player.getEyePosition().add(player.getLookAngle().scale(2.0)), rotated);
            }

            if (!self.level().isClientSide) {
                List<Float> angles = new ArrayList<>();
                angles.add(0.0F);
                if (self.hasMultishot) {
                    angles.add(10.0F);
                    angles.add(-10.0F);
                }

                for (float angle : angles) {
                    KeresSlash slash = new KeresSlash(CSEntityTypes.KERES_SLASH.get(), player, self.level());
                    Vec3 vec31 = player.getUpVector(1.0F);
                    Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double) (angle * 0.017453292F), vec31.x, vec31.y, vec31.z);
                    Vec3 vec3 = player.getViewVector(1.0F);
                    Vector3f shootAngle = vec3.toVector3f().rotate(quaternionf);
                    slash.setRoll((float) (self.random.nextGaussian() * 360.0));
                    slash.moveTo(self.position().add(0.0, 1.0, 0.0));
                    slash.baseDamage = self.damage * 4.0F;
                    slash.shoot((double) shootAngle.x, (double) shootAngle.y, (double) shootAngle.z, 6.0F, 0.0F);
                    self.level().addFreshEntity(slash);
                }

                if (self.random.nextInt(self.hasMultishot ? 2 : 3) == 0) {
                    KeresShadow shadow = new KeresShadow(CSEntityTypes.KERES_SHADOW.get(), player, self.level());
                    shadow.moveTo(player.getX(), shadow.getY() - 1.0, player.getZ());
                    shadow.shootFromRotation(player, (float)(self.level().random.nextGaussian() * 180.0), -15.0F - (float)(self.level().random.nextDouble() * 75.0), 0.0F, 1.0F, 0.0F);
                    shadow.setDeltaMovement(self.level().random.nextGaussian() * 0.25, 0.4, self.level().random.nextGaussian() * 0.25);
                    shadow.damage = self.damage * 1.5F;
                    self.level().addFreshEntity(shadow);
                }
            }

            if (self.tickCount > self.lifespan) {
                self.remove(Entity.RemovalReason.DISCARDED);
            }
        }

        ci.cancel();
    }
}