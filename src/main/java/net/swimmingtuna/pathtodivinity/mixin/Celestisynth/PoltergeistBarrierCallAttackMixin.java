package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.common.attack.poltergeist.PoltergeistBarrierCallAttack;
import com.aqutheseal.celestisynth.common.capabilities.CSEntityCapabilityProvider;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(value = PoltergeistBarrierCallAttack.class, remap = false)
public class PoltergeistBarrierCallAttackMixin {

    @Inject(method = "startUsing", at = @At("HEAD"), cancellable = true)
    public void injectStartUsing(CallbackInfo ci) {
        PoltergeistBarrierCallAttack self = (PoltergeistBarrierCallAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();

        double range = 4.0;
        Iterator<Entity> var3 = self.iterateEntities(level, self.createAABB(player.blockPosition().offset((int)(self.calculateXLook(player) * 2.0), 0, (int)(self.calculateZLook(player) * 2.0)), range)).iterator();

        while(true) {
            while(var3.hasNext()) {
                Entity entityBatch = var3.next();
                if (entityBatch instanceof LivingEntity target) {
                    if (target != player && target.isAlive() && !player.isAlliedTo(target)) {
                        self.attributeDependentAttack(player, target, self.getStack(), 6.0F, AttackHurtTypes.REGULAR); // Changed from 0.8F to 7F
                        target.playSound((SoundEvent)CSSoundEvents.SWORD_CLASH.get(), 0.25F, 0.5F);
                        CSEntityCapabilityProvider.get(target).ifPresent((data) -> {
                            data.setPhantomTag(player, 200);
                        });
                        continue;
                    }
                }

                if (entityBatch instanceof Projectile) {
                    entityBatch.remove(Entity.RemovalReason.DISCARDED);
                }
            }

            CSEffectEntity.createInstance(player, (Entity)null, CSVisualTypes.POLTERGEIST_RETREAT.get(), self.calculateXLook(player) * 2.0, 1.0, self.calculateZLook(player) * 2.0);
            self.sendExpandingParticles(level, ParticleTypes.SOUL, player.blockPosition(), 45, 0.5F);
            double deltaY = player.onGround() ? 3.0 : 0.9;
            player.setDeltaMovement(self.calculateXLook(player) * -0.7, deltaY, self.calculateZLook(player) * -0.7);
            player.hurtMarked = true;
            player.playSound(SoundEvents.ENDER_CHEST_OPEN, 1.0F, 1.5F);
            player.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.5F);
            self.useAndDamageItem(self.getStack(), level, player, 2);
            ci.cancel();
            return;
        }
    }
}