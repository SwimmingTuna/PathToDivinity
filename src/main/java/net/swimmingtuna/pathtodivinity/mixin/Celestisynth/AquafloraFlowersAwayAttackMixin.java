package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.common.attack.aquaflora.AquafloraFlowersAwayAttack;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = AquafloraFlowersAwayAttack.class, remap = false)
public class AquafloraFlowersAwayAttackMixin {

    @Inject(method = "startUsing", at = @At("HEAD"), cancellable = true)
    public void injectStartUsing(CallbackInfo ci) {
        AquafloraFlowersAwayAttack self = (AquafloraFlowersAwayAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();

        self.sendExpandingParticles(level, ParticleTypes.END_ROD, player.getX(), player.getY(), player.getZ(), 55, 1.2F);
        CSEffectEntity.createInstance(player, null, CSVisualTypes.AQUAFLORA_FLOWER.get(), 0.0, -1.0, 0.0);
        List<Entity> entities = self.iterateEntities(level, self.createAABB(player.blockPosition(), 12.0));
        player.playSound(CSSoundEvents.BLING.get(), 0.4F, 0.5F);

        for (Entity target : entities) {
            if (target instanceof LivingEntity lt) {
                if (target != player && target.isAlive() && !player.isAlliedTo(target)) {
                    CSEffectEntity.createInstance(player, target, CSVisualTypes.AQUAFLORA_FLOWER_BIND.get());
                    self.attributeDependentAttack(player, lt, self.getStack(), 3.5F, AttackHurtTypes.NO_KB);
                    target.setDeltaMovement((player.getX() - target.getX()) * 0.35, (player.getY() - target.getY()) * 0.35, (player.getZ() - target.getZ()) * 0.35);
                }
            }
        }

        self.getTagController().putBoolean("cs.checkPassiveIfBlooming", false);

        ci.cancel();
    }
}