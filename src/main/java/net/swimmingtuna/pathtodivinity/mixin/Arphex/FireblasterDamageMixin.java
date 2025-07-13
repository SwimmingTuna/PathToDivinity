package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import com.aetherteam.aether.mixin.mixins.common.accessor.LevelAccessor;
import net.arphex.procedures.FireblasterRightClickProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireblasterRightClickProcedure.class)
public class FireblasterDamageMixin {

    // Modify the damage for armored entities (2 / (armor / 5)) -> 4x damage
    @ModifyArg(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"),
            index = 1
    )
    private static float modifyArmoredDamage(float damage) {
        return damage * 4.0f;
    }
}
