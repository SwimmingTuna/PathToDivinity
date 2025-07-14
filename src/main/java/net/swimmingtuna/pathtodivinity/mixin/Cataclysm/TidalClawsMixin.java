package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.Gauntlet_of_Guard;
import com.github.L_Ender.cataclysm.items.Tidal_Claws;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Tidal_Claws.class, remap = true)
public class TidalClawsMixin { //ATTRIBUTE

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 7.0)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 10.0;
    }
}
