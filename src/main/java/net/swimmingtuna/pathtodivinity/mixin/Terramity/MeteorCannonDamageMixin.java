package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import net.mcreator.terramity.procedures.MeteorGunRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = MeteorGunRightclickedProcedure.class, remap = false)
public class MeteorCannonDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(doubleValue = 4.0D)
    )
    private static double modifyDamage(double damage) {
        return damage * 1.3D;
    }
}
