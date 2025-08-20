package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.thecelestialworkshop.celestisynth.api.item.AttackHurtTypes;
import org.thecelestialworkshop.celestisynth.api.item.CSWeaponUtil;
import org.thecelestialworkshop.celestisynth.common.attack.poltergeist.PoltergeistCosmicSteelAttack;
import org.thecelestialworkshop.celestisynth.common.capabilities.CSEntityCapabilityProvider;

@Mixin(value = PoltergeistCosmicSteelAttack.class, remap = false)
public class PoltergeistCosmicSteelAttackMixin {

    @Inject(method = "doImpact", at = @At("HEAD"), cancellable = true)
    public void injectDoImpact(boolean isGiantImpact, double kbX, double kbZ, double range, CallbackInfo ci) {
        PoltergeistCosmicSteelAttack self = (PoltergeistCosmicSteelAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();

        for (Entity entityBatch : self.iterateEntities(level, self.createAABB(player.blockPosition().offset((int) kbX, 1, (int) kbZ), range))) {
            if (entityBatch instanceof LivingEntity target) {
                if (target != player && target.isAlive() && !player.isAlliedTo(target)) {
                    float dmgCalc = isGiantImpact ? 1.5F : 1.2F;
                    float attributedDmg = self.calculateAttributeDependentDamage(player, self.getStack(), dmgCalc);
                    float smashHeightAdd = (float) self.getTagController().getInt("cs.poltergeistSmashHeight");
                    self.initiateAbilityAttack(player, target, (attributedDmg + smashHeightAdd) * 5.5F, AttackHurtTypes.NO_KB); // Multiply by 7F
                    target.addEffect(CSWeaponUtil.nonVisiblePotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 2));
                    target.addEffect(CSWeaponUtil.nonVisiblePotionEffect(MobEffects.CONFUSION, 100, 0));
                    target.hurtMarked = true;
                    target.setDeltaMovement((target.getX() - (player.getX() + kbX)) / 3.0, (target.getY() - player.getY()) / 3.0, (target.getZ() - (player.getZ() + kbZ)) / 3.0);
                    CSWeaponUtil.disableRunningWeapon(target);
                    if (!level.isClientSide()) {
                        CSEntityCapabilityProvider.get(target).ifPresent((data) -> {
                            data.setPhantomTag(player, 100);
                        });
                    }

                    if (target.isDeadOrDying()) {
                        player.getCooldowns().removeCooldown(self.getStack().getItem());
                        self.getTagExtras().putBoolean("cs.queueComboLarge", true);
                    }
                }
            }

            if (entityBatch instanceof Projectile projectile) {
                projectile.setDeltaMovement(0.0, 1.2, 0.0);
            }
        }

        ci.cancel();
    }
}