package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.api.item.CSWeaponUtil;
import com.aqutheseal.celestisynth.common.attack.breezebreaker.BreezebreakerDualGalestormAttack;
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

@Mixin(value = BreezebreakerDualGalestormAttack.class, remap = false)
public class BreezebreakerDualGalestormAttackMixin {

    @Inject(method = "tickAttack", at = @At("HEAD"), cancellable = true)
    public void injectTickAttack(CallbackInfo ci) {
        BreezebreakerDualGalestormAttack self = (BreezebreakerDualGalestormAttack)(Object)this;
        int progress = self.getTimerProgress();
        if (progress == 6 || progress == 11) {
            Player player = self.getPlayer();
            Level level = self.getLevel();
            double range = 6.0;
            List<Entity> entities = self.iterateEntities(level, self.createAABB(
                    player.blockPosition().offset((int)(self.calculateXLook(player) * 3.0), 1, (int)(self.calculateZLook(player) * 3.0)), range));

            for (Entity entity : entities) {
                if (entity instanceof LivingEntity target && target != player && target.isAlive() && !player.isAlliedTo(target)) {
                    self.attributeDependentAttack(player, target, self.getStack(), 6.75F, AttackHurtTypes.RAPID);
                    target.addEffect(CSWeaponUtil.nonVisiblePotionEffect(MobEffects.WEAKNESS, 40, 1));
                    self.sendExpandingParticles(level, ParticleTypes.POOF, target.blockPosition().above(), 15, 0.0F);
                }
            }
            player.playSound(CSSoundEvents.WIND_STRIKE.get());
            if (progress == 6) {
                CSEffectEntity.createInstance(player, null, CSVisualTypes.BREEZEBREAKER_SLASH.get(), self.calculateXLook(player), 0.0, self.calculateZLook(player));
            } else {
                CSEffectEntity.createInstance(player, null, CSVisualTypes.BREEZEBREAKER_SLASH_INVERTED.get(), self.calculateXLook(player), 0.0, self.calculateZLook(player));
            }

            player.playSound(CSSoundEvents.AIR_SWING.get(), 1.0F, 1.0F);
        }

        ci.cancel();
    }
}
