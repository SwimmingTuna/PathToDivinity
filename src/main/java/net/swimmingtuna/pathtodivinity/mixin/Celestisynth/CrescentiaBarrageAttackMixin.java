package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.api.item.CSWeaponUtil;
import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.attack.cresentia.CrescentiaBarrageAttack;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.entity.projectile.CrescentiaDragon;
import com.aqutheseal.celestisynth.common.item.weapons.CrescentiaItem;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.core.object.Color;

import java.util.List;

@Mixin(value = CrescentiaBarrageAttack.class, remap = false)
public class CrescentiaBarrageAttackMixin {

    @Inject(method = "tickAttack", at = @At("HEAD"), cancellable = true)
    public void injectTickAttack(CallbackInfo ci) {
        CrescentiaBarrageAttack self = (CrescentiaBarrageAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();

        List<CrescentiaDragon> dragons = level.getEntitiesOfClass(CrescentiaDragon.class, player.getBoundingBox().inflate(128.0)).stream().filter((e) -> {
            return e.getOwner() == player;
        }).toList();

        for (CrescentiaDragon dragon : dragons) {
            Vec3 opp = player.position().add(0.0, 1.0, 0.0).subtract(dragon.position()).normalize().scale(0.25);
            dragon.push(opp.x(), opp.y(), opp.z());
        }

        if (self.getTimerProgress() == 15) {
            player.playSound((SoundEvent)CSSoundEvents.WHIRLWIND.get(), 0.35F, 0.5F + level.random.nextFloat());
            self.chantMessage(self.player, "crescentia1", 20, Color.MAGENTA.argbInt());
        }

        if (self.getTimerProgress() >= 15 && self.getTimerProgress() <= 60) {
            double range = 6.0;
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(range, range, range).move(self.calculateXLook(player), 0.0, self.calculateZLook(player)));

            for (Entity entityBatch : entities) {
                if (entityBatch instanceof LivingEntity target) {
                    if (target != player && target.isAlive() && !player.isAlliedTo(target) && target.distanceToSqr(player) <= range * range) {
                        self.attributeDependentAttack(player, target, self.getStack(), 0.25F, AttackHurtTypes.RAPID);
                        target.addEffect(CSWeaponUtil.nonVisiblePotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 2));
                    }
                }

                if (entityBatch instanceof Projectile projectile) {
                    CrescentiaItem.createCrescentiaFirework(self.getStack(), level, player, projectile.getX(), projectile.getY(), projectile.getZ(), true);
                    player.playSound(SoundEvents.FIREWORK_ROCKET_LAUNCH, 1.0F, 1.0F);
                    projectile.remove(Entity.RemovalReason.DISCARDED);
                }
            }

            if (self.getTimerProgress() % 30 == 0) {
                player.playSound((SoundEvent)CSSoundEvents.WHIRLWIND.get(), 0.15F, 1.0F + (float)(player.getRandom().nextGaussian() * 0.25));
            }

            if (self.getTimerProgress() % 5 == 0) {
                if (level.random.nextBoolean()) {
                    CSEffectEntity.createInstance(player, (Entity)null, (CSVisualType)CSVisualTypes.CRESCENTIA_STRIKE.get(), self.calculateXLook(player), -0.3, self.calculateZLook(player));
                } else {
                    CSEffectEntity.createInstance(player, (Entity)null, (CSVisualType)CSVisualTypes.CRESCENTIA_STRIKE_INVERTED.get(), self.calculateXLook(player), -0.3, self.calculateZLook(player));
                }

                WeaponAttackInstance.playRandomBladeSound(player, WeaponAttackInstance.BASE_WEAPON_EFFECTS.length);
            }

            if (self.getTimerProgress() % 2 == 0) {
                CSEffectEntity.createInstance(player, (Entity)null, (CSVisualType)CSVisualTypes.SOLARIS_AIR_LARGE.get(), 0.0, -1.0, 0.0);
                float offX = level.random.nextFloat() * 16.0F - 8.0F;
                float offY = level.random.nextFloat() * 16.0F - 8.0F;
                float offZ = level.random.nextFloat() * 16.0F - 8.0F;
                CrescentiaItem.createCrescentiaFirework(self.getStack(), level, player, player.getX() + (double)offX, player.getY() + (double)offY, player.getZ() + (double)offZ, false);
                if (level.random.nextBoolean()) {
                    CrescentiaItem.createCrescentiaFirework(self.getStack(), level, player, player.getX() + (double)offZ, player.getY() + (double)offX, player.getZ() + (double)offY, false);
                }
            }
        }

        ci.cancel();
    }
}