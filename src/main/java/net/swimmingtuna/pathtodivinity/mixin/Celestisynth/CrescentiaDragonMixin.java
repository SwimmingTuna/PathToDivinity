package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.api.item.CSWeaponUtil;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.entity.projectile.CrescentiaDragon;
import com.aqutheseal.celestisynth.common.item.weapons.CrescentiaItem;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Mixin(value = CrescentiaDragon.class, remap = false)
public class CrescentiaDragonMixin {

    @Shadow
    public LivingEntity chomped;

    @Shadow
    public float damage;

    @Shadow
    public int lifespan;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void injectTick(CallbackInfo ci) {
        CrescentiaDragon self = (CrescentiaDragon)(Object)this;

        Vec3 offsetVector = self.getDeltaMovement().normalize().scale(6.0);

        for(int i = 0; i < 15; ++i) {
            double xx = self.random.nextGaussian() * 0.15000000596046448;
            double yy = self.random.nextGaussian() * 0.15000000596046448;
            double zz = self.random.nextGaussian() * 0.15000000596046448;
            Vec3 direction = self.position();
            ParticleUtil.sendParticle(self.level(), ParticleTypes.END_ROD, direction.x() + offsetVector.x(), direction.y() + offsetVector.y() + 0.5, direction.z() + offsetVector.z(), xx, yy, zz);
        }

        List<Entity> entities = self.level().getEntitiesOfClass(Entity.class, self.getBoundingBox().inflate(4.0));
        ItemStack fireworkStack = new ItemStack(Items.FIREWORK_ROCKET);
        Entity entity = self.getOwner();
        if (entity instanceof Player playerOwner) {
            Stream<Entity> var10000 = entities.stream().filter((ent) -> ent instanceof LivingEntity && ent != playerOwner);
            Objects.requireNonNull(LivingEntity.class);
            List<LivingEntity> entitiesFiltered = var10000.map(LivingEntity.class::cast).toList();
            if (this.chomped == null && !entitiesFiltered.isEmpty()) {
                this.chomped = entitiesFiltered.get(self.random.nextInt(entitiesFiltered.size()));
            }
            for (Entity entityBatch : entities) {
                if (entityBatch instanceof LivingEntity target) {
                    if (target != self.getOwner() && target.isAlive()) {
                        self.initiateAbilityAttack(playerOwner, target, this.damage * 2.0f, AttackHurtTypes.RAPID_NO_KB);
                        target.addEffect(CSWeaponUtil.nonVisiblePotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 2));
                    }
                }

                if (entityBatch instanceof Projectile projectile) {
                    if (projectile.getOwner() != self.getOwner()) {
                        CrescentiaItem.createCrescentiaFirework(fireworkStack, self.level(), playerOwner, projectile.getX(), projectile.getY(), projectile.getZ(), true);
                        projectile.remove(Entity.RemovalReason.DISCARDED);
                    }
                }
            }

            if (this.chomped != null) {
                Vec3 vector = self.position().add(0.0, -1.0, 0.0).add(offsetVector).subtract(this.chomped.position());
                this.chomped.setDeltaMovement(vector.x(), vector.y(), vector.z());
                this.lifespan -= (int)this.chomped.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) * 2;
            }

            if (self.tickCount % 5 == 0) {
                if (self.random.nextBoolean()) {
                    CSEffectEntity.createInstance(playerOwner, self, (CSVisualType)CSVisualTypes.CRESCENTIA_STRIKE.get(), 0.0, -1.5, 0.0);
                } else {
                    CSEffectEntity.createInstance(playerOwner, self, (CSVisualType)CSVisualTypes.CRESCENTIA_STRIKE_INVERTED.get(), 0.0, -1.5, 0.0);
                }
            }

            if (self.tickCount % 30 == 0) {
                self.playSound((SoundEvent)CSSoundEvents.WHIRLWIND.get(), 0.2F, 0.5F + (float)(self.random.nextGaussian() * 0.25));
            }

            CSEffectEntity.createInstance(playerOwner, self, (CSVisualType)CSVisualTypes.SOLARIS_AIR.get());
            if (self.tickCount % 10 == 0) {
                float offX = self.random.nextFloat() * 20.0F - 10.0F;
                float offY = self.random.nextFloat() * 20.0F - 10.0F;
                float offZ = self.random.nextFloat() * 20.0F - 10.0F;
                CrescentiaItem.createCrescentiaFirework(fireworkStack, self.level(), playerOwner, self.getX() + (double)offX, self.getY() + (double)offY, self.getZ() + (double)offZ, false);
            }
        }

        if (self.tickCount > this.lifespan) {
            self.level().explode(self.getOwner(), self.getX(), self.getY(), self.getZ(), 1.0F, Level.ExplosionInteraction.MOB);
            self.remove(Entity.RemovalReason.DISCARDED);
        }

        ci.cancel();
    }
}