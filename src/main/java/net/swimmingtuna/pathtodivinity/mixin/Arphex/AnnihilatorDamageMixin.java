package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.procedures.AnnihilatorHandTickProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = AnnihilatorHandTickProcedure.class, remap = false)
public class AnnihilatorDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 6.0F)
    )
    private static float modifyProjectileDamage(float damage) {
        return damage * 7.5F; // Average between 5x (players) and 10x (mobs) = 7.5x
    }

    // Alternative approach - if you want different multipliers based on what gets hit,
    // you would need to create a separate mixin for the AbyssExplosiveEntity's damage dealing
}

