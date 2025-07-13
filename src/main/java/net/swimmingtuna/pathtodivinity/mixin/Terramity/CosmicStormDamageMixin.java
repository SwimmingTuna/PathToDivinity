package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import net.mcreator.terramity.procedures.AdvancedBurstRifleRightclickedProcedure;
import net.mcreator.terramity.procedures.CosmicStormRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = CosmicStormRightclickedProcedure.class, remap = false)
public class CosmicStormDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(doubleValue = 25.0D)
    )
    private static double modifyDamage(double damage) {
        return damage * 0.8D;
    }
}
