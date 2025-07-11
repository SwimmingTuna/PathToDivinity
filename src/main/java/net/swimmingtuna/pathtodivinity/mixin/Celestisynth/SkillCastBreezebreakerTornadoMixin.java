package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.entity.skillcast.SkillCastBreezebreakerTornado;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Mixin(value = SkillCastBreezebreakerTornado.class, remap = false)
public class SkillCastBreezebreakerTornadoMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void injectTick(CallbackInfo ci) {
        SkillCastBreezebreakerTornado self = (SkillCastBreezebreakerTornado)(Object)this;

        UUID ownerUuid = self.getOwnerUUID();
        Player ownerPlayer = ownerUuid == null ? null : self.level().getPlayerByUUID(ownerUuid);
        self.setAngleX(self.getAngleX() + self.getAddAngleX());
        self.setAngleY(self.getAngleY() + self.getAddAngleY());
        self.setAngleZ(self.getAngleZ() + self.getAddAngleZ());
        double newX = self.getX() + (double)self.getAngleX();
        double newY = self.getY() + (double)self.getAngleY();
        double newZ = self.getZ() + (double)self.getAngleZ();
        BlockPos newPos = new BlockPos((int)newX, (int)newY, (int)newZ);
        double range = 6.0;
        List<Entity> entities = self.level().getEntitiesOfClass(Entity.class, new AABB(newX + range, newY + range * 2.0, newZ + range, newX - range, newY - range, newZ - range));
        for (Entity entityBatch : entities) {
            if (entityBatch instanceof LivingEntity target) {
                if (target != ownerPlayer && target.isAlive()) {
                    self.initiateAbilityAttack(ownerPlayer, target, self.damage * 9F, AttackHurtTypes.RAPID_NO_KB);
                    target.setDeltaMovement(0.0, 0.05, 0.0);
                }
            }
            if (entityBatch instanceof Projectile projectile) {
                projectile.remove(Entity.RemovalReason.DISCARDED);
            }
        }
        for(int yLevel = -1; yLevel < 6; ++yLevel) {
            if (yLevel == -1 || yLevel == 0 || yLevel == 1) {
                CSEffectEntity.createInstance(ownerPlayer, self, (CSVisualType)CSVisualTypes.SOLARIS_AIR_FLAT.get(), (double)self.getAngleX(), (double)(self.getAngleY() + (float)yLevel), (double)self.getAngleZ());
            }

            if (yLevel == 2 || yLevel == 3) {
                CSEffectEntity.createInstance(ownerPlayer, self, (CSVisualType) CSVisualTypes.SOLARIS_AIR_MEDIUM_FLAT.get(), (double)self.getAngleX(), (double)(self.getAngleY() + (float)yLevel), (double)self.getAngleZ());
            }

            if (yLevel == 4 || yLevel == 5) {
                CSEffectEntity.createInstance(ownerPlayer, self, (CSVisualType)CSVisualTypes.SOLARIS_AIR_LARGE_FLAT.get(), (double)self.getAngleX(), (double)(self.getAngleY() + (float)yLevel), (double)self.getAngleZ());
            }
        }

        if (self.tickCount % 20 == 0) {
            self.level().playSound(self.level().getPlayerByUUID(self.getOwnerUUID()), (double)self.getAngleX(), (double)self.getAngleY(), (double)self.getAngleZ(), (SoundEvent) CSSoundEvents.WHIRLWIND.get(), SoundSource.HOSTILE, 0.1F, 0.5F + self.random.nextFloat());
        }

        int radius = 2;

        for(int sx = -radius; sx <= radius; ++sx) {
            for(int sy = -radius; sy <= radius; ++sy) {
                for(int sz = -radius; sz <= radius; ++sz) {
                    if (self.level().getBlockState(newPos.offset(sx, sy, sz)).canBeReplaced()) {
                        self.level().destroyBlock(newPos.offset(sx, sy, sz), false, ownerPlayer);
                    }
                }
            }
        }

        if (self.tickCount == 100 || !self.level().getBlockState(newPos).isAir()) {
            self.remove(Entity.RemovalReason.DISCARDED);
        }

        ci.cancel();
    }
}