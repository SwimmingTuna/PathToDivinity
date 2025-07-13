package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import com.curseforge.macabre.procedures.HemorrhageSwordRightclickedProcedure;
import net.mcreator.terramity.procedures.FlintlockPistolRightclickedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = FlintlockPistolRightclickedProcedure.class, remap = false)
public class FlintlockPistolDamageMixin {

    @ModifyConstant(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant(doubleValue = 2.6D)
    )
    private static double modifyDamage(double damage) {
        return damage * 1.5D;
    }
}
