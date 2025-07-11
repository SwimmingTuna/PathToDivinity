package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.api.item.CSWeaponUtil;
import com.aqutheseal.celestisynth.common.attack.breezebreaker.BreezebreakerWindRoarAttack;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = BreezebreakerWindRoarAttack.class, remap = false)
public class BreezebreakerWindRoarAttackMixin {

    @Inject(method = "tickAttack", at = @At("HEAD"), cancellable = true)
    public void injectTickAttack(CallbackInfo ci) {
        BreezebreakerWindRoarAttack self = (BreezebreakerWindRoarAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();

        if (self.getTimerProgress() == 10) {
            self.sendExpandingParticles(level, ParticleTypes.CAMPFIRE_COSY_SMOKE, player.blockPosition(), 45, 0.01F);
            for (LivingEntity target : self.getEntitiesInLine(player, 10.0)) {
                if (target != player) {
                    double targetDist = (double) target.distanceTo(player);
                    self.attributeDependentAttack(player, target, self.getStack(), 2.0F + (float) targetDist * 0.5F, AttackHurtTypes.NO_KB);
                    target.addEffect(CSWeaponUtil.nonVisiblePotionEffect(MobEffects.WEAKNESS, 60, 2));
                    target.addEffect(CSWeaponUtil.nonVisiblePotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                    self.sendExpandingParticles(level, ParticleTypes.FIREWORK, player.blockPosition().above(), 45, 0.2F);
                }
            }

            double speed = 7.0;
            for(float distii = 0.0F; (double)distii < speed; distii += 0.25F) {
                BlockPos newPos = new BlockPos((int)(player.getX() + self.calculateXLook(player) * (double)distii), (int)player.getY(), (int)(player.getZ() + self.calculateZLook(player) * (double)distii));
                if (!level.isEmptyBlock(newPos)) {
                    speed = (double)distii;
                    break;
                }
            }

            Vec3 delta = new Vec3(self.calculateXLook(player) * speed, 0.0, self.calculateZLook(player) * speed);
            player.moveTo(player.getX() + self.calculateXLook(player) * speed, player.getY(), player.getZ() + self.calculateZLook(player) * speed);
            double[] multipliers = new double[]{2.0, 1.5, 1.0, 0.5, 0.0};
            CSVisualType[] effectTypes = new CSVisualType[]{CSVisualTypes.BREEZEBREAKER_DASH.get(), CSVisualTypes.BREEZEBREAKER_DASH_2.get(), CSVisualTypes.BREEZEBREAKER_DASH_3.get(), CSVisualTypes.BREEZEBREAKER_DASH_3.get(), CSVisualTypes.BREEZEBREAKER_DASH_3.get()};

            for(int i = 0; i < multipliers.length; ++i) {
                int yOffset = i > 1 ? 1 : 0;
                CSEffectEntity.createInstance(player, null, effectTypes[i], delta.x() * multipliers[i], yOffset, delta.z() * multipliers[i]);
            }

            player.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.5F);
            player.playSound((SoundEvent)CSSoundEvents.IMPACT_HIT.get(), 1.0F, 1.0F);
            player.playSound((SoundEvent)CSSoundEvents.STEP.get(), 1.0F, 1.0F);
        }

        ci.cancel();
    }
}