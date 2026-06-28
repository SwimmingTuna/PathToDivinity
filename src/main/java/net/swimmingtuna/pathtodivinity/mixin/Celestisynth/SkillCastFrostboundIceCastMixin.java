package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.thecelestialworkshop.celestisynth.api.item.AttackHurtTypes;
import org.thecelestialworkshop.celestisynth.common.capabilities.CSEntityCapabilityProvider;
import org.thecelestialworkshop.celestisynth.common.entity.base.CSEffectEntity;
import org.thecelestialworkshop.celestisynth.common.entity.helper.CSVisualType;
import org.thecelestialworkshop.celestisynth.common.entity.skillcast.SkillCastFrostboundIceCast;
import org.thecelestialworkshop.celestisynth.common.registry.CSEntityTypes;
import org.thecelestialworkshop.celestisynth.common.registry.CSSoundEvents;
import org.thecelestialworkshop.celestisynth.common.registry.CSVisualTypes;
import org.thecelestialworkshop.celestisynth.util.ParticleUtil;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Mixin(value = SkillCastFrostboundIceCast.class, remap = true)
public class SkillCastFrostboundIceCastMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void injectTick(CallbackInfo ci) {
        SkillCastFrostboundIceCast self = (SkillCastFrostboundIceCast)(Object)this;

        UUID ownerUuid = self.getOwnerUUID();
        Player ownerPlayer = ownerUuid == null ? null : self.level().getPlayerByUUID(ownerUuid);

        // Fixed: Properly typed variables and null safety
        Pair<SoundEvent, SoundEvent> sound = Pair.of(CSSoundEvents.ICE_CAST.get(), SoundEvents.PLAYER_HURT_FREEZE);
        ParticleType<?> particle = ParticleTypes.SNOWFLAKE;
        CSVisualType impact = CSVisualTypes.FROSTBOUND_ICE_CAST.get();

        int i;
        double xI;
        double zI;
        if (self.tickCount == 1 && self.getCastLevel() > 0) {
            self.playSound(sound.getFirst());

            for(i = 0; i < 360; i += 2) {
                xI = (double)(Mth.sin((float)i) * 3.0F);
                zI = (double)(Mth.cos((float)i) * 3.0F);
                ParticleUtil.sendParticles(self.level(), particle, self.getX() + xI, self.getY(), self.getZ() + zI, 1, -xI / 8.0, 0.0, -zI / 8.0);
            }
        }

        if (self.tickCount == 5 && self.getCastLevel() > 0 && !self.level().isClientSide()) {
            SkillCastFrostboundIceCast frostboundIceCast = (SkillCastFrostboundIceCast)((EntityType) CSEntityTypes.FROSTBOUND_ICE_CAST.get()).create(self.level());
            float aX = self.getAngleX();
            float aZ = self.getAngleZ();
            int floorPos = self.getFloorPositionUnderPlayerYLevel(self.level(), self.blockPosition().offset((int)aX, 0, (int)aZ));
            frostboundIceCast.setOwnerUUID(ownerUuid);
            frostboundIceCast.setOriginItem(self.getOriginItem());
            frostboundIceCast.setCastLevel(self.getCastLevel() - 1);
            frostboundIceCast.setAngleX(aX);
            frostboundIceCast.setAngleZ(aZ);
            frostboundIceCast.moveTo(self.getX() + (double)aX, (double)(floorPos + 2), self.getZ() + (double)aZ);
            frostboundIceCast.damage = self.damage;
            self.level().addFreshEntity(frostboundIceCast);
        }

        if (self.tickCount == 20 && self.getCastLevel() > 0) {
            CSEffectEntity.createInstance(ownerPlayer, self, impact, 0.0, 0.5, 0.0);

            for(i = 0; i < 360; i += 4) {
                xI = (double)(Mth.sin((float)i) * 3.0F);
                zI = (double)(Mth.cos((float)i) * 3.0F);
                ParticleUtil.sendParticles(self.level(), particle, self.getX(), self.getY() - 1.0, self.getZ(), 1, xI / 10.0, 0.3, zI / 10.0);
            }

            double range = 1.0;
            List<Entity> entities = self.level().getEntitiesOfClass(Entity.class, new AABB(self.getX() + range, self.getY() + range * 3.0, self.getZ() + range, self.getX() - range, self.getY(), self.getZ() - range));
            Iterator var17 = entities.iterator();

            while(var17.hasNext()) {
                Entity entityBatch = (Entity)var17.next();
                if (entityBatch instanceof LivingEntity) {
                    LivingEntity target = (LivingEntity)entityBatch;
                    if (target != ownerPlayer && target.isAlive()) {
                        self.initiateAbilityAttack(ownerPlayer, target, self.damage * 5.5F, AttackHurtTypes.NO_KB);
                        target.setDeltaMovement(0.0, 0.5, 0.0);
                        CSEntityCapabilityProvider.get(target).ifPresent((data) -> {
                            data.setFrostbound(200);
                        });
                        target.playSound(sound.getSecond());
                    }
                }
            }

            self.shakeScreensForNearbyPlayers(ownerPlayer, self.level(), 15.0, 20, 15, 0.02F);
        }

        if (self.tickCount == 60) {
            self.remove(Entity.RemovalReason.DISCARDED);
        }

        ci.cancel();
    }
}