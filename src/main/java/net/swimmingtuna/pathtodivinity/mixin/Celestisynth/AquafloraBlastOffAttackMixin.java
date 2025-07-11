package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.api.item.AttackHurtTypes;
import com.aqutheseal.celestisynth.api.item.CSWeaponUtil;
import com.aqutheseal.celestisynth.common.attack.aquaflora.AquafloraBlastOffAttack;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = AquafloraBlastOffAttack.class, remap = false)
public class AquafloraBlastOffAttackMixin {

    @Inject(method = "startUsing", at = @At("HEAD"), cancellable = true)
    public void injectStartUsing(CallbackInfo ci) {
        AquafloraBlastOffAttack self = (AquafloraBlastOffAttack)(Object)this;
        Player player = self.getPlayer();
        Level level = self.getLevel();
        List<Entity> surroundingEntities = self.iterateEntities(level, self.createAABB(player.blockPosition().offset((int)(self.calculateXLook(player) * 4.0), (int)(2.0 + self.calculateYLook(player) * 3.0), (int)(self.calculateZLook(player) * 4.0)), 3.0));
        player.playSound(SoundEvents.WITHER_BREAK_BLOCK, 0.7F, 1.5F);
        CSEffectEntity.createInstance(player, null, CSVisualTypes.AQUAFLORA_BASH.get(), self.calculateXLook(player) * 2.0, 1.5, self.calculateZLook(player) * 2.0);
        for (Entity entityBatch : surroundingEntities) {
            if (entityBatch instanceof LivingEntity target) {
                if (target != player && target.isAlive() && !player.isAlliedTo(target)) {
                    target.setDeltaMovement((target.getX() - player.getX()) * 0.4, 1.0, (target.getZ() - player.getZ()) * 0.4);
                    self.attributeDependentAttack(player, target, self.getStack(), 11.0F, AttackHurtTypes.NO_KB);
                    self.createHitEffect(self.getStack(), level, player, target);
                    CSWeaponUtil.disableRunningWeapon(target);
                }
            }
        }
        double check = player.onGround() ? 0.3 : 0.14;
        if (level.isClientSide()) {
            self.shakeScreens(player, 3, 2, 0.015F);
        }
        player.setDeltaMovement(player.getDeltaMovement().add(self.calculateXLook(player) * check, 0.0, self.calculateZLook(player) * check));
        self.getTagController().putBoolean("cs.checkPassiveIfBlooming", true);
        ci.cancel();
    }
}