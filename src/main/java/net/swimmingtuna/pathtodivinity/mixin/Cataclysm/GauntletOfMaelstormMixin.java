package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.Gauntlet_of_Guard;
import com.github.L_Ender.cataclysm.items.Gauntlet_of_Maelstrom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Gauntlet_of_Maelstrom.class, remap = false)
public class GauntletOfMaelstormMixin { //ATTRIBUTE

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 10.0)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 15.0;
    }
}
