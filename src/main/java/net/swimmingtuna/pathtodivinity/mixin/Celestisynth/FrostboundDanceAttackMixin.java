package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.common.attack.frostbound.FrostboundDanceAttack;
import com.aqutheseal.celestisynth.common.capabilities.CSEntityCapabilityProvider;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.common.registry.CSParticleTypes;
import com.aqutheseal.celestisynth.util.SkinUtil;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(value = FrostboundDanceAttack.class, remap = false)
public class FrostboundDanceAttackMixin {

    @Inject(method = "performImpact", at = @At("HEAD"), cancellable = true)
    public void injectPerformImpact(double xOffset, double zOffset, CallbackInfo ci) {
        FrostboundDanceAttack self = (FrostboundDanceAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();

        Pair sound;
        Object particle;
        CSVisualType impact;
        if (SkinUtil.getSkinIndex(self.getStack()) == 1) {
            sound = Pair.of((SoundEvent)CSSoundEvents.GROUND_IMPACT_WATER.get(), SoundEvents.PLAYER_HURT_DROWN);
            particle = CSParticleTypes.WATER_DROP.get();
            impact = (CSVisualType)CSVisualTypes.FROSTBOUND_IMPACT_CRACK_SEABR.get();
        } else {
            sound = Pair.of((SoundEvent)CSSoundEvents.SWORD_CLASH.get(), SoundEvents.PLAYER_HURT_FREEZE);
            particle = ParticleTypes.SNOWFLAKE;
            impact = (CSVisualType)CSVisualTypes.FROSTBOUND_IMPACT_CRACK.get();
        }

        BlockPos groundPos = self.getFloorPositionUnderPlayer(level, player.blockPosition());
        player.setDeltaMovement(0.0, (double)groundPos.getY() - player.getY(), 0.0);
        self.playSoundAt(level, SoundEvents.GLASS_BREAK, groundPos);
        self.playSoundAt(level, (SoundEvent)sound.getFirst(), groundPos);
        CSEffectEntity.createInstanceLockedPos(player, (Entity)null, impact, player.getX() + xOffset, (double)groundPos.getY() - 0.75, player.getZ() + zOffset);

        for(int i = 0; i < 360; i += 4) {
            double xI = xOffset + (double)(Mth.sin((float)i) * 3.0F);
            double zI = zOffset + (double)(Mth.cos((float)i) * 3.0F);
            ParticleUtil.sendParticles(level, (ParticleType)particle, player.getX() + xI, (double)groundPos.getY() + 1.5, player.getZ() + zI, 1, xI / 5.0, 0.0, zI / 5.0);
        }

        if (level.isClientSide()) {
            self.shakeScreens(player, 5, 4, 0.0015F);
        }
        for (Entity entity : self.iterateEntities(level, self.createAABB(groundPos.offset((int) xOffset, 1, (int) zOffset), 6.0, 3.0))) {
            if (entity instanceof LivingEntity target) {
                if (entity != player) {
                    self.attributeDependentAttack(player, target, self.getStack(), 5.5F, AttackHurtTypes.REGULAR);
                    CSEntityCapabilityProvider.get(target).ifPresent((data) -> {
                        data.setFrostbound(100);
                    });
                    entity.playSound((SoundEvent) sound.getSecond());
                }
            }
        }

        ci.cancel();
    }

    @Inject(method = "performIceSlash", at = @At("HEAD"), cancellable = true)
    public void injectPerformIceSlash(int slashIndex, double xOffset, double zOffset, CallbackInfo ci) {
        FrostboundDanceAttack self = (FrostboundDanceAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();

        Pair sound;
        Object particle;
        CSVisualType slash;
        CSVisualType var10000;
        if (SkinUtil.getSkinIndex(self.getStack()) == 1) {
            sound = Pair.of((SoundEvent)CSSoundEvents.SLASH_WATER.get(), SoundEvents.PLAYER_HURT_DROWN);
            particle = (ParticleType)CSParticleTypes.WATER_DROP.get();
            switch (slashIndex) {
                case 1 -> var10000 = (CSVisualType)CSVisualTypes.FROSTBOUND_SLASH_INVERTED_SEABR.get();
                case 2 -> var10000 = (CSVisualType)CSVisualTypes.FROSTBOUND_SLASH_LARGE_SEABR.get();
                default -> var10000 = (CSVisualType)CSVisualTypes.FROSTBOUND_SLASH_SEABR.get();
            }

            slash = var10000;
        } else {
            sound = Pair.of((SoundEvent)CSSoundEvents.FROZEN_SLASH.get(), SoundEvents.PLAYER_HURT_DROWN);
            particle = ParticleTypes.SNOWFLAKE;
            switch (slashIndex) {
                case 1 -> var10000 = (CSVisualType)CSVisualTypes.FROSTBOUND_SLASH_INVERTED.get();
                case 2 -> var10000 = (CSVisualType)CSVisualTypes.FROSTBOUND_SLASH_LARGE.get();
                default -> var10000 = (CSVisualType)CSVisualTypes.FROSTBOUND_SLASH.get();
            }

            slash = var10000;
        }

        self.playSoundAt(level, (SoundEvent)sound.getFirst(), player.blockPosition().offset((int)xOffset, 0, (int)zOffset));
        CSEffectEntity.createInstance(player, player, slash, xOffset, slashIndex == 2 ? 0.15 : 0.25, zOffset);

        for(int i = 0; i < 360; i += 4) {
            double sizeMult = slashIndex == 2 ? 1.5 : 0.5;
            double xI = xOffset + (double)(Mth.sin((float)i) * 3.0F);
            double zI = zOffset + (double)(Mth.cos((float)i) * 3.0F);
            ParticleUtil.sendParticle(level, (ParticleType)particle, player.getX() + xI, player.getY() + 0.5, player.getZ() + zI, (double)Mth.sin((float)i) * sizeMult, 0.0, (double)Mth.cos((float)i) * sizeMult);
        }

        for (Entity entity : self.iterateEntities(level, self.createAABB(player.blockPosition().offset((int) xOffset, 1, (int) zOffset), slashIndex == 2 ? 8.0 : 5.0, 3.0))) {
            if (entity instanceof LivingEntity target) {
                if (entity != player) {
                    self.attributeDependentAttack(player, target, self.getStack(), 5.5F, AttackHurtTypes.REGULAR);
                    CSEntityCapabilityProvider.get(target).ifPresent((data) -> {
                        data.setFrostbound(60);
                    });
                    entity.playSound((SoundEvent) sound.getSecond());
                }
            }
        }

        player.setDeltaMovement(self.calculateXLook(player) * 1.5, 0.25, self.calculateZLook(player) * 1.5);

        ci.cancel();
    }
}