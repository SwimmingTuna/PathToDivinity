package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import net.mcreator.terramity.procedures.AdvancedAutomaticRifleRightclickedProcedure;
import net.mcreator.terramity.procedures.PlanetBusterLaserProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = PlanetBusterLaserProcedure.class, remap = false)
public class PlanetBusterDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(doubleValue = 70.0D)
    )
    private static double modifyDamage(double damage) {
        return damage * 3.0D;
    }
}
