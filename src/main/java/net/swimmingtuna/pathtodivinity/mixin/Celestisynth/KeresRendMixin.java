package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.common.entity.projectile.KeresRend;
import com.aqutheseal.celestisynth.common.registry.CSDamageSources;
import com.aqutheseal.celestisynth.common.registry.CSMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(value = KeresRend.class, remap = false)
public class KeresRendMixin {

    @Shadow
    private List<LivingEntity> finishedAttacking;

    @Shadow
    public float baseDamage;

    @SuppressWarnings("Deprecated")
    @Inject(method = "checkWalls", at = @At("HEAD"), cancellable = true)
    private void amplifyDamage(net.minecraft.world.phys.AABB pArea, CallbackInfo ci) {
        KeresRend self = (KeresRend)(Object)this;
        Level level = self.level();

        double iter = 0.0;
        int minY = net.minecraft.util.Mth.floor(pArea.minY);
        int maxY = net.minecraft.util.Mth.floor(pArea.maxY);

        for(int yy = minY; yy <= maxY; ++yy) {
            iter += 0.25;
            pArea = pArea.inflate(iter / 6.0);
            int minX = net.minecraft.util.Mth.floor(pArea.minX);
            int maxX = net.minecraft.util.Mth.floor(pArea.maxX);
            int minZ = net.minecraft.util.Mth.floor(pArea.minZ);
            int maxZ = net.minecraft.util.Mth.floor(pArea.maxZ);

            for(int xx = minX; xx <= maxX; ++xx) {
                for(int zz = minZ; zz <= maxZ; ++zz) {
                    net.minecraft.core.BlockPos blockpos = new net.minecraft.core.BlockPos(xx, yy, zz);
                    net.minecraft.world.level.block.state.BlockState blockstate = level.getBlockState(blockpos);
                    if (!blockstate.isAir() && !blockstate.liquid() && !blockstate.is(net.minecraft.tags.BlockTags.DRAGON_IMMUNE) && (blockpos.getX() != self.getOwner().getBlockX() || blockpos.getZ() != self.getOwner().getBlockZ())) {
                        if (!level.isClientSide) {
                            if (yy == minY) {
                                level.setBlock(blockpos, net.minecraft.world.level.material.Fluids.LAVA.defaultFluidState().createLegacyBlock(), 2);
                            } else {
                                level.setBlockAndUpdate(blockpos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState());
                            }
                        }

                        double xR = self.random.nextGaussian() * 0.5;
                        double yR = self.random.nextGaussian() * 0.5;
                        double zR = self.random.nextGaussian() * 0.5;
                        com.aqutheseal.celestisynth.util.ParticleUtil.sendParticle(level, net.minecraft.core.particles.ParticleTypes.FLASH, (double)xx + xR, (double)yy + yR, (double)zz + zR);
                        self.playSound(net.minecraft.sounds.SoundEvents.BLAZE_SHOOT, 0.1F, 1.0F);
                    }
                }
            }
        }

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, self.getBoundingBox().inflate(4.0, 4.0, 4.0)).stream().filter((living) -> {
            return living != self.getOwner() && !this.finishedAttacking.contains(living);
        }).toList();

        for (LivingEntity target : targets) {
            Entity entity = self.getOwner();
            if (entity instanceof LivingEntity owner) {
                target.addEffect(new MobEffectInstance((MobEffect) CSMobEffects.CURSEBANE.get(), 250, 7));
                float damageCalculation = (this.baseDamage + target.getMaxHealth() * this.baseDamage * 0.015F); // Amplify damage by 5
                owner.heal(damageCalculation / 8.0F);
                if (owner instanceof Player player) {
                    player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + (int) ((double) damageCalculation * 0.025));
                    player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() + (float) ((int) ((double) damageCalculation * 0.025)));
                }

                ((com.aqutheseal.celestisynth.api.item.CSWeaponUtil) self).initiateAbilityAttack(owner, target, damageCalculation, CSDamageSources.instance(level).erasure(owner), AttackHurtTypes.RAPID_NO_KB);
                if (target.isDeadOrDying()) {
                    target.remove(Entity.RemovalReason.KILLED);
                }

                this.finishedAttacking.add(target);
            }
        }

        ci.cancel();
    }
}