package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.infernal_forge;
import com.github.alexmodguy.alexscaves.server.item.ExtinctionSpearItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = infernal_forge.class, remap = false)
public class InfernalForgeMixin { //MODIFY

    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 8))
    private static int modifyAttackDamage(int original) {
        return 12;
    }
}
