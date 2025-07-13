package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.common.attack.aquaflora.AquafloraSlashFrenzyAttack;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.item.weapons.AquafloraItem;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(value = AquafloraSlashFrenzyAttack.class, remap = false)
public class AquafloraSlashFrenzyAttackMixin {

    @Inject(method = "tickAttack", at = @At("HEAD"), cancellable = true)
    public void injectTickAttack(CallbackInfo ci) {
        AquafloraSlashFrenzyAttack self = (AquafloraSlashFrenzyAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();

        self.setCameraAngle(player, 1);

        if (self.getTimerProgress() >= 15 && self.getTimerProgress() % (self.checkDualWield(player, AquafloraItem.class) ? 4 : 7) == 0) {
            Predicate<Entity> filter = (entity) -> {
                boolean x;
                if (entity != player && entity instanceof LivingEntity livingEntity) {
                    if ((player.hasLineOfSight(livingEntity) || livingEntity.hasLineOfSight(player)) && livingEntity.isAlive() && !player.isAlliedTo(livingEntity)) {
                        x = true;
                        return x;
                    }
                }

                x = false;
                return x;
            };
            Stream<Entity> entityStream = self.iterateEntities(level, self.createAABB(player.blockPosition(), 12.0)).stream().filter(filter);
            Objects.requireNonNull(LivingEntity.class);
            List<LivingEntity> entities = entityStream.map(LivingEntity.class::cast).toList();
            LivingEntity target = !entities.isEmpty() ? entities.get(level.random.nextInt(entities.size())) : null;

            if (target == player || target == null) {
                player.displayClientMessage(Component.translatable("item.celestisynth.aquaflora.skill_3.notice"), true);
                player.playSound(CSSoundEvents.BLING.get(), 0.25F, 1.5F);
                CSEffectEntity.createInstance(player, null, CSVisualTypes.AQUAFLORA_DASH.get(), 0.0, 0.55, 0.0);
                self.baseStop();
                ci.cancel();
                return;
            }

            double offsetX = (double)(-4 + level.random.nextInt(8));
            double offsetZ = (double)(-4 + level.random.nextInt(8));
            if (level.isClientSide()) {
                double dx = target.getX() - (player.getX() + offsetX);
                double dz = target.getZ() - (player.getZ() + offsetZ);
                double yaw = -Math.atan2(dx, dz);
                yaw *= 57.29577951308232;
                yaw += (double)(yaw < 0.0 ? 360 : 0);
                player.setYRot((float)yaw);
            }

            CSEffectEntity.createInstance(player, null, CSVisualTypes.AQUAFLORA_DASH.get(), 0.0, 0.55, 0.0);
            BlockPos toPos = target.blockPosition().offset((int)offsetX, 1, (int)offsetZ);
            player.setDeltaMovement(((double)toPos.getX() - player.getX()) * 0.25, ((double)toPos.getY() - player.getY()) * 0.25, ((double)toPos.getZ() - player.getZ()) * 0.25);
            CSEffectEntity.createInstance(player, target, CSVisualTypes.AQUAFLORA_ASSASSINATE.get(), 0.0, -0.2, 0.0);
            player.playSound(CSSoundEvents.WIND_STRIKE.get(), 0.15F, 0.5F);
            float dualWieldMultiplier = self.checkDualWield(player, AquafloraItem.class) ? 1.5F : 2.5F;
            self.attributeDependentAttack(player, target, self.getStack(), dualWieldMultiplier, AttackHurtTypes.RAPID_NO_KB);
            AquafloraSlashFrenzyAttack.createAquafloraFirework(self.getStack(), level, player, target.getX(), target.getY() + 1.0, target.getZ());
        }

        ci.cancel();
    }
}