package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.common.attack.aquaflora.AquafloraPetalPiercesAttack;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = AquafloraPetalPiercesAttack.class, remap = false)
public class AquafloraPetalPiercesAttackMixin {

    @Inject(method = "tickAttack", at = @At("HEAD"), cancellable = true)
    public void injectTickAttack(CallbackInfo ci) {
        AquafloraPetalPiercesAttack self = (AquafloraPetalPiercesAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();

        if (self.getTimerProgress() >= 0 && self.getTimerProgress() <= 15) {
            player.playSound(CSSoundEvents.AIR_SWING.get(), 0.25F, 1.3F + level.random.nextFloat());
            CSEffectEntity.createInstance(player, null, CSVisualTypes.AQUAFLORA_STAB.get(), -0.5 + level.random.nextDouble() + self.calculateXLook(player) * 3.0, -0.5 + level.random.nextDouble() + 2.0 + self.calculateYLook(player) * 3.0, -0.5 + level.random.nextDouble() + self.calculateZLook(player) * 3.0);
            List<Entity> entities = self.iterateEntities(level, self.createAABB(player.blockPosition().offset((int)(self.calculateXLook(player) * 4.5), (int)(1.0 + self.calculateYLook(player) * 4.5), (int)(self.calculateZLook(player) * 4.5)), 2.0));
            entities.addAll(self.iterateEntities(level, self.createAABB(player.blockPosition().offset((int)(self.calculateXLook(player) * 3.0), (int)(1.0 + self.calculateYLook(player) * 3.0), (int)(self.calculateZLook(player) * 3.0)), 2.0)));
            entities.addAll(self.iterateEntities(level, self.createAABB(player.blockPosition().offset((int)(self.calculateXLook(player) * 1.5), (int)(1.0 + self.calculateYLook(player) * 1.5), (int)(self.calculateZLook(player) * 1.5)), 2.0)));

            if (!entities.isEmpty()) {
                player.playSound(CSSoundEvents.BLING.get(), 0.15F, 1.0F + level.random.nextFloat());
            }

            for (Entity entityBatch : entities) {
                if (entityBatch instanceof LivingEntity target) {
                    if (target != player && target.isAlive() && !player.isAlliedTo(target)) {
                        self.attributeDependentAttack(player, target, self.getStack(), 0.275F, AttackHurtTypes.RAPID_NO_KB);
                        self.createHitEffect(self.getStack(), level, player, target);
                    }
                }
            }
        }

        ci.cancel();
    }
}