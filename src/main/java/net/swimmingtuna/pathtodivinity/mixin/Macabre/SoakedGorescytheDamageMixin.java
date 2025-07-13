package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.item.TrueGorescytheItem;
import com.curseforge.macabre.procedures.DoubleslitRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = DoubleslitRightclickedProcedure.class, remap = false)
public class SoakedGorescytheDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 15.0F)
    )
    private static float modifyBloodslashDamage(float damage) {
        return damage * 0.7F;
    }

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(floatValue = 0.0F)
    )
    private static float modifyWhirlpoolDamage(float damage) {
        return 3.0F;
    }
}
